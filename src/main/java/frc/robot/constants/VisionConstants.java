// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.constants;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

public class VisionConstants {
  // AprilTag layout
  public final static AprilTagFieldLayout APRIL_TAG_LAYOUT =
      AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);

  // Camera names, must match names configured on coprocessor
  public final static String CAMERA_0_NAME = "camera_0";
  public final static String CAMERA_1_NAME = "camera_1";

  // Robot to camera transforms
  // (Not used by Limelight, configure in web UI instead)
  public final static Transform3d ROBOT_TO_CAMERA_0 =
      new Transform3d(0.2, 0.0, 0.2, new Rotation3d(0.0, -0.4, 0.0));
  public final static Transform3d ROBOT_TO_CAMERA_1 =
      new Transform3d(-0.2, 0.0, 0.2, new Rotation3d(0.0, -0.4, Math.PI));

  // Basic filtering thresholds
  public final static double MAX_AMBIGUITY = 0.3;
  public final static double MAX_Z_ERROR = 0.75;

  // Standard deviation baselines, for 1 meter distance and 1 tag
  // (Adjusted automatically based on distance and # of tags)
  public final static double LINEAR_STD_DEV_BASELINE = 0.02; // Meters
  public final static double ANGULAR_STD_DEV_BASELINE = 0.06; // Radians

  // Standard deviation multipliers for each camera
  // (Adjust to trust some cameras more than others)
  public final static double[] CAMERA_STD_DEV_FACTORS =
      new double[] {
        1.0, // Camera 0
        1.0 // Camera 1
      };

  // Multipliers to apply for MegaTag 2 observations
  public final static double LINEAR_STD_DEV_MEGATAG_2_FACTOR = 0.5; // More stable than full 3D solve
  public final static double ANGULAR_STD_DEV_MEGATAG_2_FACTOR =
      Double.POSITIVE_INFINITY; // No rotation data available
}
