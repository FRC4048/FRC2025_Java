package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkBase;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxEncoder;
import frc.robot.utils.diag.DiagSparkMaxSwitch;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;

  public RealHihiExtenderIO() {
    this.extenderMotor = new NeoPidMotor(Constants.ALGAE_EXTENDER_MOTOR_ID, false);
    inputProvider = new NeoPidMotorInputProvider(extenderMotor);
    resetExtenderEncoder();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "HiHiExtender",
                "ForwardLimit",
                extenderMotor.getNeoMotor(),
                DiagSparkMaxSwitch.Direction.FORWARD));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "HiHiExtender",
                "ReverseLimit",
                extenderMotor.getNeoMotor(),
                DiagSparkMaxSwitch.Direction.REVERSE));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "HiHiExtender",
                "Encoder",
                Constants.HIHI_EXTENDER_DIAGS_ENCODER,
                extenderMotor.getNeoMotor()));
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
    extenderMotor.setPidPos(encoderPos, SparkBase.ControlType.kPosition);
  }

  @Override
  public void configurePID(NeoPidConfig pidConfig) {
    extenderMotor.configurePID(pidConfig);
  }

  @Override
  public void updateInputs(PidMotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
