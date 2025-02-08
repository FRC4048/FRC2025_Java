package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.CommonMotorInputs;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, BuildableFolderMotorInputs<SparkMax>> elevatorSystem;

  public ElevatorSubsystem(ElevatorIO ElevatorIO) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("ElevatorSubsystem");
    BuildableFolderMotorInputs<SparkMax> inputs = CommonMotorInputs.createLimitedEncoded(builder);
    this.elevatorSystem = new LoggableSystem<>(ElevatorIO, inputs);
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
