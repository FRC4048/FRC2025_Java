package frc.robot.utils.logging.subsystem.providers;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.motor.SparkUtil;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SteerModuleSimInputProvider implements MotorInputProvider {
  private final SwerveModuleSimulation moduleSimulation;
  private final KinematicsConversionConfig conversionConfig;

  public SteerModuleSimInputProvider(
      SwerveModuleSimulation moduleSimulation, KinematicsConversionConfig conversionConfig) {
    this.moduleSimulation = moduleSimulation;
    this.conversionConfig = conversionConfig;
  }

  @Override
  public double getMotorTemperature() {
    return 25.0;
  }

  @Override
  public boolean getFwdLimit() {
    return false;
  }

  @Override
  public boolean getRevLimit() {
    return false;
  }

  public boolean isSteerConnected() {
    return true;
  }

  @Override
  public double getMotorCurrent() {
    return Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps));
  }

  @Override
  public double getEncoderPosition() {
    return moduleSimulation.getSteerAbsoluteFacing().getRadians()
        / conversionConfig.getProfile().getSteerGearRatio();
  }

  @Override
  public double getEncoderVelocity() {
    return moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond)
        / conversionConfig.getProfile().getSteerGearRatio();
  }

  @Override
  public double getAppliedOutput() {
    return moduleSimulation.getSteerMotorAppliedVoltage().in(Volts);
  }

  public double[] getOdometryTimestamps() {
    return SparkUtil.getSimulationOdometryTimeStamps();
  }

  public Rotation2d[] getOdometryTurnPositions() {
    return moduleSimulation.getCachedSteerAbsolutePositions();
  }
}
