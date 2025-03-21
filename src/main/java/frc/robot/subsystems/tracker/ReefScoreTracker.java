package frc.robot.subsystems.tracker;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorPosition;
import frc.robot.utils.advanced.*;
import frc.robot.utils.advanced.cleaners.StampedMapAgeClearner;
import frc.robot.utils.advanced.cleaners.StampedMapCleaner;
import frc.robot.utils.advanced.cleaners.StampedMapDirtyClearner;
import frc.robot.utils.advanced.trackers.StampedTrackerHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReefScoreTracker extends SubsystemBase {
  private final StampedTrackerHashMap<CoralPosition, BranchStatus> coralTracker;
  private ReefScoringStrategy strategy;
  private StampedMapCleaner<CoralPosition, BranchStatus> coralCleaner;
  private final StampedMapAgeClearner<CoralPosition, BranchStatus> coralMapAgeClearner;
  private final StampedMapDirtyClearner<CoralPosition, BranchStatus> coralMapDirtyClearner;

  private final StampedTrackerHashMap<AlgaePosition, BranchStatus> algaeTracker;
  private StampedMapCleaner<AlgaePosition, BranchStatus> algaeCleaner;
  private final StampedMapAgeClearner<AlgaePosition, BranchStatus> algaeMapAgeClearner;
  private final StampedMapDirtyClearner<AlgaePosition, BranchStatus> algaeMapDirtyClearner;

  public ReefScoreTracker(double objectLifetime) {
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

  @Override
  public void periodic() {
    coralTracker.updateCollection(coralCleaner);
    algaeTracker.updateCollection(algaeCleaner);
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
}
