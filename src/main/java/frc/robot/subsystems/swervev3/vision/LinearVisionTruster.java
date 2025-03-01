package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;

public class LinearVisionTruster extends DistanceVisionTruster {

  private final double slopeSTD;

  public LinearVisionTruster(Vector<N3> initialSTD, double slopeSTD) {
    super(initialSTD);
    this.slopeSTD = slopeSTD;
  }

  @Override
  public Vector<N3> calculateTrust(VisionMeasurement measurement) {
    double std = measurement.distanceFromTag() * slopeSTD;
    return initialSTD.plus(VecBuilder.fill(std, std, std));
  }
}
