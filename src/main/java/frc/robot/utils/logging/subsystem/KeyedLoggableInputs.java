package frc.robot.utils.logging.subsystem;

import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Subclass of {@link LoggableInputs} that contains a key for logging */
public abstract class KeyedLoggableInputs implements LoggableInputs {
  private final String key;

  public KeyedLoggableInputs(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
