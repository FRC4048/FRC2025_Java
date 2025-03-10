package frc.robot.utils.motor;

public record PID(double p, double i, double d) {

  public static PID of(double p, double i, double d) {
    return new PID(p, i, d);
  }
}
