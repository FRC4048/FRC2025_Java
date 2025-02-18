package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;

  public RealHihiExtenderIO() {
    NeoPidConfig params =
        new NeoPidConfig()
            .setP(Constants.HIHI_EXTENDER_PID_P)
            .setCurrentLimit(20)
            .setAllowedError(Constants.HIHI_EXTENDER_ALLOWED_ERROR)
            .setMaxAccel(Constants.HIHI_EXTENDER_MAX_ACCEL)
            .setMaxVelocity(Constants.HIHI_EXTENDER_MAX_VELOCITY);
    this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID, params);
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
    this.extenderMotor.getEncoder().setPosition(0.0);
  }

  @Override
  public void setExtenderPosition(double encoderPos) {
    extenderMotor.setPidPos(encoderPos);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }

  @Override
  public void configurePID(double p, double i, double d) {
    this.extenderMotor.setPid(p, i, d);
  }

  @Override
  public void configurePID(NeoPidConfig params) {
    this.extenderMotor.configurePID(params);
  }
}
