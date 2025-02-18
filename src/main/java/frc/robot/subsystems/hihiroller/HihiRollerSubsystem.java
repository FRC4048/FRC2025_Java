package frc.robot.subsystems.hihiroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

import org.littletonrobotics.junction.Logger;

public class HihiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiRollerIO, MotorInputs> hihiRollerSystem;

  private double targetRollerVelocity;

  public HihiRollerSubsystem(HihiRollerIO hihiRollerIO) {
    MotorInputs inputs = new MotorInputBuilder<>("HihiRollerSubsystem").addAll().build();
    this.hihiRollerSystem = new LoggableSystem<>(hihiRollerIO, inputs);
    targetRollerVelocity = Constants.HIHI_INTAKE_SPEED;

    if (Constants.HIHI_ROLLER_DEBUG) {
      SmartShuffleboard.put("HiHiRollerSubsystem/config", "TargetVelocity", targetRollerVelocity);
    }
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

    if (Constants.HIHI_ROLLER_DEBUG) {
      targetRollerVelocity = SmartShuffleboard.getDouble("HiHiRollerSubystem/config", "TargetVelocity", targetRollerVelocity);
    }
  }

  public double getTargetVelocity() {
    return targetRollerVelocity;
  }
}
