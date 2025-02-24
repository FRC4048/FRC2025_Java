package frc.robot.subsystems.gyro;

import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagGyro;

public class RealGyroIO implements GyroIO {
  private final ThreadedGyro gyro;

  public RealGyroIO(ThreadedGyro gyro) {
    this.gyro = gyro;
    Robot.getDiagnostics()
        .addDiagnosable(new DiagGyro("Gyro", "Gyro Angle", Constants.GYRO_DIAGS_ANGLE, gyro));
  }

  @Override
  public void setAngleOffset(double offset) {
    gyro.setAngleAdjustment(offset);
  }

  @Override
  public void resetGyro() {
    gyro.resetGyro();
  }

  @Override
  public void updateInputs(GyroInputs inputs) {
    inputs.anglesInDeg = gyro.getGyroValue();
    inputs.angleOffset = gyro.getAngleOffset();
  }
}
