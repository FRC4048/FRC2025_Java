package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;
import frc.robot.utils.motor.NeoPidMotorParams;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;

  public RealHihiExtenderIO() {
    NeoPidConfig config = new NeoPidConfig();
    config.setI(Constants.ALGAE_EXTENDER_MOTOR_ID)
    .setCurrentLimit(Constants.NEO_CURRENT_LIMIT)
    .setP(Constants.HIHI_EXTENDER_PID_P);
    NeoPidMotorParams params = NeoPidMotorParams.defaultParams();
    params.pidP = Constants.HIHI_EXTENDER_PID_P;
    params.currentLimit = 20;
    params.allowedError = Constants.HIHI_EXTENDER_ALLOWED_ERROR;
    params.maxVelocity = Constants.HIHI_EXTENDER_MAX_VELOCITY;
    params.maxAccel = Constants.HIHI_EXTENDER_MAX_ACCEL;
    // this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID, params);
    this.extenderMotor = new NeoPidMotor(config);
    // this.extenderMotor =
    //     new NeoPidMotor(
    //         new NeoPidConfig()
    //             .setId(Constants.ALGAE_EXTENDER_MOTOR_ID)
    //             .setCurrentLimit(Constants.NEO_CURRENT_LIMIT));
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
  public void configurePID(NeoPidMotorParams params) {
    this.extenderMotor.configurePID(params);
  }
}
