package frc.robot.utils.motor;

import frc.robot.utils.LoggedTunableNumber;
import frc.robot.utils.logging.PIDLoggableIO;

public class PIDTunableConfig {
  private final PIDLoggableIO io;
  private final NeoPidConfig initConfig;
  private final String prefix;
  private final LoggedTunableNumber PID_P;
  private final LoggedTunableNumber PID_I;
  private final LoggedTunableNumber PID_D;
  private final LoggedTunableNumber PID_I_ZONE;
  private final LoggedTunableNumber PID_FF;
  private final LoggedTunableNumber PID_MAX_VEL;
  private final LoggedTunableNumber PID_MAX_ACCEL;
  private final LoggedTunableNumber PID_ALLOWED_ERROR;
  private final boolean is550;

  public PIDTunableConfig(String prefix, PIDLoggableIO io, boolean is550) {
    this.io = io;
    this.initConfig = io.getPIDConfig();
    this.prefix = prefix;
    PID_P = new LoggedTunableNumber(prefix + "/PID_P", initConfig.getP());
    PID_I = new LoggedTunableNumber(prefix + "/PID_I", initConfig.getI());
    PID_D = new LoggedTunableNumber(prefix + "/PID_D", initConfig.getD());
    PID_I_ZONE = new LoggedTunableNumber(prefix + "/PID_I_ZONE", initConfig.getIZone());
    PID_FF = new LoggedTunableNumber(prefix + "/PID_FF", initConfig.getFF());
    PID_MAX_VEL = new LoggedTunableNumber(prefix + "/PID_MAX_VEL", initConfig.getMaxVelocity());
    PID_MAX_ACCEL = new LoggedTunableNumber(prefix + "/PID_MAX_ACCEL", initConfig.getMaxAccel());
    PID_ALLOWED_ERROR =
        new LoggedTunableNumber(prefix + "/PID_ALLOWED_ERROR", initConfig.getAllowedError());
    this.is550 = is550;
  }

  public void periodic() {
    LoggedTunableNumber.ifChanged(
        hashCode(),
        () ->
            io.configurePID(
                new NeoPidConfig(is550)
                    .setP(PID_P.get())
                    .setI(PID_I.get())
                    .setD(PID_D.get())
                    .setIZone(PID_I_ZONE.get())
                    .setFF(PID_FF.get())
                    .setMaxVelocity(PID_MAX_VEL.get())
                    .setMaxAccel(PID_MAX_ACCEL.get())
                    .setAllowedError(PID_ALLOWED_ERROR.get())),
        PID_P,
        PID_I,
        PID_D,
        PID_I_ZONE,
        PID_FF,
        PID_MAX_VEL,
        PID_MAX_ACCEL,
        PID_ALLOWED_ERROR);
  }
}
