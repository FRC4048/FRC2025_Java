package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ReefPosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, BuildableFolderMotorInputs<SparkMax>> elevatorSystem;
  private ReefPosition ReefPosition;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("ElevatorSubsystem");
    ReefPosition = ReefPosition.INTAKE;
    BuildableFolderMotorInputs<SparkMax> inputs = builder.addAll().build();
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public void setElevatorPosition(Double encoderPos) {
    // TODO: This can be moved to input-based logging once that framework switches to composition
    Logger.recordOutput("ElevatorSubystem/targetPosition", encoderPos);
    elevatorSystem.getIO().setElevatorPosition(encoderPos);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  public double getElevatorPosition() {
    return elevatorSystem.getIO().getElevatorPosition();
  }

  public void setStoredReefPosition(ReefPosition ReefPosition) {
    this.ReefPosition = ReefPosition;
  }

  public ReefPosition getStoredReefPosition() {
    return ReefPosition;
  }

  public boolean getForwardLimitSwitchState() {
    return elevatorSystem.getInputs().fwdLimit();
  }

  public boolean getReverseLimitSwitchState() {
    return elevatorSystem.getInputs().revLimit();
  }

  public void stopMotor() {
    elevatorSystem.getIO().stopMotor();
  }

  public void resetEncoder() {
    elevatorSystem.getIO().resetEncoder();
  }

  @Override
  public void periodic() {
    SmartShuffleboard.put(
        "DEBUG", "ELevatorPOsition", elevatorSystem.getIO().getElevatorPosition());
    elevatorSystem.updateInputs();
  }
}
