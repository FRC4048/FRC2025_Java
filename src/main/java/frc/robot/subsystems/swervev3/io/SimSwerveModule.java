package frc.robot.subsystems.swervev3.io;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.subsystems.swervev3.io.drive.SwerveDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SwerveSteerMotorIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

import static edu.wpi.first.units.Units.*;

public class SimSwerveModule {
    private final SwerveModuleSimulation moduleSimulation;
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

    public SimSwerveModule(SwerveModuleSimulation moduleSimulation, SwerveDriveMotorIO driveMotorIO,
                           SwerveSteerMotorIO steerMotorIO,
                           SwerveAbsIO absIO,
                           PID drivePid,
                           PID turnPid,
                           Gain driveGain,
                           Gain turnGain,
                           TrapezoidProfile.Constraints goalConstraint,
                           String moduleName) {
        this.moduleSimulation = moduleSimulation;
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
        turnFeedforward = new SimpleMotorFeedforward(turnGain.getS(), turnGain.getV());
        turningPIDController.enableContinuousInput(0, Math.PI * 2);
    }

    public void updateInputs(ModuleIOInputs inputs) {
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
            turningPIDController.reset();
        }

        // Update simulation state
        driveSystem.getIO().setDriveVoltage(driveAppliedVolts);
        steerSystem.getIO().setSteerVoltage(turnAppliedVolts));

        // Update drive inputs
        inputs.driveConnected = true;
        inputs.drivePositionRad = moduleSimulation.getDriveWheelFinalPosition().in(Radians);
        inputs.driveVelocityRadPerSec =
                moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond);
        inputs.driveAppliedVolts = driveAppliedVolts;
        inputs.driveCurrentAmps =
                Math.abs(moduleSimulation.getDriveMotorStatorCurrent().in(Amps));

        // Update turn inputs
        inputs.turnConnected = true;
        inputs.turnPosition = moduleSimulation.getSteerAbsoluteFacing();
        inputs.turnVelocityRadPerSec =
                moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond);
        inputs.turnAppliedVolts = turnAppliedVolts;
        inputs.turnCurrentAmps =
                Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps));

        // Update odometry inputs
        inputs.odometryTimestamps = SparkUtil.getSimulationOdometryTimeStamps();
        inputs.odometryDrivePositionsRad = Arrays.stream(moduleSimulation.getCachedDriveWheelFinalPositions())
                .mapToDouble(angle -> angle.in(Radians))
                .toArray();
        inputs.odometryTurnPositions = moduleSimulation.getCachedSteerAbsolutePositions();
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
}

