package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;

public class MockHihiRollerIO implements HihiRollerIO {
  @Override
  public void setRollerSpeed(double speed) {}

  @Override
  public void stopRollerMotor() {}

  @Override
  public void updateInputs(BuildableKeyedInputs<SparkMax> inputs) {}
}
