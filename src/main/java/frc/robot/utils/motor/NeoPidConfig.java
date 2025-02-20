package frc.robot.utils.motor;

public class NeoPidConfig {
  public static final double DEFAULT_P = 0.01;
  public static final double DEFAULT_I = 0;
  public static final double DEFAULT_D = 0.0;
  public static final double DEFAULT_IZONE = 0.0;
  public static final double DEFAULT_FF = 0.0;
  public static final double MAX_VELOCITY = 5000;
  public static final double MAX_ACCELERATION = 10000;
  public static final double ALLOWED_ERROR = 1.0;

  private double p = DEFAULT_P;
  private double i = DEFAULT_I;
  private double d = DEFAULT_D;
  private double iZone = DEFAULT_IZONE;
  private double ff = DEFAULT_FF;
  private int neoCurrentLimit = 40;
  private int neo550CurrentLimit = 20;
  private int currentLimit;
  private double maxVelocity = MAX_VELOCITY;
  private double maxAccel = MAX_ACCELERATION;
  private double allowedError = ALLOWED_ERROR;

  public NeoPidConfig(boolean is550) {
    this.currentLimit = is550 ? neo550CurrentLimit : neoCurrentLimit;
  }

  public NeoPidConfig setP(double p) {
    this.p = p;
    return this;
  }

  public NeoPidConfig setI(double i) {
    this.i = i;
    return this;
  }

  public NeoPidConfig setD(double d) {
    this.d = d;
    return this;
  }

  public NeoPidConfig setIZone(double iZone) {
    this.iZone = iZone;
    return this;
  }

  public NeoPidConfig setFF(double ff) {
    this.ff = ff;
    return this;
  }

  public NeoPidConfig setCurrentLimit(int currentLimit) {
    this.currentLimit = currentLimit;
    return this;
  }

  public NeoPidConfig setMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
    return this;
  }

  public NeoPidConfig setMaxAccel(double maxAccel) {
    this.maxAccel = maxAccel;
    return this;
  }

  public NeoPidConfig setAllowedError(double allowedError) {
    this.allowedError = allowedError;
    return this;
  }

  public double getP() {
    return p;
  }

  public double getI() {
    return i;
  }

  public double getD() {
    return d;
  }

  public double getIZone() {
    return iZone;
  }

  public double getFF() {
    return ff;
  }

  public int getCurrentLimit() {
    return currentLimit;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public double getMaxAccel() {
    return maxAccel;
  }

  public double getAllowedError() {
    return allowedError;
  }
}
