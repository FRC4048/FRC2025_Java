package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkBase;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxEncoder;
import frc.robot.utils.diag.DiagSparkMaxSwitch;
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
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Elevator",
                "ForwardLimit",
                elevatorMotor.getNeoMotor(),
                DiagSparkMaxSwitch.Direction.FORWARD));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Elevator",
                "ReverseLimit",
                elevatorMotor.getNeoMotor(),
                DiagSparkMaxSwitch.Direction.REVERSE));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "Elevator",
                "Encoder",
                Constants.ELEVATOR_DIAGS_ENCODER,
                elevatorMotor.getNeoMotor()));
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
  public void updatePidConfig(NeoPidConfig neoPidConfig) {
    elevatorMotor.configure(neoPidConfig);
  }

  @Override
  public void updateInputs(PidMotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
