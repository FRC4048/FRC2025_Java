package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, BuildableFolderMotorInputs<SparkMax>> elevatorSystem;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("ElevatorSubsystem");
    BuildableFolderMotorInputs<SparkMax> inputs = builder.addAll().build();
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  public boolean getForwardLimitSwitchState() {
    return elevatorSystem.getInputs().getFwd();
  }

  public boolean getReverseLimitSwitchState() {
    return elevatorSystem.getInputs().getRev();
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
