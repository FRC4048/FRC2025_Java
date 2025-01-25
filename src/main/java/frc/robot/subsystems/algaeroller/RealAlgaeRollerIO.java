package frc.robot.subsystems.algaeRoller;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;

public class RealAlgaeRollerIO implements AlgaeRollerIO {
  private final SparkMax algaeRoller;

  public RealAlgaeRollerIO() {
    this.algaeRoller =
        new SparkMax(Constants.ALGAE_ROLLER_CAN_ID, SparkLowLevel.MotorType.kBrushless);
  }

  @Override
  public void setSpeed(double speed) {
    algaeRoller.set(speed);
  }

  @Override
  public void stop() {
    algaeRoller.set(0);
  }

  @Override
  public void updateInputs(AlgaeRollerInputs inputs) {
    inputs.algaeRollerEncoder = algaeRoller.getEncoder().getPosition();
  }
}
