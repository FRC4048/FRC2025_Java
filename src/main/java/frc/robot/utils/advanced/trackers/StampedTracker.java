package frc.robot.utils.advanced.trackers;

import frc.robot.utils.advanced.cleaners.StampedCleaner;

public interface StampedTracker<T extends StampedCleaner> {
  void updateCollection(T cleaner);
}
