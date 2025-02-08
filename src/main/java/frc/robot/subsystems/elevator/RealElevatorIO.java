package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class RealElevatorIO implements ElevatorIO {
  public final SparkMax elevatorMotor;

  public RealElevatorIO() {
    this.elevatorMotor =
        new SparkMax(Constants.ELEVATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    SparkBaseConfig conf = new SparkMaxConfig().follow(Constants.ELEVATOR_MOTOR_ID);
    elevatorMotor.configure(
        conf, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double spd) {
    elevatorMotor.set(spd);
  }

  @Override
  public void stopMotor() {
    elevatorMotor.set(0);
  }

  @Override
  public void resetEncoder() {
    this.elevatorMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    inputs.process(elevatorMotor);
  }
}
