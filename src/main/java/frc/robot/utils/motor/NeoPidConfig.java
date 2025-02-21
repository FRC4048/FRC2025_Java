package frc.robot.utils.motor;

public class NeoPidConfig {
  private int id = 0;
  private double p = NeoPidMotor.DEFAULT_P;
  private double i = NeoPidMotor.DEFAULT_I;
  private double d = NeoPidMotor.DEFAULT_D;
  private double iZone = NeoPidMotor.DEFAULT_IZONE;
  private double ff = NeoPidMotor.DEFAULT_FF;
  private int currentLimit = 40;
  private double maxVelocity = NeoPidMotor.MAX_VELOCITY;
  private double maxAccel = NeoPidMotor.MAX_ACCELERATION;
  private double allowedError = NeoPidMotor.ALLOWED_ERROR;

  public int getId() {
    return id;
  }

  public NeoPidConfig setId(int id) {
    this.id = id;
    return this;
  }

  public double getP() {
    return p;
  }

  public NeoPidConfig setP(double p) {
    this.p = p;
    return this;
  }

  public double getI() {
    return i;
  }

  public NeoPidConfig setI(double i) {
    this.i = i;
    return this;
  }

  public double getD() {
    return d;
  }

  public NeoPidConfig setD(double d) {
    this.d = d;
    return this;
  }

  public double getIZone() {
    return iZone;
  }

  public NeoPidConfig setIZone(double iZone) {
    this.iZone = iZone;
    return this;
  }

  public double getFF() {
    return ff;
  }

  public NeoPidConfig setFF(double ff) {
    this.ff = ff;
    return this;
  }

  public int getCurrentLimit() {
    return currentLimit;
  }

  public NeoPidConfig setCurrentLimit(int currentLimit) {
    this.currentLimit = currentLimit;
    return this;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public NeoPidConfig setMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
    return this;
  }

  public double getMaxAccel() {
    return maxAccel;
  }

  public NeoPidConfig setMaxAccel(double maxAccel) {
    this.maxAccel = maxAccel;
    return this;
  }

  public double getAllowedError() {
    return allowedError;
  }

  public NeoPidConfig setAllowedError(double allowedError) {
    this.allowedError = allowedError;
    return this;
  }

  public NeoPidConfig setPid(double p, double i, double d) {
    setP(p).setI(i).setD(d);
    return this;
  }

  public NeoPidConfig setPidf(double p, double i, double d, double ff) {
    setPid(p, i, d).setFF(ff);
    return this;
  }

  public NeoPidConfig setTrapezoidConstructions(double maxVelocity, double maxAccel) {
    setMaxVelocity(maxVelocity).setMaxAccel(maxAccel);
    return this;
  }
}
