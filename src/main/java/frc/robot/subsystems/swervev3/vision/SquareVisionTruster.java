package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;

public class SquareVisionTruster extends DistanceVisionTruster {

  private final double constant;

  public SquareVisionTruster(Vector<N3> initialSTD, double constant) {
    super(initialSTD);
    this.initialSTD = initialSTD;
    this.constant = constant;
  }

  @Override
  public Vector<N3> calculateTrust(VisionMeasurement measurement) {
    double std = Math.pow(measurement.distanceFromTag(), 2) * constant;
    return initialSTD.plus(VecBuilder.fill(std, std, std));
  }
}
