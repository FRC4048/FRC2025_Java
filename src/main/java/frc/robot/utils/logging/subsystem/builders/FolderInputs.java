package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * subclass of {@link FolderLoggableInputs} that allows keys to be build using an {@link
 * InputSource}
 */
public abstract class FolderInputs extends FolderLoggableInputs {

  public FolderInputs(Builder<?> builder) {
    super(builder.folder);
  }

  /**
   * @return if processing was successful.
   */
  abstract boolean process(InputSource inputSource);

  public abstract static class Builder<T extends Builder<T>> {

    private final String folder;

    public Builder(String folder) {
      this.folder = folder;
    }

    T self() {
      return (T) this;
    }

    public abstract T reset();

    public FolderInputs build() {
      return new FolderInputs(this) {
        @Override
        boolean process(InputSource inputSource) {
          return true;
        }

        @Override
        public void toLog(LogTable table) {}

        @Override
        public void fromLog(LogTable table) {}
      };
    }
  }
}
