package frc.robot.utils.advanced.cleaners;

import frc.robot.utils.advanced.StampedObject;
import java.util.Collection;

public interface StampedCollectionCleaner<T> extends StampedCleaner {
  void invalidateEntry(Collection<StampedObject<T>> set, StampedObject<T> object);

  void clean(Collection<StampedObject<T>> set);
}
