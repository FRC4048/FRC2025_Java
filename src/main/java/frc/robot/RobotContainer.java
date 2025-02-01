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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
import java.util.Optional;

public class RobotContainer {
  private SwerveDrivetrain drivetrain;
  private final HihiRollerSubsystem hihiRoller;
  private final HihiExtenderSubsystem hihiExtender;
  private final CommandXboxController controller =
      new CommandXboxController(Constants.XBOX_CONTROLLER_ID);
  private final Joystick joyleft = new Joystick(Constants.LEFT_JOYSTICK_ID);
  private final Joystick joyright = new Joystick(Constants.RIGHT_JOYSTICK_ID);
  private final JoystickButton joyStickButton1 = new JoystickButton(joyleft, 1);

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
    try {
      Command command = AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post F"));

      joyStickButton1.onTrue(command);
    } catch (Exception e) {
      e.printStackTrace();
    }

    drivetrain.setDefaultCommand(
        new Drive(
            drivetrain, joyleft::getY, joyleft::getX, joyright::getX, drivetrain::getDriveMode));
    controller.x().onTrue(new SpinExtender(hihiExtender, 1));
    //    try {
    //      SmartDashboard.putData("Robot1toPostA", drivetrain);
    //      SmartDashboard.putData(
    //          "Robot 1 to Post A",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post A")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post B")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post C")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post D")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post E")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post F")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post G")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post H")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post I")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post J")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post K")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post L")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post A")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post B")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post C")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post D")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post E")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post F")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post G")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post H")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post I")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post J")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post K")));
    //      SmartShuffleboard.putCommand(
    //          "Robot2",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post L")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post A")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post B")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post C")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post D")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post E")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post F")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post G")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post H")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post I")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post J")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post K")));
    //      SmartShuffleboard.putCommand(
    //          "Robot3",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 3 to Post L")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post A")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post B")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post C")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "A to D",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post D")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post E")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot4 to Post F")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post G")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "E to H",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post H")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post I")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post J")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post K")));
    //      SmartShuffleboard.putCommand(
    //          "Robot4",
    //          "I to L",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post L")));
    //      SmartShuffleboard.putCommand(
    //          "Robot1",
    //          "Push",
    //          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Push Robot 1 to Post A")));

    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }
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
