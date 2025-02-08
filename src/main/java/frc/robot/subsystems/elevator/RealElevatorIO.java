package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;

public class RealElevatorIO implements ElevatorIO {
  public final SparkMax elevatorMotor;

  public RealElevatorIO() {
    this.elevatorMotor =
        new SparkMax(Constants.ELEVATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
  }

  @Override
  public void setSpeed(double spd) {
    elevatorMotor.set(spd);
  }

  @Override
  public void updateInputs(LimitedEncodedMotorInput inputs) {
    inputs.process(elevatorMotor);
  }
}
