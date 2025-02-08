package frc.robot.subsystems.coral;

public class MockCoralIO implements CoralIO {
  @Override
  public void setShooterSpeed(double speed) {}

  @Override
  public void setTiltAngularVelocity(double angleSpeed) {}

  @Override
  public void stopShooterMotors() {}

  @Override
  public void stopTiltMotors() {}

  @Override
  public void resetTiltEncoder() {}

  @Override
  public void updateInputs(CoralInputs inputs) {}

  @Override
  public void toggleLimitSwitch(boolean state) {}
}
