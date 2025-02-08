package frc.robot.utils.logging.subsystem.inputs;

import org.littletonrobotics.junction.inputs.LoggableInputs;

public abstract class KeyedInputs implements LoggableInputs {
  private final String key;

  public KeyedInputs(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
