package frc.robot.utils.advanced;

import java.util.Objects;
import org.littletonrobotics.junction.Logger;

public class StampedObject<T> {
  private double timestampSeconds;
  private final T value;

  public StampedObject(double currentTimeStampSeconds, T value) {
    this.timestampSeconds = currentTimeStampSeconds;
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public double getTimestampSeconds() {
    return timestampSeconds;
  }

  public static <T> StampedObject<T> of(T object) {
    return new StampedObject<>(Logger.getTimestamp() / 1000.0, object);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    StampedObject<?> that = (StampedObject<?>) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
