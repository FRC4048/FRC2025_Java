package frc.robot.utils.motor;

import frc.robot.utils.LoggedTunableNumber;
import frc.robot.utils.logging.PIDLoggableIO;

public class PIDTunableConfig {
  private final PIDLoggableIO io;
  private final LoggedTunableNumber[] tunables;
  private final NeoPidConfig initConfig;
  private final String prefix;

  public PIDTunableConfig(
      String prefix, NeoPidConfig initConfig, PIDLoggableIO io, LoggedTunableNumber... tunables) {
    this.io = io;
    this.tunables = tunables;
    this.initConfig = initConfig;
    this.prefix = prefix;
  }

  public void periodic() {
    LoggedTunableNumber.ifChanged(
        hashCode(),
        () ->
            io.configurePID(
                new NeoPidConfig()
                    .setP(tunables[0].get())
                    .setI(tunables[1].get())
                    .setD(tunables[2].get())
                    .setIZone(tunables[3].get())
                    .setFF(tunables[4].get())
                    .setMaxVelocity(tunables[5].get())
                    .setMaxAccel(tunables[6].get())
                    .setAllowedError(tunables[7].get())));
  }
}
