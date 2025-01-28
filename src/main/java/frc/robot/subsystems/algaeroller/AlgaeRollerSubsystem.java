package frc.robot.subsystems.algaeroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<AlgaeRollerIO, AlgaeRollerInputs> algaeRollerSystem;

  public AlgaeRollerSubsystem(AlgaeRollerIO algaeRollerIO) {
    this.algaeRollerSystem = new LoggableSystem<>(algaeRollerIO, new AlgaeRollerInputs());
  }

  public void setRollerMotorSpeed(double speed) {
    algaeRollerSystem.getIO().setRollerSpeed(speed);
  }

  public double getRollerMotorPosition() {
    return algaeRollerSystem.getInputs().algaeRollerEncoder;
  }

  public void stopAlgaeRollerMotor() {
    algaeRollerSystem.getIO().stopRollerMotor();
  }

  @Override
  public void periodic() {
    algaeRollerSystem.updateInputs();
  }
}
