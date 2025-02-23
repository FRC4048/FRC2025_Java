package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPositions;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.TunablePIDManager;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, PidMotorInputs> elevatorSystem;
  private ElevatorPositions elevatorPositions;
  private final TunablePIDManager pidConfig;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("ElevatorSubsystem").addAll().build();
    elevatorPositions = ElevatorPositions.CORAL_INTAKE;
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
    NeoPidConfig neoPidConfig =
        new NeoPidConfig(Constants.ELEVATOR_USE_MAX_MOTION)
            .setPidf(
                Constants.ELEVATOR_PID_P,
                Constants.ELEVATOR_PID_I,
                Constants.ELEVATOR_PID_D,
                Constants.ELEVATOR_PID_FF)
            .setMaxVelocity(Constants.ELEVATOR_PID_MAX_VELOCITY)
            .setMaxAccel(Constants.ELEVATOR_PID_MAX_ACCELERATION)
            .setIZone(Constants.ELEVATOR_PID_IZONE);
    this.pidConfig =
        new TunablePIDManager("ElevatorSubsystem", elevatorSystem.getIO(), neoPidConfig);
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

  public void setEncoder(double value) {
    elevatorSystem.getIO().setEncoder(value);
  }

  @Override
  public void periodic() {
    elevatorSystem.updateInputs();
    pidConfig.periodic();
  }
}
