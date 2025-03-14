package frc.robot.utils.advanced.cleaners;

import frc.robot.utils.advanced.StampedObject;
import java.util.Collection;
import org.littletonrobotics.junction.Logger;

public abstract class StampedCollectionAgeClearner<T> implements StampedCollectionCleaner<T> {
  private double objectLifetime;

  protected StampedCollectionAgeClearner(double objectLifetime) {
    this.objectLifetime = objectLifetime;
  }

  public void setObjectLifetime(double objectLifetime) {
    this.objectLifetime = objectLifetime;
  }

  @Override
  public void clean(Collection<StampedObject<T>> set) {
    double currentTimestamp = Logger.getTimestamp() / 1000.0;
    for (StampedObject<T> obj : set) {
      if (obj.getTimestampSeconds() + objectLifetime < currentTimestamp) {
        invalidateEntry(set, obj);
      }
    }
  }
}
