package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;

/**
 * POJO containing wheel encoder standard deviation and vision standard deviation
 */
public record PoseDeviation(Vector<N3> wheelStd, Vector<N3> visionStd) {
}
