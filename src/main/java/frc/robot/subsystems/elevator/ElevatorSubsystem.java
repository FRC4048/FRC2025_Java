package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.ReefPosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.TunablePIDManager;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, PidMotorInputs> elevatorSystem;
  private ReefPosition reefPosition;
  public final NeoPidConfig initConfig;
  private final TunablePIDManager pidConfig;

  public ElevatorSubsystem(ElevatorIO elevatorIO) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("ElevatorSubsystem").addAll().build();
    reefPosition = ReefPosition.LEVEL0;
    this.elevatorSystem = new LoggableSystem<>(elevatorIO, inputs);
    this.initConfig =
        new NeoPidConfig(Constants.ELEVATOR_USE_MAX_MOTION)
            .setP(Constants.ELEVATOR_PID_P)
            .setI(Constants.ELEVATOR_PID_I)
            .setD(Constants.ELEVATOR_PID_D)
            .setIZone(Constants.ELEVATOR_PID_I_ZONE)
            .setFF(Constants.ELEVATOR_PID_FF)
            .setMaxVelocity(Constants.ELEVATOR_PID_MAX_VEL)
            .setMaxAccel(Constants.ELEVATOR_PID_MAX_ACC)
            .setAllowedError(Constants.ELEVATOR_PID_ALLOWED_ERROR);
    initConfig.setCurrentLimit(Constants.ELEVATOR_CURRENT_LIMIT);
    elevatorIO.configurePID(initConfig);
    pidConfig =
        new TunablePIDManager(
            "Elevator", elevatorIO, initConfig, Constants.ELEVATOR_USE_MAX_MOTION);
  }

  public void setElevatorMotorSpeed(double speed) {
    elevatorSystem.getIO().setSpeed(speed);
  }

  public void setElevatorPosition(double encoderPos) {
    if (encoderPos < 0) {
      encoderPos = 0;
    } else if (encoderPos > Constants.MAX_ELEVATOR_HEIGHT_METERS) {
      encoderPos = Constants.MAX_ELEVATOR_HEIGHT_METERS;
    }
    // TODO: This can be moved to input-based logging once that framework switches to composition
    Logger.recordOutput("ElevatorSubsystem/targetPosition", encoderPos);
    elevatorSystem.getIO().setElevatorPosition(encoderPos);
  }

  public double getEncoderValue() {
    return elevatorSystem.getInputs().getEncoderPosition();
  }

  public double getElevatorPosition() {
    return elevatorSystem.getIO().getElevatorPosition();
  }

  public void setStoredReefPosition(ReefPosition reefPosition) {
    this.reefPosition = reefPosition;
  }

  public ReefPosition getStoredReefPosition() {
    return reefPosition;
  }

  public boolean getForwardLimitSwitchState() {
    return elevatorSystem.getInputs().getFwdLimit();
  }

  public boolean getReverseLimitSwitchState() {
    return elevatorSystem.getInputs().getRevLimit();
  }

  public void stopMotor() {
    elevatorSystem.getIO().stopMotor();
  }

  public void resetEncoder() {
    elevatorSystem.getIO().resetEncoder();
  }

  @Override
  public void periodic() {
    pidConfig.periodic();
    elevatorSystem.updateInputs();
  }

  public NeoPidConfig getInitConfig() {
    return initConfig;
  }
}
