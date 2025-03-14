package frc.robot.utils.advanced.cleaners;

import frc.robot.utils.advanced.StampedObject;
import java.util.Map;
import org.littletonrobotics.junction.Logger;

public abstract class StampedMapAgeClearner<K, V> implements StampedMapCleaner<K, V> {
  private double objectLifetime;

  protected StampedMapAgeClearner(double objectLifetime) {
    this.objectLifetime = objectLifetime;
  }

  public void setObjectLifetime(double objectLifetime) {
    this.objectLifetime = objectLifetime;
  }

  @Override
  public void clean(Map<K, StampedObject<V>> map) {
    double currentTimestamp = Logger.getTimestamp() / 1000.0;
    for (Map.Entry<K, StampedObject<V>> entry : map.entrySet()) {
      if (entry.getValue().getTimestampSeconds() + objectLifetime < currentTimestamp) {
        invalidateEntry(map, entry.getKey());
      }
    }
  }
}
