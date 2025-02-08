package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

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
  public void updateInputs(ElevatorInputs inputs) {
    inputs.elevatorMotorEncoderValue = elevatorMotor.getEncoder().getPosition();
  }
}
