package frc.robot.subsystems.swervev3.io;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.abs.CANCoderAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.subsystems.swervev3.io.drive.SparkMaxDriveMotorIO;
import frc.robot.subsystems.swervev3.io.drive.SwerveDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SparkMaxSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SwerveSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.math.AngleUtils;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;

import static edu.wpi.first.units.Units.*;

public class SimSwerveModule{
    private final SwerveModuleSimulation moduleSimulation;
    private final SwerveModuleSimulationConfig config;
    private final LoggableSystem<SwerveDriveMotorIO, MotorInputs> driveSystem;
    private final LoggableSystem<SwerveSteerMotorIO, MotorInputs> steerSystem;
    private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
    private final PIDController drivePIDController;
    private final ProfiledPIDController turningPIDController;
    private final SimpleMotorFeedforward driveFeedforward;
    private final SimpleMotorFeedforward turnFeedforward;
    private boolean driveClosedLoop = false;
    private boolean turnClosedLoop = false;
    private double steerOffset;
    private double driveFFVolts = 0.0;
    private double driveAppliedVolts = 0.0;
    private double turnAppliedVolts = 0.0;

    public SimSwerveModule(SwerveDriveMotorIO driveMotorIO,
                           SwerveSteerMotorIO steerMotorIO,
                           SwerveAbsIO absIO,
                           PID drivePid,
                           PID turnPid,
                           Gain driveGain,
                           Gain turnGain,
                           TrapezoidProfile.Constraints goalConstraint,
                           String moduleName) {
        MotorInputs driveInputs =
                new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
        MotorInputs steerInputs =
                new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
        this.driveSystem = new LoggableSystem<>(driveMotorIO, driveInputs);
        this.steerSystem = new LoggableSystem<>(steerMotorIO, steerInputs);
        this.absSystem = new LoggableSystem<>(absIO, new SwerveAbsInput("Drivetrain/" + moduleName));
        drivePIDController = new PIDController(drivePid.getP(), drivePid.getI(), drivePid.getD());
        turningPIDController =
                new ProfiledPIDController(turnPid.getP(), turnPid.getI(), turnPid.getD(), goalConstraint);
        driveFeedforward = new SimpleMotorFeedforward(driveGain.getS(), driveGain.getV());
        driveFFVolts = driveFeedforward.getKv();
        turnFeedforward = new SimpleMotorFeedforward(turnGain.getS(), turnGain.getV());
        turningPIDController.enableContinuousInput(0, Math.PI * 2);
        this.config = new SwerveModuleSimulationConfig(driveSystem.getIO());
        this.moduleSimulation = new SwerveModuleSimulation(config);
    }

    public void updateInputs() {
        // Run closed-loop control
        if (driveClosedLoop) {
            driveAppliedVolts = driveFFVolts
                    + drivePIDController.calculate(
                    moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
        } else {
            drivePIDController.reset();
        }
        if (turnClosedLoop) {
            turnAppliedVolts = turningPIDController.calculate(
                    moduleSimulation.getSteerAbsoluteFacing().getRadians());
        } else {
            turningPIDController.reset(absSystem.getInputs().absEncoderPosition); // TODO: might be wrong
        }

        // Update simulation state
        driveSystem.getIO().setDriveVoltage(driveAppliedVolts);
        steerSystem.getIO().setSteerVoltage(turnAppliedVolts);

        // Update drive inputs
        driveSystem.updateInputs();

        // Update turn inputs
        steerSystem.updateInputs();

        // Update abs inputs
        absSystem.updateInputs();

//        // Update odometry inputs
//        inputs.odometryTimestamps = SparkUtil.getSimulationOdometryTimeStamps();
//        inputs.odometryDrivePositionsRad = Arrays.stream(moduleSimulation.getCachedDriveWheelFinalPositions())
//                .mapToDouble(angle -> angle.in(Radians))
//                .toArray();
//        inputs.odometryTurnPositions = moduleSimulation.getCachedSteerAbsolutePositions(); // TODO: UNCOMMENT
    }


    public void setDriveOpenLoop(double output) {
        driveClosedLoop = false;
        driveAppliedVolts = output;
    }


    public void setTurnOpenLoop(double output) {
        turnClosedLoop = false;
        turnAppliedVolts = output;
    }


    public void setDriveVelocity(double velocityRadPerSec) {
        driveClosedLoop = true;
        driveFFVolts = Constants.DRIVE_PID_FF_S * Math.signum(velocityRadPerSec) + Constants.DRIVE_PID_FF_V * velocityRadPerSec;
        drivePIDController.setSetpoint(velocityRadPerSec);
    }


    public void setTurnPosition(Rotation2d rotation) {
        turnClosedLoop = true;
        turningPIDController.setGoal(rotation.getRadians());
    }
    public void setState(SwerveModuleState desiredState) {
        double steerEncoderPosition = getSteerPosition();
        SwerveModuleState state =
                SwerveModuleState.optimize(desiredState, new Rotation2d(steerEncoderPosition));
        double driveSpeed =
                drivePIDController.calculate(
                        driveSystem.getInputs().getEncoderVelocity(), (state.speedMetersPerSecond))
                        + driveFeedforward.calculate(state.speedMetersPerSecond);
        double turnSpeed =
                turningPIDController.calculate(steerEncoderPosition, state.angle.getRadians())
                        + turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);
        driveSystem.getIO().setDriveVoltage(driveSpeed);
        steerSystem.getIO().setSteerVoltage(turnSpeed * 12);
    }

    public SwerveModuleState getLatestState() {
        return new SwerveModuleState(
                driveSystem.getInputs().getEncoderVelocity(), new Rotation2d(getSteerPosition()));
    }


    public void stop() {
        driveSystem.getIO().setDriveVoltage(0);
        steerSystem.getIO().setSteerVoltage(0);
    }

    public void resetRelativeEnc() {
        driveSystem.getIO().resetEncoder();
        steerSystem.getIO().resetEncoder();
    }

    public void setSteerOffset(double zeroAbs) {
        steerSystem.getIO().resetEncoder();
        double offset = AngleUtils.normalizeAbsAngleRadians(zeroAbs - getAbsPosition());
        steerOffset = AngleUtils.normalizeSwerveAngle(offset);
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                driveSystem.getInputs().getEncoderPosition(), new Rotation2d(getSteerPosition()));
    }

    public static SimSwerveModule createModule(
            SwerveIdConfig idConf,
            KinematicsConversionConfig kinematicsConfig,
            SwervePidConfig pidConfig,
            ModulePosition position,
            boolean driveInverted) {
        SparkMaxDriveMotorIO frontLeftDriveMotorIO =
                new SparkMaxDriveMotorIO(idConf.getDriveMotorId(), kinematicsConfig, driveInverted);
        SparkMaxSteerMotorIO frontLeftSteerMotorIO =
                new SparkMaxSteerMotorIO(
                        idConf.getTurnMotorId(),
                        kinematicsConfig,
                        kinematicsConfig.getProfile().isSteerInverted());
        CANCoderAbsIO frontLeftAbsIO = new CANCoderAbsIO(idConf.getCanCoderId());
        return new SimSwerveModule(
                frontLeftDriveMotorIO,
                frontLeftSteerMotorIO,
                frontLeftAbsIO,
                pidConfig,
                position.getLoggingKey());
    }

    public double getAbsPosition() {
        return absSystem.getInputs().absEncoderPosition;
    }

    private double getSteerPosition() {
        return AngleUtils.normalizeSwerveAngle(
                steerSystem.getInputs().getEncoderPosition() - steerOffset);
    }
}

