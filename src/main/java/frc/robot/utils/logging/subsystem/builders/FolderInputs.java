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

  protected final InputSource<R> inputSource;

  public FolderInputs(Builder<R, ?> builder) {
    super(builder.folder);
    this.inputSource = builder.inputSource;
  }

  abstract boolean process(R source);

  public abstract static class Builder<R, T extends Builder<R, T>> {

    private final String folder;
    private final InputSource<R> inputSource;

    public Builder(String folder, InputSource<R> inputSource) {
      this.folder = folder;
      this.inputSource = inputSource;
    }

    T self() {
      return (T) this;
    }

    public abstract T reset();

    public FolderInputs<R> build() {
      return new FolderInputs<>(this) {
        @Override
        boolean process(R source) {
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
