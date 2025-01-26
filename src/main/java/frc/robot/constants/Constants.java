package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants extends Constants2025 {
  public static final Mode simMode = Mode.REPLAY;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public static final long MAX_LOG_TIME_WAIT = 10;
  public static final boolean ENABLE_LOGGING = true;

  public enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,
    /** Replaying from a log file. */
    REPLAY
  }
}
