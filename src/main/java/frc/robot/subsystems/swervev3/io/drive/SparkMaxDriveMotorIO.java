package frc.robot.subsystems.swervev3.io.drive;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.processors.SparkMaxInputSource;

public class SparkMaxDriveMotorIO implements SwerveDriveMotorIO {

  private final SparkMax driveMotor;
  private final SparkBaseConfig driveConfig;
  private final SparkMaxInputSource inputSource;

  public SparkMaxDriveMotorIO(
      int driveMotorIO, KinematicsConversionConfig conversionConfig, boolean driveInverted) {
    driveMotor = new SparkMax(driveMotorIO, SparkMax.MotorType.kBrushless);
    inputSource = new SparkMaxInputSource(driveMotor);
    driveConfig = new SparkMaxConfig();
    setMotorConfig(driveInverted);
    setConversionFactors(conversionConfig);
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveMotor.setVoltage(volts);
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double driveVelConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio() * 60);
    double drivePosConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio());
    driveConfig
        .encoder
        .positionConversionFactor(drivePosConvFactor)
        .velocityConversionFactor(driveVelConvFactor);
    driveMotor.configure(
        driveConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  private void setMotorConfig(boolean driveInverted) {
    // driveMotor.restoreFactoryDefaults(); //TODO: idk what to do
    driveConfig
        .inverted(driveInverted)
        .idleMode(IdleMode.kBrake)
        .closedLoopRampRate(Constants.DRIVE_RAMP_RATE_LIMIT)
        .secondaryCurrentLimit(Constants.DRIVE_SECONDARY_LIMIT)
        .smartCurrentLimit(
            Constants.DRIVE_SMART_LIMIT); // TODO: change current limiting because its different
    driveMotor.configure(
        driveConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void resetEncoder() {
    driveMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputSource);
  }
}
