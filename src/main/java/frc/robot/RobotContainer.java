// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
import frc.robot.commands.Climber.ClimberRunMotors;
import frc.robot.commands.RollAlgae;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.intake.IntakeCoral;
import frc.robot.commands.subsystemtests.SpinExtender;
import frc.robot.commands.subsystemtests.SpinRollerByeBye;
import frc.robot.commands.subsystemtests.SpinTiltByeBye;
import frc.robot.constants.Constants;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyeroller.MockAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.RealAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.SimAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.algaebyebyetilt.MockAlgaeByeByeTiltIO;
import frc.robot.subsystems.algaebyebyetilt.RealAlgaeByeByeTiltIO;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.climber.MockClimberIO;
import frc.robot.subsystems.climber.RealClimberIO;
import frc.robot.subsystems.climber.SimClimberIO;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.coral.MockCoralIO;
import frc.robot.subsystems.coral.RealCoralIO;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.MockElevatorIO;
import frc.robot.subsystems.elevator.RealElevatorIO;
import frc.robot.subsystems.elevator.SimElevatorIO;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.MockGyroIO;
import frc.robot.subsystems.gyro.RealGyroIO;
import frc.robot.subsystems.gyro.ThreadedGyro;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiextender.MockHihiExtenderIO;
import frc.robot.subsystems.hihiextender.RealHihiExtenderIO;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.subsystems.hihiroller.MockHihiRollerIO;
import frc.robot.subsystems.hihiroller.RealHihiRollerIO;
import frc.robot.subsystems.hihiroller.SimHihiRollerIO;
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
  private final AlgaeByeByeRollerSubsystem byebyeRoller;
  private final AlgaeByeByeTiltSubsystem byebyeTilt;
  private final HihiRollerSubsystem hihiRoller;
  private final HihiExtenderSubsystem hihiExtender;
  private final ElevatorSubsystem elevatorSubsystem;
  private final CoralSubsystem shooter;
  private final ClimberSubsystem climber;
  private final CommandXboxController controller =
      new CommandXboxController(Constants.XBOX_CONTROLLER_ID);
  private final Joystick joyleft = new Joystick(Constants.LEFT_JOYSTICK_ID);
  private final Joystick joyright = new Joystick(Constants.RIGHT_JOYSTICK_ID);

  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL -> {
        hihiRoller = new HihiRollerSubsystem(new RealHihiRollerIO());
        hihiExtender = new HihiExtenderSubsystem(new RealHihiExtenderIO());
        elevatorSubsystem = new ElevatorSubsystem(new RealElevatorIO());
        shooter = new CoralSubsystem(new RealCoralIO());
        climberSubsystem = new ClimberSubsystem(new RealClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new RealAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new RealAlgaeByeByeTiltIO());
      }
      case REPLAY -> {
        hihiRoller = new HihiRollerSubsystem(new MockHihiRollerIO());
        hihiExtender = new HihiExtenderSubsystem(new MockHihiExtenderIO());
        elevatorSubsystem = new ElevatorSubsystem(new MockElevatorIO());
        shooter = new CoralSubsystem(new MockCoralIO());
        climberSubsystem = new ClimberSubsystem(new MockClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new MockAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new MockAlgaeByeByeTiltIO());
      }
      case SIM -> {
        hihiRoller = new HihiRollerSubsystem(new SimHihiRollerIO()); // TODO
        hihiExtender = new HihiExtenderSubsystem(new MockHihiExtenderIO()); // TODO
        elevatorSubsystem = new ElevatorSubsystem(new SimElevatorIO());
        shooter = new CoralSubsystem(new MockCoralIO());
        climberSubsystem = new ClimberSubsystem(new SimClimberIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new MockAlgaeByeByeTiltIO()); // TODO
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new SimAlgaeByeByeRollerIO());
      }
      default -> {
        throw new RuntimeException("Did not specify Robot Mode");
      }
    }
    setupDriveTrain();
    configureBindings();
    putShuffleboardCommands();
  }

  private void configureBindings() {
    drivetrain.setDefaultCommand(
        new Drive(
            drivetrain, joyleft::getY, joyleft::getX, joyright::getX, drivetrain::getDriveMode));
    controller.x().onTrue(new SpinExtender(hihiExtender, 1));
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.putCommand("DEBUG", "Roll Algae", new RollAlgae(hihiRoller, 0.5));
      SmartShuffleboard.putCommand("DEBUG", "Climber run", new ClimberRunMotors(climber, 0.5));
      SmartShuffleboard.putCommand("DEBUG", "Climber stop", new ClimberRunMotors(climber, 0));
      SmartShuffleboard.put("DEBUG", "CID", Constants.ALGAE_ROLLER_CAN_ID);
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

  public ClimberSubsystem getClimber() {
    return climber;
  }

  public void putShuffleboardCommands() {
    if (Constants.INTAKE_DEBUG) {
      SmartShuffleboard.putCommand("Commands", "Intake Coral", new IntakeCoral(shooter));
      SmartShuffleboard.putCommand(
          "Commands", "Shoot Coral", new ShootCoral(shooter, Constants.CORAL_SHOOTER_SPEED));
    }
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.putCommand(
          "Bye Bye",
          "Spin Roller ",
          new SpinRollerByeBye(byebyeRoller, Constants.BYEBYE_ROLLER_SPEED));
      SmartShuffleboard.putCommand(
          "Bye Bye", "Spin Tilt", new SpinTiltByeBye(byebyeTilt, Constants.TILT_SPEED));
    }
  }
}
