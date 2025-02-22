// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
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
import frc.robot.commands.CancelAll;
import frc.robot.commands.RollAlgae;
import frc.robot.commands.byebye.ByeByeToFwrLimit;
import frc.robot.commands.byebye.ByeByeToRevLimit;
import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.elevator.*;
import frc.robot.commands.hihi.*;
import frc.robot.commands.lightStrip.SetLedPattern;
import frc.robot.commands.sequences.ByeByeAllDone;
import frc.robot.commands.sequences.IntakeAlgae;
import frc.robot.commands.sequences.PickUpCoral;
import frc.robot.commands.sequences.RemoveAlgaeFromReef;
import frc.robot.commands.sequences.ShootAlgae;
import frc.robot.commands.subsystemtests.CoralIdleMode;
import frc.robot.commands.subsystemtests.SetCoralLimitState;
import frc.robot.commands.subsystemtests.SpinRollerByeBye;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyeroller.MockAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.RealAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.SimAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.algaebyebyetilt.MockAlgaeByeByeTiltIO;
import frc.robot.subsystems.algaebyebyetilt.RealAlgaeByeByeTiltIO;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.coral.MockCoralIOFollower;
import frc.robot.subsystems.coral.MockCoralIOLeader;
import frc.robot.subsystems.coral.RealCoralIOFollower;
import frc.robot.subsystems.coral.RealCoralIOLeader;
import frc.robot.subsystems.coral.SimCoralIOFollower;
import frc.robot.subsystems.coral.SimCoralIOLeader;
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
import frc.robot.subsystems.hihiextender.SimHihiExtenderIO;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.subsystems.hihiroller.MockHihiRollerIO;
import frc.robot.subsystems.hihiroller.RealHihiRollerIO;
import frc.robot.subsystems.hihiroller.SimHihiRollerIO;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.subsystems.lightStrip.MockLightStripIO;
import frc.robot.subsystems.lightStrip.RealLightStripIO;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.io.abs.MockAbsIO;
import frc.robot.subsystems.swervev3.io.drive.MockDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.MockSteerMotorIO;
import frc.robot.utils.BlinkinPattern;
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
  private final CoralSubsystem coralSubsystem;
  //  private final ClimberSubsystem climber;
  private final LightStrip lightStrip;
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
        coralSubsystem = new CoralSubsystem(new RealCoralIOFollower(), new RealCoralIOLeader());
        //                climber = new ClimberSubsystem(new RealClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new RealAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new RealAlgaeByeByeTiltIO());
        lightStrip = new LightStrip(new RealLightStripIO());
      }
      case REPLAY -> {
        hihiRoller = new HihiRollerSubsystem(new MockHihiRollerIO());
        hihiExtender = new HihiExtenderSubsystem(new MockHihiExtenderIO());
        elevatorSubsystem = new ElevatorSubsystem(new MockElevatorIO());
        coralSubsystem = new CoralSubsystem(new MockCoralIOFollower(), new MockCoralIOLeader());
        //                climber = new ClimberSubsystem(new MockClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new MockAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new MockAlgaeByeByeTiltIO());
        lightStrip = new LightStrip(new MockLightStripIO());
      }
      case SIM -> {
        hihiRoller = new HihiRollerSubsystem(new SimHihiRollerIO()); // TODO
        hihiExtender = new HihiExtenderSubsystem(new SimHihiExtenderIO()); // TODO
        elevatorSubsystem = new ElevatorSubsystem(new SimElevatorIO());
        coralSubsystem = new CoralSubsystem(new SimCoralIOFollower(), new SimCoralIOLeader());
        //        climber = new ClimberSubsystem(new SimClimberIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new MockAlgaeByeByeTiltIO()); // TODO
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new SimAlgaeByeByeRollerIO());
        lightStrip = new LightStrip(new MockLightStripIO());
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
    controller.leftTrigger().onTrue(new PickUpCoral(elevatorSubsystem, coralSubsystem));
    controller
        .povUp()
        .onTrue(new SetElevatorStoredPosition(ElevatorPositions.LEVEL4, elevatorSubsystem));
    controller
        .povDown()
        .onTrue(new SetElevatorStoredPosition(ElevatorPositions.LEVEL1, elevatorSubsystem));
    controller
        .povLeft()
        .onTrue(new SetElevatorStoredPosition(ElevatorPositions.LEVEL2, elevatorSubsystem));
    controller
        .povRight()
        .onTrue(new SetElevatorStoredPosition(ElevatorPositions.LEVEL3, elevatorSubsystem));
    controller.rightBumper().onTrue(new ElevatorToStoredPosition(elevatorSubsystem));
    controller.leftBumper().onTrue(new ResetElevator(elevatorSubsystem));
    controller.rightTrigger().onTrue(new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED));
    SetElevatorTargetPosition setElevatorTargetPosition =
        new SetElevatorTargetPosition(() -> (controller.getLeftY()), elevatorSubsystem);
    elevatorSubsystem.setDefaultCommand(setElevatorTargetPosition);
    controller.x().onTrue(new IntakeAlgae(hihiExtender, hihiRoller));
    controller.y().onTrue(new ShootAlgae(hihiExtender, hihiRoller));
    controller.a().onTrue(new RemoveAlgaeFromReef(byebyeTilt, byebyeRoller));
    controller.b().onTrue(new ByeByeAllDone(byebyeTilt, byebyeRoller));
    controller.back().onTrue(new CancelAll(elevatorSubsystem, hihiExtender));
    // climber on Right Trigger
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.putCommand("DEBUG", "Roll Algae", new RollAlgae(hihiRoller, 0.5));
      //      SmartShuffleboard.putCommand("DEBUG", "Climber reset", new ResetClimber(climber));
      //      SmartShuffleboard.putCommand("DEBUG", "Climber stop", new CloseClimber(climber));
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

  public void putShuffleboardCommands() {

    if (Constants.CORAL_DEBUG) {
      SmartShuffleboard.putCommand(
          "Commands", "Shoot Coral", new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED));
      SmartShuffleboard.putCommand("Commands", "Intake Coral", new IntakeCoral(coralSubsystem));

      SmartShuffleboard.putCommand(
          "Coral", "CoralBreakModeCoast", new CoralIdleMode(coralSubsystem, IdleMode.kCoast));

      SmartShuffleboard.putCommand(
          "Coral", "CoralBreakModeBrake", new CoralIdleMode(coralSubsystem, IdleMode.kBrake));

      SmartShuffleboard.putCommand(
          "Coral", "Set Coral Limit True", new SetCoralLimitState(coralSubsystem, true));

      SmartShuffleboard.putCommand(
          "Coral", "Set Coral Limit False", new SetCoralLimitState(coralSubsystem, false));

      SmartShuffleboard.putCommand(
          "Coral", "Pick Up Coral", new PickUpCoral(elevatorSubsystem, coralSubsystem));
    }

    if (Constants.HIHI_DEBUG) {
      // HiHi Commads

      SmartShuffleboard.putCommand("HiHi", "Extend HiHi", new ExtendHiHi(hihiExtender));

      SmartShuffleboard.putCommand("HiHi", "Retract HiHi", new RetractHiHi(hihiExtender));

      SmartShuffleboard.putCommand("HiHi", "Roll HiHi Roller In", new RollHiHiRollerIn(hihiRoller));

      SmartShuffleboard.putCommand(
          "HiHi", "Roll HiHi Roller Out", new ShootHiHiRollerOut(hihiRoller));

      SmartShuffleboard.putCommand(
          "HiHi", "Intake Algae", new IntakeAlgae(hihiExtender, hihiRoller));
    }

    if (Constants.BYEBYE_DEBUG) {
      // ByeBye Commands

      SmartShuffleboard.putCommand(
          "ByeBye", "ByeBye To FWD Limit", new ByeByeToFwrLimit(byebyeTilt));

      SmartShuffleboard.putCommand(
          "ByeBye", "ByeBye To REV Limit", new ByeByeToRevLimit(byebyeTilt));

      SmartShuffleboard.putCommand(
          "ByeBye",
          "ByeBye Spin Roller",
          new SpinRollerByeBye(byebyeRoller, Constants.BYEBYE_ROLLER_SPEED));
    }

    if (Constants.ELEVATOR_DEBUG) {
      // Elevator Commands
      SmartShuffleboard.putCommand(
          "Elevator",
          "SetElevatorSetpointTo0",
          new SetElevatorTargetPosition(() -> 0, elevatorSubsystem));
      SmartShuffleboard.putCommand(
          "Elevator", "RestElevatorEncoder", new ResetElevatorEncoder(elevatorSubsystem));

      SmartShuffleboard.putCommand(
          "Elevator", "Reset Elevator", new ResetElevator(elevatorSubsystem));

      SmartShuffleboard.putCommand(
          "Elevator", "Elevator To Position", new ElevatorToStoredPosition(elevatorSubsystem));

      SmartShuffleboard.putCommand(
          "Elevator",
          "Store L0",
          new SetElevatorStoredPosition(ElevatorPositions.CORAL_INTAKE, elevatorSubsystem));
      SmartShuffleboard.putCommand(
          "Elevator",
          "Store L1",
          new SetElevatorStoredPosition(ElevatorPositions.LEVEL1, elevatorSubsystem));
      SmartShuffleboard.putCommand(
          "Elevator",
          "Store L2",
          new SetElevatorStoredPosition(ElevatorPositions.LEVEL2, elevatorSubsystem));
      SmartShuffleboard.putCommand(
          "Elevator",
          "Store L3",
          new SetElevatorStoredPosition(ElevatorPositions.LEVEL3, elevatorSubsystem));
      SmartShuffleboard.putCommand(
          "Elevator",
          "Store L4",
          new SetElevatorStoredPosition(ElevatorPositions.LEVEL4, elevatorSubsystem));
    }

    if (Constants.CLIMBER_DEBUG) {
      // Climber Commands

      //      SmartShuffleboard.putCommand("Climber", "Reset Climber", new ResetClimber(climber));
      //
      //      SmartShuffleboard.putCommand("Climber", "Close Climber", new CloseClimber(climber));
    }

    SmartShuffleboard.putCommand(
        "DEBUG",
        "LightStripPatternGreen",
        new SetLedPattern(lightStrip, BlinkinPattern.BLUE_GREEN));
    SmartShuffleboard.putCommand(
        "DEBUG",
        "LightStripPatternViolet",
        new SetLedPattern(lightStrip, BlinkinPattern.BLUE_VIOLET));
    SmartShuffleboard.putCommand(
        "DEBUG", "CoralBreakModeCoast", new CoralIdleMode(coralSubsystem, IdleMode.kCoast));
  }
}
