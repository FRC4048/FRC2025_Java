package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidMotor;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;

  public RealHihiExtenderIO() {
    this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID);
    inputProvider = new NeoPidMotorInputProvider(extenderMotor);
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
  public void resetExtenderEncoder() {
    // TODO: Implement this
    this.extenderMotor.getNeoMotor().getEncoder().setPosition(0);
  }

  @Override
  public void setExtenderPosition(double encoderPos) {
    extenderMotor.setPidPos(encoderPos);
  }

  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
