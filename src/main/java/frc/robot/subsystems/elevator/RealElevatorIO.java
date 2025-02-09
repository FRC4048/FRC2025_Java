package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.motor.NeoPidMotor;

public class RealElevatorIO implements ElevatorIO {
  public final NeoPidMotor elevatorMotor;

  public RealElevatorIO() {
    this.elevatorMotor = new NeoPidMotor(Constants.ELEVATOR_MOTOR_ID);
  }

  @Override
  public void setSpeed(double spd) {
    elevatorMotor.getNeoMotor().set(spd);
  }

  public void setElevatorPosition(double encoderPos) {
    // Does this need to be converted from heightInMeters to encoder Pos?
    elevatorMotor.setPidPos(encoderPos);
  }

  public double getElevatorPosition() {
    return elevatorMotor.getPidPosition();
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    inputs.process(elevatorMotor.getNeoMotor());
  }
}
