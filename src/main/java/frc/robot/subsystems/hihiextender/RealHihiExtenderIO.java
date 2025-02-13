package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.motor.NeoPidMotor;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;

  public RealHihiExtenderIO() {
    this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID);
    resetExtenderEncoder();
  }

  @Override
  public void stopHihiExtenderMotor() {
    this.extenderMotor.getNeoMotor().set(0);
  }

  @Override
  public void setHihiExtenderSpeed(double speed) {
    this.extenderMotor.getNeoMotor().set(speed);
  }

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    inputs.process(extenderMotor.getNeoMotor());
  }

  @Override
  public void setExtenderPosition(double encoderPos) {
    extenderMotor.setPidPos(encoderPos);
  }
}
