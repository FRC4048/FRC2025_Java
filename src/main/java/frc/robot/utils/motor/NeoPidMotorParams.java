package frc.robot.utils.motor;

public class NeoPidMotorParams {
  public double pidP;
  public double pidI;
  public double pidD;
  public double iZone;
  public double pidFF;
  public int currentLimit;
  public double maxVelocity;
  public double maxAccel;
  public double allowedError;

  public NeoPidMotorParams() {}

  public NeoPidMotorParams(
      double pidP,
      double pidI,
      double pidD,
      double iZone,
      double pidFF,
      int currentLimit,
      double maxVelocity,
      double maxAccel,
      double allowedError) {
    this.pidD = pidP;
    this.pidI = pidI;
    this.pidD = pidD;
    this.iZone = iZone;
    this.pidFF = pidFF;
    this.currentLimit = currentLimit;
    this.maxVelocity = maxVelocity;
    this.maxAccel = maxAccel;
    this.allowedError = allowedError;
  }

  public static NeoPidMotorParams defaultParams() {
    return new NeoPidMotorParams(
        NeoPidMotor.DEFAULT_P,
        NeoPidMotor.DEFAULT_I,
        NeoPidMotor.DEFAULT_D,
        NeoPidMotor.DEFAULT_IZONE,
        NeoPidMotor.DEFAULT_FF,
        40,
        NeoPidMotor.MAX_VELOCITY,
        NeoPidMotor.MAX_ACCELERATION,
        NeoPidMotor.ALLOWED_ERROR);
  }
}
