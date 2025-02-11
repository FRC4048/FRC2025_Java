package frc.robot.subsystems.coral;

public class MockCoralIO implements CoralIO {
  @Override
  public void setShooterSpeed(double speed) {}

  @Override
  public void stopShooterMotors() {}

  @Override
  public void updateInputs(CoralInputs inputs) {}

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {}
}
