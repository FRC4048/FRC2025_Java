package frc.robot.subsystems.swervev3.io.steer;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;
import frc.robot.utils.motor.SparkUtil;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimSteerMotorIO implements SimSwerveSteerMotorIO {
  private final SparkMax steerMotor;
  private final SparkMaxSim simMotor;
  private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
  private final DCMotor steerGearbox = DCMotor.getNEO(1);
  private final SparkBaseConfig steerConfig;
  private final SparkMaxInputProvider inputProvider;
  private final SwerveModuleSimulation moduleSimulation;
  private final ProfiledPIDController turningController;
  private double turnAppliedVolts = 0;
  private boolean steerClosedLoop = false;

  public SimSteerMotorIO(
      int steerMotorId,
      KinematicsConversionConfig conversionConfig,
      boolean steerInverted,
      SwerveModuleSimulation moduleSimulation,
      ProfiledPIDController turningController,
      LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem) {
    steerMotor = new SparkMax(steerMotorId, SparkMax.MotorType.kBrushless);
    simMotor = new SparkMaxSim(steerMotor, steerGearbox);
    steerConfig = new SparkMaxConfig();
    this.inputProvider = new SparkMaxInputProvider(steerMotor);
    this.moduleSimulation = moduleSimulation;
    this.turningController = turningController;
    this.absSystem = absSystem;
    setMotorConfig(steerInverted);
    setConversionFactors(conversionConfig);
    resetEncoder();
  }

  public void updateInputs(SteerMotorInputs inputs) {
    if (steerClosedLoop) {
      turnAppliedVolts =
          turningController.calculate(moduleSimulation.getSteerAbsoluteFacing().getRadians());
    } else {
      turningController.reset(absSystem.getInputs().absEncoderPosition); // TODO: might be wrong
    }
    steerMotor.setVoltage(turnAppliedVolts);
    inputs.setSteerConnected(true);
    inputs.setEncoderPosition(moduleSimulation.getSteerAbsoluteFacing().getRadians());
    inputs.setEncoderVelocity(moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond));
    inputs.setAppliedOutput(turnAppliedVolts);
    inputs.setMotorCurrent(Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps)));
    inputs.setOdometryTimestamps(SparkUtil.getSimulationOdometryTimeStamps());
    inputs.setOdometryTurnPositions(moduleSimulation.getCachedSteerAbsolutePositions());
    inputs.process(inputProvider);
  }

  private void setMotorConfig(boolean steerInverted) {
    // driveMotor.restoreFactoryDefaults(); //TODO: idk what to do
    steerConfig.inverted(steerInverted).idleMode(SparkBaseConfig.IdleMode.kBrake);
    steerMotor.configure(
        steerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double steerPosConvFactor = 2 * Math.PI / conversionConfig.getProfile().getSteerGearRatio();
    steerConfig
        .encoder
        .positionConversionFactor(steerPosConvFactor)
        .velocityConversionFactor(steerPosConvFactor / 60);
    steerMotor.configure(
        steerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void setSteerVoltage(double volts) {
    steerMotor.setVoltage(volts);
  }

  public void resetEncoder() {
    steerMotor.getEncoder().setPosition(0);
  }

  public void setSteerOpenLoop(double output) {
    steerClosedLoop = false;
    turnAppliedVolts = output;
  }

  public void setTurnPosition(Rotation2d rotation) {
    steerClosedLoop = true;
    turningController.setGoal(rotation.getRadians());
  }
}
