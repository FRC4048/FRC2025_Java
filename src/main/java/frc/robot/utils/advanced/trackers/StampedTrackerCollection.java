package frc.robot.utils.advanced.trackers;

import frc.robot.utils.advanced.StampedObject;
import frc.robot.utils.advanced.cleaners.StampedCollectionCleaner;
import java.util.Collection;
import java.util.Collections;

public class StampedTrackerCollection<T> implements StampedTracker<StampedCollectionCleaner<T>> {
  private final Collection<StampedObject<T>> stampedCollection;

  public StampedTrackerCollection(Collection<StampedObject<T>> stampedCollection) {
    this.stampedCollection = stampedCollection;
  }

  public void registerObject(T object) {
    stampedCollection.add(StampedObject.of(object));
  }

  public void registerObject(StampedObject<T> object) {
    stampedCollection.add(object);
  }

  @Override
  public void updateCollection(StampedCollectionCleaner<T> cleaner) {
    if (cleaner != null) {
      cleaner.clean(stampedCollection);
    }
  }

  public Collection<StampedObject<T>> getReadOnlyStampedObjects() {
    return Collections.unmodifiableCollection(stampedCollection);
  }
}
