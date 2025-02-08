package frc.robot.subsystems.swervev3.io.steer;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockSteerMotorIO implements SwerveSteerMotorIO {

  @Override
  public void setSteerVoltage(double volts) {}

  @Override
  public void resetEncoder() {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}
}
