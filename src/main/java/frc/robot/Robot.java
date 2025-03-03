// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.pathfinding.Pathfinding;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.camera.CameraThread;
import frc.robot.commands.drivetrain.ResetGyro;
import frc.robot.commands.drivetrain.SetBaseVisionStd;
import frc.robot.commands.drivetrain.SetInitOdom;
import frc.robot.commands.drivetrain.WheelAlign;
import frc.robot.constants.Constants;
import frc.robot.utils.RobotMode;
import frc.robot.utils.diag.Diagnostics;
import frc.robot.utils.logging.commands.CommandLogger;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import java.util.concurrent.atomic.AtomicReference;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {
  private Command autoCommand;
  private static final Diagnostics diagnostics = new Diagnostics();

  private final RobotContainer robotContainer;
  private static final AtomicReference<RobotMode> mode = new AtomicReference<>(RobotMode.DISABLED);
  public double counter = 0;

  public Robot() {
    Pathfinding.setPathfinder(new LocalADStarAK());
    // Record Metadata
    Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
    Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
    Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
    Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
    Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
    switch (BuildConstants.DIRTY) {
      case 0:
        Logger.recordMetadata("GitDirty", "All changes committed");
        break;
      case 1:
        Logger.recordMetadata("GitDirty", "Uncomitted changes");
        break;
      default:
        Logger.recordMetadata("GitDirty", "Unknown");
        break;
    }
    // Set up data receivers & replay source
    switch (Constants.currentMode) {
      case REAL:
        // Running on a real robot, log to a USB stick ("/U/logs")
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case SIM:
        // Running a physics simulator, log to NT
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }

    // Start AdvantageKit logger
    Logger.start();
    CommandLogger.get().init();
    robotContainer = new RobotContainer();

    new CameraThread();
  }

  public static RobotMode getMode() {
    return mode.get();
  }

  @Override
  public void robotPeriodic() {
    if (getMode() != RobotMode.TEST) {
      CommandScheduler.getInstance().run();
      if (counter == 0) {
        actualInit();
      }
      counter++;
    }

    if (Constants.ENABLE_LOGGING) {
      CommandLogger.get().log();
    }
  }

  /** Use this instead of robot init. */
  private void actualInit() {
    if (Robot.isReal()) {
      new LoggableSequentialCommandGroup(
              new WheelAlign(robotContainer.getDrivetrain()),
              new ResetGyro(robotContainer.getDrivetrain()))
          .schedule();
    }
  }

  @Override
  public void disabledInit() {
    mode.set(RobotMode.DISABLED);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    mode.set(RobotMode.AUTONOMOUS);
    new SetInitOdom(robotContainer.getDrivetrain(), robotContainer.getAutoChooser()).schedule();
    new SetBaseVisionStd(robotContainer.getDrivetrain(), VecBuilder.fill(0.45, 0.45, 0.1));
    autoCommand = robotContainer.getAutonomousCommand();
    if (autoCommand != null) {
      autoCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    mode.set(RobotMode.TELEOP);
    diagnostics.reset();
    if (autoCommand != null) {
      autoCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    mode.set(RobotMode.TEST);
    diagnostics.reset();
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
    diagnostics.refresh();
  }

  @Override
  public void testExit() {}

  public void simulationInit() {
    mode.set(RobotMode.SIMULATION);
  }

  public static Diagnostics getDiagnostics() {
    return diagnostics;
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    robotContainer.updateSimulation();
  }
}
