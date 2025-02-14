package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkInputs;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, MotorInputs<SparkMax>> elevatorSystem;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    SparkInputs.Builder<?> builder = new SparkInputs.Builder<>("ElevatorSubsystem");
    MotorInputs<SparkMax> inputs = builder.addAll().build();
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
    /* Example for Neo Pid
    NeoPidMotorInputs.Builder<?> builder2 = new NeoPidMotorInputs.Builder<>("ElevatorSubsystem");
    MotorInputs<SparkMax> inputs2 = builder2.addAll().build();
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs2);
    */
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public void setElevatorPosition(double encoderPos) {
    // TODO: This can be moved to input-based logging once that framework switches to composition
    Logger.recordOutput("ElevatorSubystem/targetPosition", encoderPos);
    elevatorSystem.getIO().setElevatorPosition(encoderPos);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
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
}
