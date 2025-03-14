package frc.robot.utils.advanced.trackers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorPosition;
import frc.robot.utils.advanced.*;
import frc.robot.utils.advanced.cleaners.StampedMapAgeClearner;
import frc.robot.utils.advanced.cleaners.StampedMapCleaner;
import frc.robot.utils.advanced.cleaners.StampedMapDirtyClearner;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReefScoreTracker extends SubsystemBase {
  private final StampedTrackerHashMap<CoralPosition, BranchStatus> coralTracker;
  private ReefScoringStrategy strategy;
  private StampedMapCleaner<CoralPosition, BranchStatus> cleaner;
  private final StampedMapAgeClearner<CoralPosition, BranchStatus> mapAgeClearner;
  private final StampedMapDirtyClearner<CoralPosition, BranchStatus> mapDirtyClearner;

  public ReefScoreTracker(double objectLifetime) {
    mapAgeClearner =
        new StampedMapAgeClearner<>(objectLifetime) {
          @Override
          public void invalidateEntry(
              Map<CoralPosition, StampedObject<BranchStatus>> map, CoralPosition key) {
            map.put(key, StampedObject.of(BranchStatus.UNKNOWN));
          }
        };
    mapDirtyClearner = new StampedMapDirtyClearner<>();
    this.coralTracker = new StampedTrackerHashMap<>();
  }

  public void setStrategy(ReefScoringStrategy strategy) {
    this.strategy = strategy;
  }

  public void setShouldMeasurementsExpire(boolean shouldMeasurementsExpire) {
    cleaner = shouldMeasurementsExpire ? mapAgeClearner : mapDirtyClearner;
  }

  public void setObjectLifetime(double objectLifetime) {
    mapAgeClearner.setObjectLifetime(objectLifetime);
  }

  @Override
  public void periodic() {
    coralTracker.updateCollection(cleaner);
  }

  /**
   * @param reefTrunk which reef pole the measurement recorded
   * @param elevatorPosition the level/height of the measurement.
   * @param branchStatus if the reef branch has a coral. is empty, or is unknown.
   * @return If the operation was successful
   */
  public boolean setScoreStatus(
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

  private Set<CoralPosition> getValidFilteredByBranchStatus(BranchStatus branchStatus) {
    return coralTracker.getReadOnlyStampedObjects().entrySet().stream()
        .filter(e -> e.getValue().getValue().equals(branchStatus))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }
}
