package frc.robot.utils.advanced.trackers;

import frc.robot.utils.advanced.StampedObject;
import frc.robot.utils.advanced.cleaners.StampedMapCleaner;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StampedTrackerHashMap<K, V> implements StampedTrackerMap<K, V> {
  private final HashMap<K, StampedObject<V>> stampedObjectList = new HashMap<>();

  @Override
  public void registerObject(K key, V value) {
    registerObject(key, StampedObject.of(value));
  }

  @Override
  public void registerObject(K key, StampedObject<V> value) {
    stampedObjectList.put(key, value);
  }

  public Map<K, StampedObject<V>> getReadOnlyStampedObjects() {
    return Collections.unmodifiableMap(stampedObjectList);
  }

  @Override
  public void updateCollection(StampedMapCleaner<K, V> cleaner) {
    if (cleaner != null) {
      cleaner.clean(stampedObjectList);
    }
  }
}
