package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockHihiRollerIO implements HihiRollerIO {
  @Override
  public void setRollerSpeed(double speed) {}

  @Override
  public void stopRollerMotor() {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}
}
