package frc.robot.utils.motor;

public record Gain(double v, double s) {

  public static Gain of(double v, double s) {
    return new Gain(v, s);
  }
}
