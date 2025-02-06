package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealElevatorIO implements ElevatorIO {
  public final SparkMax elevatorMotor1;
  public final SparkMax elevatorMotor2;

  public RealElevatorIO() {
    this.elevatorMotor1 =
        new SparkMax(Constants.ELEVATOR_MOTOR_1_ID, SparkLowLevel.MotorType.kBrushless);
    this.elevatorMotor2 =
        new SparkMax(Constants.ELEVATOR_MOTOR_2_ID, SparkLowLevel.MotorType.kBrushless);
    SparkBaseConfig conf = new SparkMaxConfig().follow(Constants.ELEVATOR_MOTOR_1_ID);
    elevatorMotor2.configure(
        conf, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double spd) {
    elevatorMotor1.set(spd);
  }

  @Override
  public void updateInputs(ElevatorInputs inputs) {
    inputs.encoder1Position = elevatorMotor1.getEncoder().getPosition();
    inputs.encoder1Velocity = elevatorMotor1.getEncoder().getVelocity();
    inputs.motor1Current = elevatorMotor1.getOutputCurrent();

    inputs.encoder2Position = elevatorMotor2.getEncoder().getPosition();
    inputs.encoder2Velocity = elevatorMotor2.getEncoder().getVelocity();
    inputs.motor2Current = elevatorMotor1.getOutputCurrent();

    inputs.fwdLimit = elevatorMotor1.getForwardLimitSwitch().isPressed();
    inputs.revLimit = elevatorMotor1.getReverseLimitSwitch().isPressed();
  }
}
