package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * subclass of {@link FolderLoggableInputs} that allows keys to be build using an {@link
 * InputSource}
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public abstract class FolderInputs<R> extends FolderLoggableInputs {

  public FolderInputs(Builder<R, ?> builder) {
    super(builder.folder);
  }

  abstract void process(R source);

  public abstract static class Builder<R, T extends Builder<R, T>> {

    private final String folder;

    public Builder(String folder) {
      this.folder = folder;
    }

    T self() {
      return (T) this;
    }

    public abstract T reset();

    public FolderInputs<R> build() {
      return new FolderInputs<>(this) {
        @Override
        void process(R source) {}

        @Override
        public void toLog(LogTable table) {}

        @Override
        public void fromLog(LogTable table) {}
      };
    }
  }
}
