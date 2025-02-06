package frc.robot.subsystems.hihiroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.commoninputs.EncodedMotorInput;
import frc.robot.utils.logging.LoggableSystem;
import org.littletonrobotics.junction.Logger;

public class HihiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiRollerIO, EncodedMotorInput> hihiRollerSystem;

  public HihiRollerSubsystem(HihiRollerIO hihiRollerIO) {
    this.hihiRollerSystem =
        new LoggableSystem<>(hihiRollerIO, new EncodedMotorInput(), HihiRollerSubsystem.class);
  }

  public void setRollerMotorSpeed(double speed) {
    hihiRollerSystem.getIO().setRollerSpeed(speed);
    Logger.recordOutput("rollerSubsystem/Speed", speed);
  }

  public double getRollerMotorPosition() {
    return hihiRollerSystem.getInputs().encoderPosition;
  }

  public void stopHihiRollerMotor() {
    hihiRollerSystem.getIO().stopRollerMotor();
  }

  @Override
  public void periodic() {
    hihiRollerSystem.updateInputs();
  }
}
