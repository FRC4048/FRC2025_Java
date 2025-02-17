package frc.robot.subsystems.coral;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class MockCoralIOFollower implements CoralIOFollower {
  
  @Override
  public void updateInputs(MotorInputs inputs) {}

  @Override
  public void setIdleMode(IdleMode mode) {}
}
