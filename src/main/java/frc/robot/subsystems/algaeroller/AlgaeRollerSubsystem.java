package frc.robot.subsystems.algaeroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<AlgaeRollerIO, AlgaeRollerInputs> algaeRollerSystem;

  public AlgaeRollerSubsystem(AlgaeRollerIO algaeRollerIO) {
    this.algaeRollerSystem = new LoggableSystem<>(algaeRollerIO, new AlgaeRollerInputs());
  }

  public void setRollerMotorSpeed(double speed) {
    algaeRollerSystem.getIO().setSpeedRoller(speed);
  }

  public double getRollerMotorPosition() {
    return algaeRollerSystem.getInputs().algaeRollerEncoder;
  }

  public void stopAlgaeRollerMotor() {
    algaeRollerSystem.getIO().stopRollerMotor();
  }

  public void setSpeedAngle(double speed) {
    algaeRollerSystem.getIO().setSpeedAngle(speed);
  }

  public void stopAngleMotor() {
    algaeRollerSystem.getIO().stopAngleMotor();
  }

  @Override
  public void periodic() {
    algaeRollerSystem.updateInputs();
  }
}
