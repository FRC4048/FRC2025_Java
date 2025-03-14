package frc.robot.utils.advanced.cleaners;

import frc.robot.utils.advanced.StampedObject;
import java.util.Map;

public class StampedMapDirtyClearner<K, V> implements StampedMapCleaner<K, V> {

  @Override
  public void invalidateEntry(Map<K, StampedObject<V>> entry, K key) {}

  @Override
  public void clean(Map<K, StampedObject<V>> map) {}
}
