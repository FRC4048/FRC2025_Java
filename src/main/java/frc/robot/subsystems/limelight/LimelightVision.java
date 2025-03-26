// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePosition;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.ElevatorPosition;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.advanced.*;
import frc.robot.utils.advanced.cleaners.StampedMapAgeClearner;
import frc.robot.utils.advanced.cleaners.StampedMapCleaner;
import frc.robot.utils.advanced.cleaners.StampedMapDirtyClearner;
import frc.robot.utils.advanced.trackers.StampedTrackerHashMap;
import frc.robot.utils.logging.subsystem.LoggableSystem;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class LimelightVision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
  private final Supplier<Pose2d> pose2dSupplier;
  private final StampedTrackerHashMap<CoralPosition, BranchStatus> coralTracker;
  private ReefScoringStrategy strategy;
  private StampedMapCleaner<CoralPosition, BranchStatus> coralCleaner;
  private final StampedMapAgeClearner<CoralPosition, BranchStatus> coralMapAgeClearner;
  private final StampedMapDirtyClearner<CoralPosition, BranchStatus> coralMapDirtyClearner;

  private final StampedTrackerHashMap<AlgaePosition, BranchStatus> algaeTracker;
  private StampedMapCleaner<AlgaePosition, BranchStatus> algaeCleaner;
  private final StampedMapAgeClearner<AlgaePosition, BranchStatus> algaeMapAgeClearner;
  private final StampedMapDirtyClearner<AlgaePosition, BranchStatus> algaeMapDirtyClearner;

  public LimelightVision(VisionIO io, Supplier<Pose2d> pose2dSupplier, double objectLifetime) {
    this.system = new LoggableSystem<>(io, new VisionInputs("limelight"));
    this.pose2dSupplier = pose2dSupplier;

    coralMapAgeClearner =
            new StampedMapAgeClearner<>(objectLifetime) {
              @Override
              public void invalidateEntry(
                      Map<CoralPosition, StampedObject<BranchStatus>> map, CoralPosition key) {
                map.put(key, StampedObject.of(BranchStatus.UNKNOWN));
              }
            };
    coralMapDirtyClearner = new StampedMapDirtyClearner<>();
    this.coralTracker = new StampedTrackerHashMap<>();

    algaeMapAgeClearner =
            new StampedMapAgeClearner<>(objectLifetime) {
              @Override
              public void invalidateEntry(
                      Map<AlgaePosition, StampedObject<BranchStatus>> map, AlgaePosition key) {
                map.put(key, StampedObject.of(BranchStatus.UNKNOWN));
              }
            };
    algaeMapDirtyClearner = new StampedMapDirtyClearner<>();
    this.algaeTracker = new StampedTrackerHashMap<>();
  }

  /**
   * @return The piece's x offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double[] getPieceOffsetAngleX(int detectionIndex) {
    return system.getInputs().tx;
  }

  /**
   * @return The piece's y offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double[] getPieceOffsetAngleY() {
    return system.getInputs().ty;
  }

  public boolean isPieceSeen() {
    return system.getInputs().valid;
  }

  @AutoLogOutput
  public CoralPosition[] getAllBranchPosition() {
    return coralTracker.getReadOnlyStampedObjects().keySet().toArray(CoralPosition[]::new);
  }

  @AutoLogOutput
  public AlgaePosition[] getAllAlgaePosition() {
    return algaeTracker.getReadOnlyStampedObjects().keySet().toArray(AlgaePosition[]::new);

  }

  @Override
  public void periodic() {
    system.updateInputs();
    locateGamePieces();
    coralTracker.updateCollection(coralCleaner);
    algaeTracker.updateCollection(algaeCleaner);
  }
  public void setStrategy(ReefScoringStrategy strategy) {
    this.strategy = strategy;
  }

  public void setShouldMeasurementsExpire(boolean shouldMeasurementsExpire) {
    coralCleaner = shouldMeasurementsExpire ? coralMapAgeClearner : coralMapDirtyClearner;
    algaeCleaner = shouldMeasurementsExpire ? algaeMapAgeClearner : algaeMapDirtyClearner;
  }

  public void setObjectLifetime(double objectLifetime) {
    coralMapAgeClearner.setObjectLifetime(objectLifetime);
    algaeMapAgeClearner.setObjectLifetime(objectLifetime);
  }


  /**
   * @param algaePosition the algae location of measurment
   * @param branchStatus if the reef branch has a coral. is empty, or is unknown.
   * @return If the operation was successful
   */
  public boolean setAlgaeScoreStatus(
          AlgaePosition algaePosition, BranchStatus branchStatus, double timestampSeconds) {
    algaeTracker.registerObject(algaePosition, new StampedObject<>(timestampSeconds, branchStatus));
    return true;
  }

  /**
   * @param reefTrunk which reef pole the measurement recorded
   * @param elevatorPosition the level/height of the measurement.
   * @param branchStatus if the reef branch has a coral. is empty, or is unknown.
   * @return If the operation was successful
   */
  public boolean setCoralScoreStatus(
          ReefTrunk reefTrunk,
          ElevatorPosition elevatorPosition,
          BranchStatus branchStatus,
          double timestampSeconds) {
    if (elevatorPosition == ElevatorPosition.CORAL_INTAKE) {
      return false;
    }
    coralTracker.registerObject(
            new CoralPosition(reefTrunk, elevatorPosition),
            new StampedObject<>(timestampSeconds, branchStatus));
    return true;
  }

  private Set<CoralPosition> getCoralValidFilteredByBranchStatus(BranchStatus branchStatus) {
    return coralTracker.getReadOnlyStampedObjects().entrySet().stream()
            .filter(e -> e.getValue().getValue().equals(branchStatus))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
  }

  private Set<AlgaePosition> getAglaeValidFilteredByBranchStatus(BranchStatus branchStatus) {
    return algaeTracker.getReadOnlyStampedObjects().entrySet().stream()
            .filter(e -> e.getValue().getValue().equals(branchStatus))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
  }

  private void locateGamePieces() {
    int detectionLength = system.getInputs().tx.length;
    if (!system.getInputs().valid) {
      return;
    }
    for (int i = 0; i < detectionLength; i++) {
      String className = system.getInputs().className[i];
      if (className.equalsIgnoreCase("coral")) {
        BranchPositions coralBranch =
            GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        setCoralScoreStatus(coralBranch.getTrunk(),coralBranch.getElevatorLevel(),BranchStatus.SCORED,Logger.getTimestamp()/1e6);
      } else if (className.equalsIgnoreCase("algae")) {
        AlgaePosition algaePos =
            GamePieceLocate.findAlgaePos(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        setAlgaeScoreStatus(algaePos,BranchStatus.SCORED, Logger.getTimestamp()/1e6);
      }
    }
  }
}
