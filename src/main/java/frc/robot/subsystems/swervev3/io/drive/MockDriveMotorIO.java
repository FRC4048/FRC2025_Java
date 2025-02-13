package frc.robot.subsystems.swervev3.io.drive;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockDriveMotorIO implements SwerveDriveMotorIO {

  @Override
  public void setDriveVoltage(double volts) {}

  @Override
  public void resetEncoder() {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}
}
