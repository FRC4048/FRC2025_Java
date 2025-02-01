package frc.robot.subsystems.hihiRoller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;
import org.littletonrobotics.junction.Logger;

public class HiHiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HiHiRollerIO, HiHiRollerInputs> hihiRollerSystem;

  public HiHiRollerSubsystem(HiHiRollerIO hihiRollerIO) {
    this.hihiRollerSystem = new LoggableSystem<>(hihiRollerIO, new HiHiRollerInputs());
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
