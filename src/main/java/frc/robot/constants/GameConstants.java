package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class GameConstants {

  // Controllers
  public static final int LEFT_JOYSTICK_ID = 0;
  public static final int RIGHT_JOYSTICK_ID = 1;
  public static final int XBOX_CONTROLLER_ID = 2;

  // Debug
  public static final boolean SWERVE_DEBUG = false;

  // Speeds
  public static final double MAX_AUTO_ALIGN_SPEED = 0.9;
  public static final double ELEVATOR_RISE_SPEED = 0.66;
  public static final int ALGAE_EXTENDER_MOTOR_SPEED = 4; // TODO: change later

  // Timeouts
  public static final int SERVER_SOCKET_CONNECTION_TIMEOUT = 2000;
  public static final int ELEVATOR_TIMEOUT = 10; // TODO: change later
  public static final int SHOOT_CORAL_TIMEOUT = 10;

  // Logging
  public static final long MAX_LOG_TIME_WAIT = 10;
  public static final boolean ENABLE_LOGGING = true;

  // Treshholds
  public static final double VISION_CONSISTANCY_THRESHOLD = 0.25;
  public static final double ENCODER_THRESHHOLD_ELEVATOR = 12; // TODO: change later
  public static final double AUTO_ALIGN_THRESHOLD = 2.3; // degrees //TODO: change later

  // Mode
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

  // Limits
  public static final int DRIVE_SMART_LIMIT = 38; // TODO: change later
  public static final int DRIVE_SECONDARY_LIMIT = 48; // TODO: change later
  public static final double DRIVE_RAMP_RATE_LIMIT = 0.1; // TODO: change later

  // Drive PID
  public static final double DRIVE_PID_P = 1; // TODO: change later
  public static final double DRIVE_PID_I = 0; // TODO: change later
  public static final double DRIVE_PID_D = 0; // TODO: change later
  public static final double DRIVE_PID_FF_S = 1; // TODO: change later
  public static final double DRIVE_PID_FF_V = 2.8; // TODO: change later

  // Steer PID
  public static final double STEER_PID_P = 0.3; // TODO: change later
  public static final double STEER_PID_I = 0; // TODO: change later
  public static final double STEER_PID_D = 0.005; // TODO: change later
  public static final double STEER_PID_FF_S = 0; // 0.2; //TODO: change later
  public static final double STEER_PID_FF_V = 0; // 0.8; //TODO: change later

  // Other
  public static final double GRAVITY = -9.81;
  public static final long GYRO_THREAD_RATE_MS = 10;
  public static final int SERVER_SOCKET_ATTEMPT_DELAY = 100;
  public static final int TCP_SERVER_PORT = 5806;
  public static final double INITIAL_ELEVATOR_HEIGHT = 0; // TODO: change later
  public static final boolean ENABLE_VISION = true;
  public static final long POSE_BUFFER_STORAGE_TIME = 2;
}
