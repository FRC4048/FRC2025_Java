package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class GameConstants {

  //Controllers 
  public static final int LEFT_JOYSTICK_ID = 0;
  public static final int RIGHT_JOYSTICK_ID = 1;

  //Debug
  public static final boolean SWERVE_DEBUG = false;

  //Speeds
  public static final double MAX_AUTO_ALIGN_SPEED = 0.9;

  //Timeouts
  public static final int SERVER_SOCKET_CONNECTION_TIMEOUT = 2000;

  //Logging
  public static final long MAX_LOG_TIME_WAIT = 10;
  public static final boolean ENABLE_LOGGING = true;

  //Mode
  public static final Mode simMode = Mode.REPLAY;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,
    /** Replaying from a log file. */
    REPLAY
  }

  //Other
  public static final double GRAVITY = -9.81;
  public static final long GYRO_THREAD_RATE_MS = 10;
  public static final int SERVER_SOCKET_ATTEMPT_DELAY = 100;
}
