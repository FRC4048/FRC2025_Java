package frc.robot.subsystems.hihiroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;
import org.littletonrobotics.junction.Logger;

public class HihiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiRollerIO, MotorInputs> hihiRollerSystem;

  public HihiRollerSubsystem(HihiRollerIO hihiRollerIO) {
    MotorInputs.Builder<?> builder = new MotorInputs.Builder<>("HihiRollerSubsystem");
    MotorInputs inputs = builder.addAll().build();
    this.hihiRollerSystem = new LoggableSystem<>(hihiRollerIO, inputs);
  }

  public void setRollerMotorSpeed(double speed) {
    hihiRollerSystem.getIO().setRollerSpeed(speed);
    Logger.recordOutput("rollerSubsystem/Speed", speed);
  }

  public double getRollerMotorPosition() {
    return hihiRollerSystem.getInputs().getEncoderPosition();
  }

  public void stopHihiRollerMotor() {
    hihiRollerSystem.getIO().stopRollerMotor();
  }

  @Override
  public void periodic() {
    hihiRollerSystem.updateInputs();
  }
}
