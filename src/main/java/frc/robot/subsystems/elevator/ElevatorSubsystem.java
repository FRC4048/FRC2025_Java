package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;
import frc.robot.utils.logging.LoggableSystem;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, LimitedEncodedMotorInput> elevatorSystem;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    this.elevatorSystem =
        new LoggableSystem<>(ElevatorIO, new LimitedEncodedMotorInput(), ElevatorSubsystem.class);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public double getEncoderValue1() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  @Override
  public void periodic() {
    elevatorSystem.updateInputs();
  }
}
