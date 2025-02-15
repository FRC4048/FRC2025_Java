package frc.robot.subsystems.coral;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class MockCoralIO implements CoralIO {
  @Override
  public void setShooterSpeed(double speed) {}

  @Override
  public void stopShooterMotors() {}

  @Override
  public void updateInputs(MotorInputs inputs) {}

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {}

  @Override
  public void setIdleMode(IdleMode mode) {}
}
