package frc.robot.utils.advanced.trackers;

import frc.robot.utils.advanced.StampedObject;
import frc.robot.utils.advanced.cleaners.StampedMapCleaner;

public interface StampedTrackerMap<K, V> extends StampedTracker<StampedMapCleaner<K, V>> {
  void registerObject(K key, V value);
  void registerObject(K key, StampedObject<V> value);
}
