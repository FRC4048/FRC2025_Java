package frc.robot.subsystems.CoralAngle;

public class MockCoralAngleIO implements CoralAngleIO {

  @Override
  public void stopTiltMotors() {}

  @Override
  public void resetTiltEncoder() {}

  @Override
  public void setTiltAngularVelocity(double angleVelocity) {}
  @Override
  public void updateInputs(CoralAngleInput inputs) {}
}
