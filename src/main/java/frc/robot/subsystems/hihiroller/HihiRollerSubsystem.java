package frc.robot.subsystems.hihiroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;
import org.littletonrobotics.junction.Logger;

public class HihiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiRollerIO, HihiRollerInputs> hihiRollerSystem;

  public HihiRollerSubsystem(HihiRollerIO hihiRollerIO) {
    this.hihiRollerSystem = new LoggableSystem<>(hihiRollerIO, new HihiRollerInputs());
  }

  public void setRollerMotorSpeed(double speed) {
    hihiRollerSystem.getIO().setRollerSpeed(speed);
    Logger.recordOutput("rollerSubsystem/Speed", speed);
  }

  public double getRollerMotorPosition() {
    return hihiRollerSystem.getInputs().hihiRollerEncoder;
  }

  public void stopHihiRollerMotor() {
    hihiRollerSystem.getIO().stopRollerMotor();
  }

  @Override
  public void periodic() {
    hihiRollerSystem.updateInputs();
  }
}
