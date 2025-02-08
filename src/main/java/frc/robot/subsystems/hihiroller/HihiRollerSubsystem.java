package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;
import frc.robot.utils.commoninputs.CommonMotorInputs;
import frc.robot.utils.commoninputs.SparkMaxInputBuilder;
import frc.robot.utils.logging.LoggableSystem;
import org.littletonrobotics.junction.Logger;

public class HihiRollerSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiRollerIO, BuildableKeyedInputs<SparkMax>> hihiRollerSystem;

  public HihiRollerSubsystem(HihiRollerIO hihiRollerIO) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("HihiRollerSubsystem");
    BuildableKeyedInputs<SparkMax> inputs = CommonMotorInputs.createLimitedEncoded(builder);
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
