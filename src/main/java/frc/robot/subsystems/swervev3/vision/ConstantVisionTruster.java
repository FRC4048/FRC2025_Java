package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;

public class ConstantVisionTruster extends DistanceVisionTruster {

  public ConstantVisionTruster(Vector<N3> initialSTD) {
    super(initialSTD);
  }

  @Override
  public Vector<N3> calculateTrust(VisionMeasurement measurement) {
    return initialSTD;
  }
}
