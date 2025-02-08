package frc.robot.subsystems.swervev3.io.drive;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public interface SwerveDriveMotorIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {
  void setDriveVoltage(double volts);

  void resetEncoder();
}
