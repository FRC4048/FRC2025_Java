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
  public static final double CAMERA_OFFSET_FROM_CENTER_X = 0;
  public static final double CAMERA_OFFSET_FROM_CENTER_Y = 0;
  public static final long POSE_BUFFER_STORAGE_TIME = 2;
  public static final double VISION_CONSISTANCY_THRESHOLD = 0.25;

  public enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,
    /** Replaying from a log file. */
    REPLAY
  }
}
