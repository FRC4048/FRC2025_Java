// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.apriltags.MockApriltag;
import frc.robot.apriltags.TCPApriltag;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.subsystemTests.SpinExtender;
import frc.robot.constants.Constants;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.MockGyroIO;
import frc.robot.subsystems.gyro.RealGyroIO;
import frc.robot.subsystems.gyro.ThreadedGyro;
import frc.robot.subsystems.hihiExtender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiExtender.MockHihiExtenderIO;
import frc.robot.subsystems.hihiExtender.RealHihiExtenderIO;
import frc.robot.subsystems.hihiRoller.HihiRollerSubsystem;
import frc.robot.subsystems.hihiRoller.MockHihiRollerIO;
import frc.robot.subsystems.hihiRoller.RealHihiRollerIO;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.io.abs.MockAbsIO;
import frc.robot.subsystems.swervev3.io.drive.MockDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.MockSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import java.util.Optional;

public class RobotContainer {
  private SwerveDrivetrain drivetrain;
  private final HihiRollerSubsystem hihiRoller;
  private final HihiExtenderSubsystem hihiExtender;
  private final CommandXboxController controller =
      new CommandXboxController(Constants.XBOX_CONTROLLER_ID);
  private final Joystick joyleft = new Joystick(Constants.LEFT_JOYSTICK_ID);
  private final Joystick joyright = new Joystick(Constants.RIGHT_JOYSTICK_ID);

  public RobotContainer() {
    if (Robot.isReal()) {
      hihiRoller = new HihiRollerSubsystem(new RealHihiRollerIO());
      hihiExtender = new HihiExtenderSubsystem(new RealHihiExtenderIO());
    } else {
      hihiRoller = new HihiRollerSubsystem(new MockHihiRollerIO());
      hihiExtender = new HihiExtenderSubsystem(new MockHihiExtenderIO());
    }
    setupDriveTrain();
    configureBindings();
  }

  private void configureBindings() {
    drivetrain.setDefaultCommand(
        new Drive(
            drivetrain, joyleft::getY, joyleft::getX, joyright::getX, drivetrain::getDriveMode));
    controller.x().onTrue(new SpinExtender(hihiExtender, 1));

    try {
      SmartShuffleboard.putCommand(
          "Robot1",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostA")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostB")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostC")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostD")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostE")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostF")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostG")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostH")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostI")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostJ")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostK")));
      SmartShuffleboard.putCommand(
          "Robot1",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot1toPostL")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostA")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostB")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostC")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostD")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostE")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostF")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostG")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostH")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostI")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostJ")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostK")));
      SmartShuffleboard.putCommand(
          "Robot2",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot2toPostL")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostA")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostB")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostC")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostD")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostE")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostF")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostG")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostH")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostI")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostJ")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostK")));
      SmartShuffleboard.putCommand(
          "Robot3",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot3toPostL")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostA")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostB")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostC")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "A to D",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostD")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostE")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostF")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostG")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "E to H",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostH")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostI")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostJ")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostK")));
      SmartShuffleboard.putCommand(
          "Robot4",
          "I to L",
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4toPostL")));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public static boolean isRedAlliance() {
    Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
    return alliance.filter(value -> value == DriverStation.Alliance.Red).isPresent();
  }

  private void setupDriveTrain() {
    SwerveIdConfig frontLeftIdConf =
        new SwerveIdConfig(
            Constants.DRIVE_FRONT_LEFT_D,
            Constants.DRIVE_FRONT_LEFT_S,
            Constants.DRIVE_CANCODER_FRONT_LEFT);
    SwerveIdConfig frontRightIdConf =
        new SwerveIdConfig(
            Constants.DRIVE_FRONT_RIGHT_D,
            Constants.DRIVE_FRONT_RIGHT_S,
            Constants.DRIVE_CANCODER_FRONT_RIGHT);
    SwerveIdConfig backLeftIdConf =
        new SwerveIdConfig(
            Constants.DRIVE_BACK_LEFT_D,
            Constants.DRIVE_BACK_LEFT_S,
            Constants.DRIVE_CANCODER_BACK_LEFT);
    SwerveIdConfig backRightIdConf =
        new SwerveIdConfig(
            Constants.DRIVE_BACK_RIGHT_D,
            Constants.DRIVE_BACK_RIGHT_S,
            Constants.DRIVE_CANCODER_BACK_RIGHT);

    TrapezoidProfile.Constraints constraints =
        new TrapezoidProfile.Constraints(Constants.MAX_ANGULAR_SPEED * 150, 2 * Math.PI * 150);
    PID drivePid = PID.of(Constants.DRIVE_PID_P, Constants.DRIVE_PID_I, Constants.DRIVE_PID_D);
    PID steerPid = PID.of(Constants.STEER_PID_P, Constants.STEER_PID_I, Constants.STEER_PID_D);
    Gain driveGain = Gain.of(Constants.DRIVE_PID_FF_V, Constants.DRIVE_PID_FF_S);
    Gain steerGain = Gain.of(Constants.STEER_PID_FF_V, Constants.STEER_PID_FF_S);

    KinematicsConversionConfig kConfig =
        new KinematicsConversionConfig(Constants.WHEEL_RADIUS, Constants.SWERVE_MODULE_PROFILE);
    SwervePidConfig pidConfig =
        new SwervePidConfig(drivePid, steerPid, driveGain, steerGain, constraints);

    SwerveModule frontLeft;
    SwerveModule frontRight;
    SwerveModule backLeft;
    SwerveModule backRight;

    GyroIO gyroIO;
    LoggableIO<ApriltagInputs> apriltagIO;
    if (Robot.isReal()) {
      frontLeft =
          SwerveModule.createModule(
              frontLeftIdConf, kConfig, pidConfig, ModulePosition.FRONT_LEFT, false);
      frontRight =
          SwerveModule.createModule(
              frontRightIdConf, kConfig, pidConfig, ModulePosition.FRONT_RIGHT, true);
      backLeft =
          SwerveModule.createModule(
              backLeftIdConf, kConfig, pidConfig, ModulePosition.BACK_LEFT, false);
      backRight =
          SwerveModule.createModule(
              backRightIdConf,
              kConfig,
              pidConfig,
              ModulePosition.BACK_RIGHT,
              true); // TODO: put these in the right SwerveModuleProfiles later

      ThreadedGyro threadedGyro =
          new ThreadedGyro(new AHRS(NavXComType.kMXP_SPI)); // TODO: change comtype later
      threadedGyro.start();
      gyroIO = new RealGyroIO(threadedGyro);
      apriltagIO = new TCPApriltag();
    } else {
      frontLeft =
          new SwerveModule(
              new MockDriveMotorIO(),
              new MockSteerMotorIO(),
              new MockAbsIO(),
              pidConfig,
              "frontLeft");
      frontRight =
          new SwerveModule(
              new MockDriveMotorIO(),
              new MockSteerMotorIO(),
              new MockAbsIO(),
              pidConfig,
              "frontRight");
      backLeft =
          new SwerveModule(
              new MockDriveMotorIO(),
              new MockSteerMotorIO(),
              new MockAbsIO(),
              pidConfig,
              "backLeft");
      backRight =
          new SwerveModule(
              new MockDriveMotorIO(),
              new MockSteerMotorIO(),
              new MockAbsIO(),
              pidConfig,
              "backRight");
      gyroIO = new MockGyroIO();
      apriltagIO = new MockApriltag();
    }
    drivetrain =
        new SwerveDrivetrain(frontLeft, frontRight, backLeft, backRight, gyroIO, apriltagIO);
  }

  public SwerveDrivetrain getDrivetrain() {
    return drivetrain;
  }
}
