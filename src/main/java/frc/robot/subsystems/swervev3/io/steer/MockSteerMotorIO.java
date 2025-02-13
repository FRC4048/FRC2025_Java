package frc.robot.subsystems.swervev3.io.steer;

import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public class MockSteerMotorIO implements SwerveSteerMotorIO {

  @Override
  public void setSteerVoltage(double volts) {}

  @Override
  public void resetEncoder() {}

  @Override
  public void updateInputs(MotorInputs inputs) {}
}
