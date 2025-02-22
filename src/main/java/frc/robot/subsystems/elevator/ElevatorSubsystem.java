package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPositions;
import frc.robot.utils.logging.LoggedTunableNumber;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, PidMotorInputs> elevatorSystem;
  private ElevatorPositions elevatorPositions;
  private final LoggedTunableNumber kP = new LoggedTunableNumber("ElevatorSubsystem/kP", 0.03);
  private final LoggedTunableNumber kI = new LoggedTunableNumber("ElevatorSubsystem/kI", 0);
  private final LoggedTunableNumber kD = new LoggedTunableNumber("ElevatorSubsystem/kD", 0);
  private final LoggedTunableNumber iZone = new LoggedTunableNumber("ElevatorSubsystem/iZone", 0);
  private final LoggedTunableNumber feedForward =
      new LoggedTunableNumber("ElevatorSubsystem/feedForward", 0.001);
  private final LoggedTunableNumber maxVelocity =
      new LoggedTunableNumber("ElevatorSubsystem/maxVelocity", 3000);
  private final LoggedTunableNumber maxAcceleration =
      new LoggedTunableNumber("ElevatorSubsystem/maxAcceleration", 30000);

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("ElevatorSubsystem").addAll().build();
    elevatorPositions = ElevatorPositions.CORAL_INTAKE;
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public void setElevatorPosition(double encoderPos) {
    if (encoderPos > 0) {
      encoderPos = 0;
    } else if (encoderPos < Constants.MAX_ELEVATOR_ENCODER_POSITION) {
      encoderPos = Constants.MAX_ELEVATOR_ENCODER_POSITION;
    }
    // TODO: This can be moved to input-based logging once that framework switches to composition
    Logger.recordOutput("ElevatorSubsystem/targetPosition", encoderPos);
    elevatorSystem.getIO().setElevatorPosition(encoderPos);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  public double getElevatorTargetPosition() {
    return elevatorSystem.getInputs().getPidSetpoint();
  }

  public void setStoredReefPosition(ElevatorPositions elevatorPositions) {
    Logger.recordOutput("SelectedReefPosition", elevatorPositions);
    this.elevatorPositions = elevatorPositions;
  }

  public ElevatorPositions getStoredReefPosition() {
    return elevatorPositions;
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

    LoggedTunableNumber.ifChanged(
        hashCode(),
        doubles -> {
          NeoPidConfig neoPidConfig =
              new NeoPidConfig(true)
                  .setPidf(doubles[0], doubles[1], doubles[2], doubles[3])
                  .setTrapezoidConstructions(doubles[4], doubles[5])
                  .setIZone(doubles[6]);
          elevatorSystem.getIO().updatePidConfig(neoPidConfig);
        },
        kP,
        kI,
        kD,
        feedForward,
        maxVelocity,
        maxAcceleration,
        iZone);
    Logger.recordOutput(
        "ElevatorErrorTimesP",
        (elevatorSystem.getInputs().getPidSetpoint()
                - elevatorSystem.getInputs().getEncoderPosition())
            * kP.get());
  }
}
