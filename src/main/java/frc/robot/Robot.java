// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.pathfinding.Pathfinding;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.drivetrain.WheelAlign;
import frc.robot.constants.Constants;
import frc.robot.utils.RobotMode;
import frc.robot.utils.logging.CommandLogger;
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

  public Robot() {
    Pathfinding.setPathfinder(new LocalADStarAK());
    // Record metadata
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
    m_robotContainer = new RobotContainer();
  }

  public static RobotMode getMode() {
    return mode.get();
  }

  @Override
  public void robotInit() {
    if (Constants.ENABLE_LOGGING) {
      Logger.recordMetadata("ProjectName", "FRC2025_Java"); // Set a metadata value
      Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
      if (isReal()) {
        Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
      } else {
        setUseTiming(false); // Run as fast as possible (false == run fast, true == run real)
        String logPath =
            LogFileUtil
                .findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
        Logger.setReplaySource(new WPILOGReader(logPath)); // Read replay log
        Logger.addDataReceiver(
            new WPILOGWriter(
                LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save outputs to a new log
      }
      Logger
          .start(); // Start logging! No more data receivers, replay sources, or metadata values may
      // be added.
      // Log active commands
      CommandLogger.get().init();
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    if (Constants.ENABLE_LOGGING) {
      CommandLogger.get().log();
    }
    counter++;
    if (counter == 2) {
      new WheelAlign(m_robotContainer.getDrivetrain()).schedule();
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
}
