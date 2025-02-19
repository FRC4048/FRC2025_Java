package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.ReefPosition;
import frc.robot.utils.logging.LoggedTunableNumber;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, PidMotorInputs> elevatorSystem;
  private ReefPosition reefPosition;
  private final LoggedTunableNumber heightOffset =
      new LoggedTunableNumber("HeightOffset", Constants.ELEVATOR_ENCODER_HEIGHT_OFFSET);
  private final LoggedTunableNumber heightRatio =
      new LoggedTunableNumber("HeightRatio", Constants.ELEVATOR_ENCODER_HEIGHT_RATIO);

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("ElevatorSubsystem").addAll().build();
    reefPosition = ReefPosition.LEVEL0;
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public void setElevatorPosition(double encoderPos) {
    if (encoderPos < 0) {
      encoderPos = 0;
    } else if (encoderPos > Constants.MAX_ELEVATOR_HEIGHT_METERS) {
      encoderPos = Constants.MAX_ELEVATOR_HEIGHT_METERS;
    }
    // TODO: This can be moved to input-based logging once that framework switches to composition
    Logger.recordOutput("ElevatorSubsystem/targetPosition", encoderPos);
    elevatorSystem.getIO().setElevatorPosition(encoderPos);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  public double getElevatorPosition() {
    return elevatorSystem.getIO().getElevatorPosition();
  }

  public void setStoredReefPosition(ReefPosition reefPosition) {
    this.reefPosition = reefPosition;
  }

  public ReefPosition getStoredReefPosition() {
    return reefPosition;
  }

  public boolean getForwardLimitSwitchState() {
    return elevatorSystem.getInputs().getFwdLimit();
  }

  public boolean getReverseLimitSwitchState() {
    return elevatorSystem.getInputs().getRevLimit();
  }

  public void stopMotor() {
    elevatorSystem.getIO().stopMotor();
  }

  public void resetEncoder() {
    elevatorSystem.getIO().resetEncoder();
  }

  @Override
  public void periodic() {
    elevatorSystem.updateInputs();
  }

  public double getElevatorHeight() {
    double encValue = getEncoderValue();
    return heightOffset.get() + heightRatio.get() * encValue;
  }
}
