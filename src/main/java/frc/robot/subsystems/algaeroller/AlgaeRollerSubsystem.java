package frc.robot.subsystems.algaeRoller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<AlgaeRollerIO, AlgaeRollerInputs> algaeRollerSystem;

  public AlgaeRollerSubsystem(AlgaeRollerIO algaeRollerIO) {
    this.algaeRollerSystem = new LoggableSystem<>(algaeRollerIO, new AlgaeRollerInputs());
  }

  public void setRollerMotorSpeed(double speed) {
    algaeRollerSystem.getIO().setSpeed(speed);
  }

  public double getRollerMotorPosition() {
    return algaeRollerSystem.getInputs().algaeRollerEncoder;
  }

  public void stopAlgaeRollerMotor() {
    algaeRollerSystem.getIO().stop();
  }

  @Override
  public void periodic() {
    algaeRollerSystem.updateInputs();
  }
}
