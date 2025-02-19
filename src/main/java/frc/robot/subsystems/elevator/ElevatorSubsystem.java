package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.ReefPosition;
import frc.robot.utils.logging.LoggedTunableNumber;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, PidMotorInputs> elevatorSystem;
  private ReefPosition reefPosition;
  private final LoggedTunableNumber kP =
      new LoggedTunableNumber("ElevatorSubsystem/kP", NeoPidMotor.DEFAULT_P);
  private final LoggedTunableNumber kI =
      new LoggedTunableNumber("ElevatorSubsystem/kI", NeoPidMotor.DEFAULT_I);
  private final LoggedTunableNumber kD =
      new LoggedTunableNumber("ElevatorSubsystem/kD", NeoPidMotor.DEFAULT_D);
  private final LoggedTunableNumber iZone =
      new LoggedTunableNumber("ElevatorSubsystem/iZone", NeoPidMotor.DEFAULT_IZONE);
  private final LoggedTunableNumber feedForward =
      new LoggedTunableNumber("ElevatorSubsystem/feedForward", NeoPidMotor.DEFAULT_FF);
  private final LoggedTunableNumber maxVelocity =
      new LoggedTunableNumber("ElevatorSubsystem/maxVelocity", NeoPidMotor.MAX_VELOCITY);
  private final LoggedTunableNumber maxAcceleration =
      new LoggedTunableNumber("ElevatorSubsystem/maxAcceleration", NeoPidMotor.MAX_ACCELERATION);

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
    LoggedTunableNumber.ifChanged(
        hashCode(),
        doubles -> {
          NeoPidConfig neoPidConfig =
              new NeoPidConfig()
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
  }
}
