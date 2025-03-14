package frc.robot.utils.advanced.cleaners;

import frc.robot.utils.advanced.StampedObject;
import java.util.Map;

public interface StampedMapCleaner<K, V> extends StampedCleaner {
  void invalidateEntry(Map<K, StampedObject<V>> entry, K key);

  void clean(Map<K, StampedObject<V>> map);
}
