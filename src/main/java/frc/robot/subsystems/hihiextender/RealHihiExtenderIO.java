package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;
  private final NeoPidConfig initConfig;

  public RealHihiExtenderIO() {
    this.initConfig =
        new NeoPidConfig(true)
            .setP(Constants.HIHI_PID_P)
            .setI(Constants.HIHI_PID_I)
            .setD(Constants.HIHI_PID_D)
            .setIZone(Constants.HIHI_PID_I_ZONE)
            .setFF(Constants.HIHI_PID_FF)
            .setMaxVelocity(Constants.HIHI_PID_MAX_VEL)
            .setMaxAccel(Constants.HIHI_PID_MAX_ACC)
            .setAllowedError(Constants.HIHI_PID_ALLOWED_ERROR);
    this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID, new NeoPidConfig(true));
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
    this.extenderMotor.getNeoMotor().getEncoder().setPosition(0);
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
  public void configurePID(NeoPidConfig pidConfig) {
    extenderMotor.configurePID(pidConfig);
  }

  @Override
  public void updateInputs(PidMotorInputs inputs) {
    inputs.process(inputProvider);
  }

  @Override
  public NeoPidConfig getPIDConfig() {
    return initConfig;
  }
}
