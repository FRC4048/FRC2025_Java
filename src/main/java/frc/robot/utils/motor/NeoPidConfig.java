package frc.robot.utils.motor;

public class NeoPidConfig {
  private int id = 0;
  private double p = 1e-2;
  private double i = 0.0;
  private double d = 0.0;
  private double iZone = 0.0;
  private double ff = 0.0;
  private int currentLimit = 40;

  public NeoPidConfig setId(int id) {
    this.id = id;
    return this;
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

  public int getId() {
    return id;
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
}
