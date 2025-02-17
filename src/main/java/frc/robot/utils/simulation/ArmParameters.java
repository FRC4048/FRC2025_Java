package frc.robot.utils.simulation;

import edu.wpi.first.math.geometry.Rotation2d;

public class ArmParameters {
  public String name = "HiHi Extender";
  public double armGearing = 45;
  public double armInertia = 1;
  public double armLength = 0.5;
  public Rotation2d armMinAngle = Rotation2d.fromRadians(-Math.PI / 2);
  public Rotation2d armMaxAngle = Rotation2d.fromRadians(Math.PI * 5 / 12);
  public boolean armSimulateGravity = true;
}
