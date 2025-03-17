package frc.robot.utils.logging.subsystem.providers;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimDriveMotorInputProvider implements MotorInputProvider {
  private final SwerveModuleSimulation moduleSimulation;
  private final KinematicsConversionConfig conversionConfig;

  public SimDriveMotorInputProvider(
      SwerveModuleSimulation moduleSimulation, KinematicsConversionConfig conversionConfig) {
    this.moduleSimulation = moduleSimulation;
    this.conversionConfig = conversionConfig;
  }

  @Override
  public double getMotorCurrent() {
    return Math.abs(moduleSimulation.getDriveMotorStatorCurrent().in(Amps));
  }

  /**
   * @return 25
   */
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

  @Override
  public double getEncoderVelocity() {
    return moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond)
        * (conversionConfig.getWheelRadius());
  }

  @Override
  public double getEncoderPosition() {
    return moduleSimulation.getDriveWheelFinalPosition().in(Radians)
        * (conversionConfig.getWheelRadius());
  }

  @Override
  public double getAppliedOutput() {
    return moduleSimulation.getDriveMotorAppliedVoltage().in(Volts);
  }
}
