package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;

public interface VisionTruster {
  Vector<N3> calculateTrust(VisionMeasurement measurement);
}
