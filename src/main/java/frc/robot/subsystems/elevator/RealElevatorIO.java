package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;

public class RealElevatorIO implements ElevatorIO {
  protected final NeoPidMotor elevatorMotor;
  private final NeoPidMotorInputProvider inputProvider;

  public RealElevatorIO() {
    this.elevatorMotor =
        new NeoPidMotor(Constants.ELEVATOR_MOTOR_ID, Constants.ELEVATOR_USE_MAX_MOTION);
    this.inputProvider = new NeoPidMotorInputProvider(elevatorMotor);
  }

  @Override
  public void setSpeed(double spd) {
    elevatorMotor.getNeoMotor().set(spd);
  }

  public void setElevatorPosition(double encoderPos) {
    // Does this need to be converted from heightInMeters to encoder Pos?
    elevatorMotor.setPidPos(encoderPos, SparkBase.ControlType.kMAXMotionPositionControl);
  }

  @Override
  public void stopMotor() {
    elevatorMotor.getNeoMotor().set(0);
  }

  @Override
  public void resetEncoder() {
    this.elevatorMotor.getEncoder().setPosition(0);
  }

  @Override
  public void configurePID(NeoPidConfig neoPidConfig) {
    elevatorMotor.configure(neoPidConfig);
  }

  @Override
  public void updateInputs(PidMotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
