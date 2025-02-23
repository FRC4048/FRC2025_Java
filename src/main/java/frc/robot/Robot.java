// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.pathfinding.Pathfinding;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyro;
import frc.robot.commands.drivetrain.WheelAlign;
import frc.robot.constants.Constants;
import frc.robot.utils.RobotMode;
import frc.robot.utils.logging.commands.CommandLogger;
import java.util.concurrent.atomic.AtomicReference;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  private static final AtomicReference<RobotMode> mode = new AtomicReference<>(RobotMode.DISABLED);
  public double counter = 0;
  private static DriverStation.Alliance alliance = null;
  private static boolean fmsAttached = false;

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
    m_robotContainer = new RobotContainer();
  }

  public static RobotMode getMode() {
    return mode.get();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    if (Constants.ENABLE_LOGGING) {
      CommandLogger.get().log();
    }
    if (counter == 0) {
      actualInit();
    }
    counter++;
  }

  /** Use this instead of robot init. */
  private void actualInit() {
    new SequentialCommandGroup(
            new WheelAlign(m_robotContainer.getDrivetrain()),
            new ResetGyro(m_robotContainer.getDrivetrain()))
        .schedule();
  }

  @Override
  public void disabledInit() {
    mode.set(RobotMode.DISABLED);
  }

  @Override
  public void disabledPeriodic() {
    updateFmsAlliance();
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    mode.set(RobotMode.AUTONOMOUS);
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    mode.set(RobotMode.TELEOP);
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    mode.set(RobotMode.TEST);
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  public void simulationInit() {
    mode.set(RobotMode.SIMULATION);
  }

  /**
   * Updates the alliance color if the FMS goes from not attached to attached. This code will only
   * work in competition and there is no fms when you connect locally. Non competition alliance
   * information is updated in {@link Robot#driverStationConnected()}
   */
  private void updateFmsAlliance() {
    if (DriverStation.isDSAttached()) {
      boolean fms = DriverStation.isFMSAttached();
      if ((fms && !fmsAttached) || alliance == null) {
        alliance = DriverStation.getAlliance().orElse(null);
        Logger.recordOutput("FMS_ALLIANCE", alliance == null ? "null" : alliance.name());
      }
      fmsAttached = fms;
    }
  }

  /**
   * Grab alliance color when robot connects to driver station. However, if driver station is not
   * connected to FMS, it will grab the wrong color. This code is here so it will work properly when
   * not in competition. Competition alliance color selection is handled by {@link
   * Robot#updateFmsAlliance()}
   */
  @Override
  public void driverStationConnected() {
    alliance = DriverStation.getAlliance().orElse(null);
    Logger.recordOutput("FMS_ALLIANCE", alliance == null ? "null" : alliance.name());
  }
}
