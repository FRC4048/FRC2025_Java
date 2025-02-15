package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputSource;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a Motor with a Pid */
public class PidMotorInputs extends MotorInputs {

  private Double pidSetpoint;

  public PidMotorInputs(PidMotorInputBuilder<?> builder) {
    super(builder);
    this.pidSetpoint = builder.isLogPidSetpoint() ? 0.0 : null;
  }

  @Override
  public void toLog(LogTable table) {
    super.toLog(table);
    if (pidSetpoint != null) {
      table.put("pidSetpoint", pidSetpoint);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (pidSetpoint != null) {
      pidSetpoint = table.get("pidSetpoint", pidSetpoint);
    }
  }

  @Override
  public boolean process(InputSource inputSource) {
    boolean success = super.process(inputSource);

    if (success && inputSource instanceof PidMotorInputSource pidMotorInputSource) {
      if (pidSetpoint != null) {
        pidSetpoint = pidMotorInputSource.getPidSetpoint();
      }
      return true;
    } else {
      DriverStation.reportError("InputSource must be of type PidMotorInputSource", true);
      return false;
    }
  }

  public Double getPidSetpoint() {
    return pidSetpoint;
  }
}
