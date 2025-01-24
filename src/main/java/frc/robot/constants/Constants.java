package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants extends Constants2025 {
  public static final Mode simMode = Mode.REPLAY;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public static final long MAX_LOG_TIME_WAIT = 10;
  public static final boolean ENABLE_LOGGING = true;
  public static final long GYRO_THREAD_RATE_MS = 10;
  public static final int SERVER_SOCKET_CONNECTION_TIMEOUT = 2000;
  public static final int SERVER_SOCKET_ATTEMPT_DELAY = 100;

  public enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,
    /** Replaying from a log file. */
    REPLAY
  }
}
