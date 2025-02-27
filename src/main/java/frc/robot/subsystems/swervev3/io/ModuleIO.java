package frc.robot.subsystems.swervev3.io;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface ModuleIO {
  void updateInputs();

  void setState(SwerveModuleState desiredState);

  SwerveModuleState getLatestState();

  void stop();

  void resetRelativeEnc();

  void setSteerOffset(double zeroAbs);

  SwerveModulePosition getPosition();

  double getAbsPosition();
}
