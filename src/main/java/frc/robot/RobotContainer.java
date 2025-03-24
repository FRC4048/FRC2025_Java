// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.apriltags.MockApriltag;
import frc.robot.apriltags.TCPApriltag;
import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.autochooser.chooser.AutoChooser2025;
import frc.robot.autochooser.event.RealAutoEventProvider;
import frc.robot.commands.byebye.ByeByeToFwrLimit;
import frc.robot.commands.byebye.ByeByeToRevLimit;
import frc.robot.commands.climber.ClimbToLimit;
import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.RobotSlide;
import frc.robot.commands.drivetrain.SetInitOdom;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.ResetElevatorEncoder;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.elevator.SetElevatorTargetPosition;
import frc.robot.commands.lightStrip.SetLedFromElevatorPosition;
import frc.robot.commands.lightStrip.SetLedPattern;
import frc.robot.commands.sequences.*;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.constants.GameConstants;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyeroller.MockAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.RealAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyeroller.SimAlgaeByeByeRollerIO;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.algaebyebyetilt.MockAlgaeByeByeTiltIO;
import frc.robot.subsystems.algaebyebyetilt.RealAlgaeByeByeTiltIO;
import frc.robot.subsystems.algaebyebyetilt.SimAlgaeByeByeTiltIO;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.climber.MockClimberIO;
import frc.robot.subsystems.climber.RealClimberIO;
import frc.robot.subsystems.climber.SimClimberIO;
import frc.robot.subsystems.coral.*;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.MockElevatorIO;
import frc.robot.subsystems.elevator.RealElevatorIO;
import frc.robot.subsystems.elevator.SimElevatorIO;
import frc.robot.subsystems.gyro.*;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.subsystems.lightStrip.MockLightStripIO;
import frc.robot.subsystems.lightStrip.RealLightStripIO;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.io.abs.MockAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SimAbsIO;
import frc.robot.subsystems.swervev3.io.drive.MockDriveMotorIO;
import frc.robot.subsystems.swervev3.io.drive.SimDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.MockSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SimSteerMotorIO;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;
import frc.robot.utils.simulation.RobotVisualizer;
import frc.robot.utils.simulation.SwerveSimulationUtils;
import java.util.function.Consumer;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.littletonrobotics.junction.Logger;

public class RobotContainer {
  private AutoChooser2025 autoChooser;
  private SwerveDrivetrain drivetrain;
  private final AlgaeByeByeRollerSubsystem byebyeRoller;
  private final AlgaeByeByeTiltSubsystem byebyeTilt;
  //  private final HihiRollerSubsystem hihiRoller;
  //  private final HihiExtenderSubsystem hihiExtender;
  private final ElevatorSubsystem elevatorSubsystem;
  private final CoralSubsystem coralSubsystem;
  private final ClimberSubsystem climber;
  private final LightStrip lightStrip;
  private final CommandXboxController controller =
      new CommandXboxController(Constants.XBOX_CONTROLLER_ID);
  private final Joystick joyleft = new Joystick(Constants.LEFT_JOYSTICK_ID);
  private final Joystick joyright = new Joystick(Constants.RIGHT_JOYSTICK_ID);
  private RobotVisualizer robotVisualizer = null;
  private SwerveDriveSimulation driveSimulation;

  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL -> {
        //  hihiRoller = new HihiRollerSubsystem(new RealHihiRollerIO());
        // hihiExtender = new HihiExtenderSubsystem(new RealHihiExtenderIO());
        elevatorSubsystem = new ElevatorSubsystem(new RealElevatorIO());
        coralSubsystem =
            new CoralSubsystem(
                new RealCoralIOFollower(), new RealCoralIOLeader(), new RealCoralIOAligner());
        climber = new ClimberSubsystem(new RealClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new RealAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new RealAlgaeByeByeTiltIO());
        lightStrip = new LightStrip(new RealLightStripIO());
      }
      case REPLAY -> {
        //  hihiRoller = new HihiRollerSubsystem(new MockHihiRollerIO());
        // hihiExtender = new HihiExtenderSubsystem(new MockHihiExtenderIO());
        elevatorSubsystem = new ElevatorSubsystem(new MockElevatorIO());
        coralSubsystem =
            new CoralSubsystem(
                new MockCoralIOFollower(), new MockCoralIOLeader(), new MockCoralIOAligner());
        climber = new ClimberSubsystem(new MockClimberIO());
        byebyeRoller = new AlgaeByeByeRollerSubsystem(new MockAlgaeByeByeRollerIO());
        byebyeTilt = new AlgaeByeByeTiltSubsystem(new MockAlgaeByeByeTiltIO());
        lightStrip = new LightStrip(new MockLightStripIO());
      }
      case SIM -> {
        robotVisualizer = new RobotVisualizer();
        //        hihiRoller =
        //            new HihiRollerSubsystem(
        //                new SimHihiRollerIO(null)); //
        // robotVisualizer.getAlgaeHiHiRollerLigament()));
        //        hihiExtender =
        //            new HihiExtenderSubsystem(
        //                new SimHihiExtenderIO(null)); //
        // robotVisualizer.getAlgaeHiHiTiltLigament()));
        elevatorSubsystem =
            new ElevatorSubsystem(new SimElevatorIO(robotVisualizer.getElevatorLigament()));
        coralSubsystem =
            new CoralSubsystem(
                new SimCoralIOFollower(),
                new SimCoralIOLeader(robotVisualizer.getCoralRollerLigament()),
                new SimCoralIOAligner(robotVisualizer.getCoralRollerLigament()));
        climber = new ClimberSubsystem(new SimClimberIO(robotVisualizer.getClimberLigament()));
        byebyeTilt =
            new AlgaeByeByeTiltSubsystem(
                new SimAlgaeByeByeTiltIO(robotVisualizer.getAlgaeByeByeTiltLigament()));
        byebyeRoller =
            new AlgaeByeByeRollerSubsystem(
                new SimAlgaeByeByeRollerIO(robotVisualizer.getAlgaeByeByeRollerLigament()));
        lightStrip = new LightStrip(new MockLightStripIO());
      }
      default -> {
        throw new RuntimeException("Did not specify Robot Mode");
      }
    }
    setupDriveTrain();
    configureBindings();
    setupAutoChooser();
    putShuffleboardCommands();
    pathPlannerCommands();
  }

  private void setupAutoChooser() {
    autoChooser =
        new AutoChooser2025(
            new RealAutoEventProvider(AutoAction.DO_NOTHING, FieldLocation.ZERO),
            elevatorSubsystem,
            coralSubsystem,
            lightStrip,
            byebyeTilt);

    autoChooser
        .getProvider()
        .addOnValidationCommand(() -> new SetInitOdom(drivetrain, autoChooser).initialize());
    autoChooser.getProvider().forceRefresh();
  }

  public AutoChooser2025 getAutoChooser() {
    return autoChooser;
  }

  private void pathPlannerCommands() {
    // COMMANDS REGISTERED FOR PATHPLANNER
    NamedCommands.registerCommand("ByeByeToFwrLimit", new ByeByeToFwrLimit(byebyeTilt));
    NamedCommands.registerCommand(
        "ByeByeToRevLimit", new ByeByeToRevLimit(byebyeTilt, elevatorSubsystem));
    NamedCommands.registerCommand(
        "ShootCoral", new ShootCoral(coralSubsystem, elevatorSubsystem::getStoredReefPosition));
    NamedCommands.registerCommand(
        "ElevatorToPositionL0",
        new SetElevatorStoredPosition(
            ElevatorPosition.CORAL_INTAKE, elevatorSubsystem, lightStrip));
    NamedCommands.registerCommand(
        "ElevatorToPositionL1",
        new SetElevatorStoredPosition(ElevatorPosition.LEVEL1, elevatorSubsystem, lightStrip));
    NamedCommands.registerCommand(
        "ElevatorToPositionL2",
        new SetElevatorStoredPosition(ElevatorPosition.LEVEL2, elevatorSubsystem, lightStrip));
    NamedCommands.registerCommand(
        "ElevatorToPositionL3",
        new SetElevatorStoredPosition(ElevatorPosition.LEVEL3, elevatorSubsystem, lightStrip));
    NamedCommands.registerCommand(
        "ElevatorToPositionL4",
        new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip));
  }

  private void configureBindings() {
    lightStrip.setDefaultCommand(
        new SetLedFromElevatorPosition(elevatorSubsystem::getStoredReefPosition, lightStrip));
    drivetrain.setDefaultCommand(
        new Drive(
            drivetrain, joyleft::getY, joyleft::getX, joyright::getX, drivetrain::getDriveMode));

    JoystickButton joyLeft2 = new JoystickButton(joyleft, 2);
    JoystickButton joyRight1 = new JoystickButton(joyright, 1);
    RobotSlide robotSlide = new RobotSlide(drivetrain, joyleft::getX, joyleft::getY);
    joyLeft2.whileTrue(robotSlide);

    controller
        .leftTrigger()
        .onTrue(
            new PickUpCoral(
                elevatorSubsystem, byebyeTilt, byebyeRoller, coralSubsystem, lightStrip));
    controller
        .povUp()
        .onTrue(
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip));
    controller
        .povDown()
        .onTrue(
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL2, elevatorSubsystem, lightStrip));
    controller
        .povLeft()
        .onTrue(
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL1, elevatorSubsystem, lightStrip));
    controller
        .povRight()
        .onTrue(
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL3, elevatorSubsystem, lightStrip));
    controller.rightBumper().onTrue(new ElevatorToStoredPosition(elevatorSubsystem));
    controller.leftBumper().onTrue(new ResetElevator(elevatorSubsystem));
    controller
        .rightTrigger()
        .onTrue(new ShootCoral(coralSubsystem, elevatorSubsystem::getStoredReefPosition));
    SetElevatorTargetPosition setElevatorTargetPosition =
        new SetElevatorTargetPosition(controller::getLeftY, elevatorSubsystem);
    elevatorSubsystem.setDefaultCommand(setElevatorTargetPosition);
    controller.b().onTrue(new ClimbToLimit(climber, Constants.CLIMBER_PHASE2_SPEED));
    controller
        .a()
        .onTrue(new DeployHarpoon(climber, elevatorSubsystem, lightStrip, ElevatorPosition.CLIMB));
    //    controller.a().onTrue(new DeployClimber(climber));
    controller.x().onTrue(new ByeByeAllDone(byebyeTilt, byebyeRoller, elevatorSubsystem));
    controller.y().onTrue(new RemoveAlgaeFromReef(byebyeTilt, byebyeRoller, elevatorSubsystem));
    // new Trigger(() -> controller.getRightY() > Constants.CLIMBER_DEADBAND)
    //     .onTrue(new DeployHarpoon(climber, elevatorSubsystem, lightStrip,
    // ElevatorPosition.LEVEL1));

    // new Trigger(() -> controller.getRightY() < -Constants.CLIMBER_DEADBAND)
    //     .onTrue(new ClimbToLimit(climber, Constants.CLIMBER_PHASE2_SPEED));
    controller
        .back()
        //            .onTrue(new CancelAll(byebyeTilt, byebyeRoller, elevatorSubsystem,
        // hihiExtender));
        .onTrue(new CancelAll(byebyeTilt, byebyeRoller, elevatorSubsystem, climber));
    joyRight1.onTrue(new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED));
    // climber on Right Trigger
    //    if (Constants.COMMAND_DEBUG) {
    //      SmartDashboard.putData("Roll Algae", new RollAlgae(hihiRoller, 0.5));
    //      SmartDashboard.putData("Climber reset", new ResetClimber(climber));
    //      SmartDashboard.putData("Climber stop", new CloseClimber(climber));
    //    }
  }

  public Command getAutonomousCommand() {
    return autoChooser.getAutoCommand();
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
    Consumer<Pose2d> resetSimulationPoseCallBack;
    if (Constants.currentMode == GameConstants.Mode.REAL) {
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
      resetSimulationPoseCallBack = (pose) -> {};
    } else if (Constants.currentMode == GameConstants.Mode.REPLAY) {
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
      resetSimulationPoseCallBack = (pose) -> {};
    } else {

      driveSimulation =
          new SwerveDriveSimulation(
              SwerveSimulationUtils.simulationConfig(), new Pose2d(0, 0, new Rotation2d()));
      resetSimulationPoseCallBack = driveSimulation::setSimulationWorldPose;
      SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
      SwerveModuleSimulation[] driveModules = driveSimulation.getModules();

      frontLeft =
          new SwerveModule(
              new SimDriveMotorIO(kConfig, driveModules[0]),
              new SimSteerMotorIO(driveModules[0]),
              new SimAbsIO(driveModules[0]),
              pidConfig,
              "frontLeft");
      frontRight =
          new SwerveModule(
              new SimDriveMotorIO(kConfig, driveModules[1]),
              new SimSteerMotorIO(driveModules[1]),
              new SimAbsIO(driveModules[1]),
              pidConfig,
              "frontRight");
      backLeft =
          new SwerveModule(
              new SimDriveMotorIO(kConfig, driveModules[2]),
              new SimSteerMotorIO(driveModules[2]),
              new SimAbsIO(driveModules[2]),
              pidConfig,
              "backLeft");
      backRight =
          new SwerveModule(
              new SimDriveMotorIO(kConfig, driveModules[3]),
              new SimSteerMotorIO(driveModules[3]),
              new SimAbsIO(driveModules[3]),
              pidConfig,
              "backRight");
      gyroIO = new SimGyroIO(driveSimulation.getGyroSimulation());
      apriltagIO = new MockApriltag();
    }
    drivetrain =
        new SwerveDrivetrain(
            frontLeft,
            frontRight,
            backLeft,
            backRight,
            gyroIO,
            apriltagIO,
            resetSimulationPoseCallBack);
  }

  public SwerveDrivetrain getDrivetrain() {
    return drivetrain;
  }

  public RobotVisualizer getRobotVisualizer() {
    return robotVisualizer;
  }

  public void putShuffleboardCommands() {

    if (Constants.CORAL_DEBUG) {
      SmartDashboard.putData(
          "Shoot Coral", new ShootCoral(coralSubsystem, elevatorSubsystem::getStoredReefPosition));
      SmartDashboard.putData("Intake Coral", new IntakeCoral(coralSubsystem));

      SmartDashboard.putData(
          "Pick Up Coral",
          new PickUpCoral(elevatorSubsystem, byebyeTilt, byebyeRoller, coralSubsystem, lightStrip));
    }

    //    if (Constants.HIHI_DEBUG) {
    // HiHi Commads

    // SmartDashboard.putData("Extend HiHi", new ExtendHiHi(hihiExtender));

    //  SmartDashboard.putData("Retract HiHi", new RetractHiHi(hihiExtender));

    // SmartDashboard.putData("Roll HiHi Roller In", new RollHiHiRollerIn(hihiRoller));

    // SmartDashboard.putData("Roll HiHi Roller Out", new ShootHiHiRollerOut(hihiRoller));

    // SmartDashboard.putData("Intake Algae", new IntakeAlgae(hihiExtender, hihiRoller));
    //    }

    if (Constants.BYEBYE_DEBUG) {
      // ByeBye Commands

      SmartDashboard.putData("ByeBye To FWD Limit", new ByeByeToFwrLimit(byebyeTilt));

      SmartDashboard.putData(
          "ByeBye To REV Limit", new ByeByeToRevLimit(byebyeTilt, elevatorSubsystem));
    }

    if (Constants.ELEVATOR_DEBUG) {
      // Elevator Commands
      SmartDashboard.putData(
          "SetElevatorSetpointTo0", new SetElevatorTargetPosition(() -> 0, elevatorSubsystem));
      SmartDashboard.putData("RestElevatorEncoder", new ResetElevatorEncoder(elevatorSubsystem));

      SmartDashboard.putData("Reset Elevator", new ResetElevator(elevatorSubsystem));

      SmartDashboard.putData(
          "Elevator To Position", new ElevatorToStoredPosition(elevatorSubsystem));

      SmartDashboard.putData(
          "Store L0",
          new SetElevatorStoredPosition(
              ElevatorPosition.CORAL_INTAKE, elevatorSubsystem, lightStrip));
      SmartDashboard.putData(
          "Store L1",
          new SetElevatorStoredPosition(ElevatorPosition.LEVEL1, elevatorSubsystem, lightStrip));
      SmartDashboard.putData(
          "Store L2",
          new SetElevatorStoredPosition(ElevatorPosition.LEVEL2, elevatorSubsystem, lightStrip));
      SmartDashboard.putData(
          "Store L3",
          new SetElevatorStoredPosition(ElevatorPosition.LEVEL3, elevatorSubsystem, lightStrip));
      SmartDashboard.putData(
          "Store L4",
          new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip));
    }

    if (Constants.CLIMBER_DEBUG) {
      SmartDashboard.putData(new ClimbToLimit(climber, Constants.CLIMBER_PHASE2_SPEED));
      SmartDashboard.putData(
          new DeployHarpoon(climber, elevatorSubsystem, lightStrip, ElevatorPosition.CLIMB));
      // Climber Commands

      //      SmartDashboard.putData( "Reset Climber", new ResetClimber(climber));
      //
      //      SmartDashboard.putData( "Close Climber", new CloseClimber(climber));
    }

    SmartDashboard.putData(
        "LightStripPatternGreen", new SetLedPattern(lightStrip, BlinkinPattern.BLUE_GREEN));
    SmartDashboard.putData(
        "LightStripPatternViolet", new SetLedPattern(lightStrip, BlinkinPattern.BLUE_VIOLET));
  }

  public void updateSimulation() {
    if (Constants.currentMode == Constants.Mode.SIM) {
      SimulatedArena.getInstance().simulationPeriodic();
      Logger.recordOutput(
          "FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
    }
  }
}
