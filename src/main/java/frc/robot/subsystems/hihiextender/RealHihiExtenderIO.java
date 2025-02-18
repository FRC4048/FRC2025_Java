package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.LoggedTunableNumber;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.NeoPidMotorInputProvider;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.NeoPidMotor;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RealHihiExtenderIO implements HihiExtenderIO {
  protected final NeoPidMotor extenderMotor;
  private final NeoPidMotorInputProvider inputProvider;
  private static final LoggedTunableNumber PID_P =
      new LoggedTunableNumber("HiHi/PID_P", Constants.HIHI_PID_P);
  private static final LoggedTunableNumber PID_I =
      new LoggedTunableNumber("HiHi/PID_I", Constants.HIHI_PID_I);
  private static final LoggedTunableNumber PID_D =
      new LoggedTunableNumber("HiHi/PID_D", Constants.HIHI_PID_D);

  public RealHihiExtenderIO() {
    this.extenderMotor =
        new NeoPidMotor(
            new NeoPidConfig()
                .setId(Constants.ALGAE_EXTENDER_MOTOR_ID)
                .setCurrentLimit(Constants.NEO_CURRENT_LIMIT)
                .setP(PID_P.get())
                .setI(PID_I.get())
                .setD(PID_D.get()));
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
    extenderMotor.setPid(PID_P.get(), PID_I.get(), PID_D.get());
    Logger.recordOutput("HiHi/PID_D", PID_D.get());
    inputs.process(inputProvider);
  }
}
