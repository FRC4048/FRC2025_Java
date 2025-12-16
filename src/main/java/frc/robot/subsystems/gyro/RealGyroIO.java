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
    inputs.blah1 = gyro.getBlah1();
    inputs.worldLinearAccelX = gyro.getWorldLinearAccelX();
    inputs.worldLinearAccelY = gyro.getWorldLinearAccelY();
    inputs.worldLinearAccelZ = gyro.getWorldLinearAccelZ();
    inputs.velocityX = gyro.getVelocityX();
    inputs.velocityY = gyro.getVelocityY();
    inputs.velocityZ = gyro.getVelocityZ();
    inputs.displacementX = gyro.getDisplacementX();
    inputs.displacementY = gyro.getDisplacementY();
    inputs.displacementZ = gyro.getDisplacementZ();
    inputs.rawGyroX = gyro.getRawGyroX();
    inputs.rawGyroY = gyro.getRawGyroY();
    inputs.rawGyroZ = gyro.getRawGyroZ();
    inputs.rawAccelX = gyro.getRawAccelX();
    inputs.rawAccelY = gyro.getRawAccelY();
    inputs.rawAccelZ = gyro.getRawAccelZ();
    inputs.rawMagX = gyro.getRawMagX();
    inputs.rawMagY = gyro.getRawMagY();
    inputs.rawMagZ = gyro.getRawMagZ();
    inputs.yaw = gyro.getYaw();
    inputs.pitch = gyro.getPitch();
    inputs.roll = gyro.getRoll();
    inputs.compassHeading = gyro.getCompassHeading();
    inputs.fusedHeading = gyro.getFusedHeading();
    inputs.robotCentricVelocityX = gyro.getRobotCentricVelocityX();
    inputs.robotCentricVelocityY = gyro.getRobotCentricVelocityY();
    inputs.robotCentricVelocityZ = gyro.getRobotCentricVelocityZ();
  }
}
