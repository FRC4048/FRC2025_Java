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

package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public interface VisionIO extends LoggableIO<VisionIO.VisionIOInputs> {
  class VisionIOInputs extends FolderLoggableInputs {
    public VisionIOInputs(String folder) {
      super(folder);
    }

    public boolean connected = false;
    public TargetObservation latestTargetObservation =
        new TargetObservation(new Rotation2d(), new Rotation2d());
    public PoseObservation[] poseObservations = new PoseObservation[0];
    public int[] tagIds = new int[0];

    @Override
    public void toLog(LogTable table) {
      table.put("connected", connected);
      table.put("latestTargetObservation", latestTargetObservation);
      table.put("tagIds.length", tagIds.length);
      table.put("poseObservations", poseObservations);
    }

    @Override
    public void fromLog(LogTable table) {
      connected = table.get("connected", connected);
      latestTargetObservation = table.get("latestTargetObservation", latestTargetObservation);
      tagIds = table.get("tagIds", tagIds);
      poseObservations = table.get("poseObservations", poseObservations);
    }
  }

  /** Represents the angle to a simple target, not used for pose estimation. */
  record TargetObservation(Rotation2d tx, Rotation2d ty) {}

  /** Represents a robot pose sample used for pose estimation. */
  record PoseObservation(
      double timestamp,
      Pose3d pose,
      double ambiguity,
      int tagCount,
      double averageTagDistance,
      PoseObservationType type) {}

  enum PoseObservationType {
    MEGATAG_1,
    MEGATAG_2,
    PHOTONVISION
  }
}
