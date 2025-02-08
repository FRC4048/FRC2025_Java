package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, ElevatorInputs> elevatorSystem;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, new ElevatorInputs());
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().elevatorMotorEncoderValue;
  }

  public boolean getForwardLimitSwitchState() {
    return elevatorSystem.getInputs().forwardLimitSwitchState;
  }

  public boolean getReverseLimitSwitchState() {
    return elevatorSystem.getInputs().backLimitSwitchState;
  }
  public void stopMotor(){
    elevatorSystem.getIO().stopMotor();
  }
  @Override
  public void periodic() {
    elevatorSystem.updateInputs();
  }

  public void resetEncoder() {
    elevatorSystem.getIO().resetEncoder();
  }
}
