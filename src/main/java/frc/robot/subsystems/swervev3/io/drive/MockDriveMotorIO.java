package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class MockDriveMotorIO implements SwerveDriveMotorIO {

  @Override
  public void setDriveVoltage(double volts) {}

  @Override
  public void resetEncoder() {}

  @Override
  public void updateInputs(MotorInputs inputs) {}
}
