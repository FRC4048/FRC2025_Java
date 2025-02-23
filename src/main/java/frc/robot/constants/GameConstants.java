package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.utils.SwerveModuleProfileV2;

public class GameConstants {

  // Controllers
  public static final int LEFT_JOYSTICK_ID = 0;
  public static final int RIGHT_JOYSTICK_ID = 1;
  public static final int XBOX_CONTROLLER_ID = 2;

  // Diags
  public static final double BYEBYE_ROLLER_DIAGS_ENCODER = 1;
  public static final double HIHI_ROLLER_DIAGS_ENCODER = 1;
  public static final double ELEVATOR_DIAGS_ENCODER = 1;
  public static final double HIHI_EXTENDER_DIAGS_ENCODER = 1;

  // Debug
  public static final boolean SWERVE_DEBUG = true;
  public static final boolean INTAKE_DEBUG = true;
  public static final boolean CLIMBER_DEBUG = true;
  public static final boolean ELEVATOR_DEBUG = true;
  public static final boolean CORAL_DEBUG = true;
  public static final boolean HIHI_DEBUG = true;
  public static final boolean BYEBYE_DEBUG = true;
  public static final boolean COMMAND_DEBUG = true;
  public static final boolean INPUTS_DEBUG = true;
  public static final boolean TUNING_MODE = true;

  // Speeds
  public static final double MAX_AUTO_ALIGN_SPEED = 0.9;
  public static final double ELEVATOR_RISE_SPEED = 0.66;
  public static final double CLIMBER_CLOSE_SPEED = 0.66; // TODO: change later
  public static final double ELEVATOR_LOWER_SPEED = -0.5;
  public static final int ALGAE_EXTENDER_MOTOR_SPEED = 4; // TODO: change later
  public static final double BYEBYE_ROLLER_SPEED = 0.5;
  public static final double TILT_SPEED = 0.15;
  public static final double BYEBYE_FORWARD_SPEED = 0.5; // TODO: change later
  public static final double BYEBYE_REVERSE_SPEED = -0.5; // TODO: change later
  public static final double INTAKE_MOTOR_SPEED = 0.25;
  public static final double INTAKE_TILT_VELOCITY = 0.5;
  public static final double CORAL_SHOOTER_SPEED = 0.5;
  public static final double HIHI_EXTEND_SPEED = 0.4;
  public static final double HIHI_RETRACT_SPEED = -0.15;
  public static final double HIHI_INTAKE_SPEED = 0.7;
  public static final double HIHI_SHOOT_SPEED = -0.9;
  public static final double CLIMBER_SPEED = 0.5;
  public static final double CLIMBER_RISE_SPEED = 0.5;
  public static final double HIHI_INTAKE_BASE_VELOCITY = 7000.0;

  // Timeouts
  public static final int SERVER_SOCKET_CONNECTION_TIMEOUT = 2000;
  public static final int ELEVATOR_TIMEOUT = 10;
  public static final int ELEVATOR_RESET_TIMEOUT = 10;
  public static final int BYEBYE_SPIN_ROLLER_TIMEOUT = 10;
  public static final int BYEBYE_SPIN_TILT_TIMEOUT = 5;
  public static final int ELEVATOR_TO_POSITION_TIMEOUT = 10; // TODO: change later
  public static final int BYEBYE_FORWARD_TIMEOUT = 10; // TODO: change later
  public static final int BYEBYE_REVERSE_TIMEOUT = 10; // TODO: change later
  public static final int SHOOT_CORAL_TIMEOUT = 1;
  public static final int CORAL_FWR_TIMEOUT = 10;
  public static final int ROLL_ALGAE_TIMEOUT = 10;
  public static final double HIHI_EXTEND_TIMEOUT = 10;
  public static final double HIHI_RETRACT_TIMEOUT = 10;
  public static final double HIHI_ROLLER_OUT_TIMEOUT = 5;
  public static final double HIHI_ROLLER_IN_TIMEOUT = 5;
  public static final int INTAKE_CORAL_TIMEOUT = 10;
  public static final int CLOSE_CLIMBER_TIMEOUT = 10; // TODO: change later
  public static final int RESET_CLIMBER_TIMEOUT = 10;
  public static final double HIHI_INTAKE_TIMEOUT = 10; // TODO: Change Later

  // Logging
  public static final long MAX_LOG_TIME_WAIT = 10;
  public static final boolean ENABLE_LOGGING = true;

  // Treshholds
  public static final double VISION_CONSISTANCY_THRESHOLD = 0.25;
  public static final double AUTO_ALIGN_THRESHOLD = 2.3; // degrees //TODO: change later
  public static final double ELEVATOR_ENCODER_THRESHHOLD = 1; // TODO: Change Later
  public static final int HIHI_EXTENDER_TICK_LIMIT = 10;

  // Mode
  public static final Mode simMode = Mode.SIM;
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
  public static final int NEO_CURRENT_LIMIT = 20;
  public static final int HIHI_CURRENT_LIMIT = 10;

  // HiHi PID
  public static final double HIHI_PID_P = 0.02; // 0.08 with max motion
  public static final double HIHI_PID_I = 0;
  public static final double HIHI_PID_D = 0;
  public static final double HIHI_PID_I_ZONE = 0;
  public static final double HIHI_PID_FF = 0;
  public static final double HIHI_PID_MAX_VEL = 3000;
  public static final double HIHI_PID_MAX_ACC = 2500;
  public static final double HIHI_PID_ALLOWED_ERROR = 0.5;
  public static final boolean HIHI_USE_MAX_MOTION = false;

  // Elevator PID
  public static final boolean ELEVATOR_USE_MAX_MOTION = true;

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

  // Lengths
  public static final double ELEVATOR_DRUM_RADIUS =
      Units.inchesToMeters(1); // In M(in), change later
  public static final double MIN_ELEVATOR_HEIGHT_METERS = 0; // in m
  public static final double MAX_ELEVATOR_HEIGHT_METERS = -200; // in m
  public static final double INITIAL_ELEVATOR_HEIGHT = 0; // TODO: change later
  public static final double HIHI_LENGTH = 0.5; // TODO: change later

  // Angles
  public static final Rotation2d HIHI_MIN_ANGLE =
      Rotation2d.fromRadians(-Math.PI / 2); // TODO: change later
  public static final Rotation2d HIHI_MAX_ANGLE =
      Rotation2d.fromRadians(Math.PI * 5 / 12); // TODO: change later
  public static final double HIHI_EXTEND_POSITION = 15.5; // TODO: change later
  public static final double HIHI_RETRACT_POSITION = 0.0; // TODO: change later

  // Zeros
  public static final double BACK_RIGHT_ABS_ENCODER_ZERO = 0.306885; // TODO: change later
  public static final double FRONT_LEFT_ABS_ENCODER_ZERO = -0.059082; // TODO: change later
  public static final double BACK_LEFT_ABS_ENCODER_ZERO = 0.379150; // TODO: change later
  public static final double FRONT_RIGHT_ABS_ENCODER_ZERO = -0.100586; // TODO: change later

  // Drivetrain
  public static final double WHEEL_RADIUS = 0.0508; // TODO: change later
  public static final double ROBOT_WIDTH = 0.8636; // TODO: change later
  public static final double ROBOT_LENGTH = 0.8636; // TODO: change later
  public static final double MAX_VELOCITY = 3.81; // 4 meters per second //TODO: change later
  public static final double MAX_ANGULAR_SPEED = 6 * Math.PI; // TODO: change later

  // Other
  public static final double GRAVITY = -9.81;
  public static final long GYRO_THREAD_RATE_MS = 10;
  public static final int SERVER_SOCKET_ATTEMPT_DELAY = 100;
  public static final int TCP_SERVER_PORT = 5806;
  public static final boolean ENABLE_VISION = true;
  public static final long POSE_BUFFER_STORAGE_TIME = 2;
  public static final double ELEVATOR_GEARING = 10; // TODO: change later
  public static final double CARRIAGE_MASS = 25.4; // In Kg, change later
  public static final SwerveModuleProfileV2 SWERVE_MODULE_PROFILE =
      SwerveModuleProfileV2.MK4I; // TODO: change later
  public static final double HIHI_GEARING = 45.0; // TODO: change later
  public static final double HIHI_INERTIA = 1.0; // TODO: change later
  public static final boolean HI_HI_SIMULATE_GRAVITY = true;
  public static final int MAX_VALID_TICKS_INTAKE = 15; // TODO: Change Later
  public static final int MAX_VALID_TICKS_ELEVATOR = 10; // TODO: Change Later
}
