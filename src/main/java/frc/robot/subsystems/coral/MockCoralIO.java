package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockCoralIO implements CoralIO {
  @Override
  public void setShooterSpeed(double speed) {}

  @Override
  public void stopShooterMotors() {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {}

  @Override
  public void breakModeCoast(boolean breakMode) {}
}
