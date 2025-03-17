package frc.robot.utils.logging.subsystem.providers;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimSteerMotorInputProvider implements MotorInputProvider {
  private final SwerveModuleSimulation moduleSimulation;

  public SimSteerMotorInputProvider(SwerveModuleSimulation moduleSimulation) {
    this.moduleSimulation = moduleSimulation;
  }

  /*This will always return 25 because I don't think we can simulate temperature currently */
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
  public double getMotorCurrent() {
    return Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps));
  }

  @Override
  public double getEncoderPosition() {
    return moduleSimulation.getSteerAbsoluteFacing().getRadians();
  }

  @Override
  public double getEncoderVelocity() {
    return moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond);
  }

  @Override
  public double getAppliedOutput() {
    return moduleSimulation.getSteerMotorAppliedVoltage().in(Volts);
  }
}
