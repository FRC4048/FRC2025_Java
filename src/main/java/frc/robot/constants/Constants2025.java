package frc.robot.constants;

import frc.robot.utils.SwerveModuleProfileV2;

public class Constants2025 extends GameConstants {
  public static final int ELEVATOR_MOTOR_1_ID = 0; // TODO: change later
  public static final double INITIAL_ELEVATOR_HEIGHT = 0; // TODO: change later
  public static final int ELEVATOR_MOTOR_2_ID = 1; // TODO: change later
  public static final int SHOOTER_MOTOR_1_ID = 0; // TODO: change later
  public static final int SHOOTER_MOTOR_2_ID = 1; // TODO: change later
  public static final int SHOOTER_TILT_MOTOR_ID = 2; // TODO: change later
  public static final double AUTO_ALIGN_THRESHOLD = 2.3; // degrees //TODO: change later
  public static final double ALGAE_ROLLER_CAN_ID = 7; //change later

  public static final double BACK_RIGHT_ABS_ENCODER_ZERO = 0.47119; // TODO: change later
  public static final double FRONT_LEFT_ABS_ENCODER_ZERO = 0.2773; // TODO: change later
  public static final double BACK_LEFT_ABS_ENCODER_ZERO = -0.031; // TODO: change later
  public static final double FRONT_RIGHT_ABS_ENCODER_ZERO = -0.3974; // TODO: change later
  public static final int DRIVE_FRONT_RIGHT_S = 29; // TODO: change later
  public static final int DRIVE_FRONT_RIGHT_D = 59; // TODO: change later
  public static final int DRIVE_BACK_RIGHT_S = 30; // TODO: change later
  public static final int DRIVE_BACK_RIGHT_D = 60; // TODO: change later
  public static final int DRIVE_FRONT_LEFT_S = 28; // TODO: change later
  public static final int DRIVE_FRONT_LEFT_D = 58; // TODO: change later
  public static final int DRIVE_BACK_LEFT_S = 27; // TODO: change later
  public static final int DRIVE_BACK_LEFT_D = 57; // TODO: change later
  public static final int DRIVE_CANCODER_FRONT_RIGHT = 39; // TODO: change later
  public static final int DRIVE_CANCODER_BACK_RIGHT = 40; // TODO: change later
  public static final int DRIVE_CANCODER_FRONT_LEFT = 38; // TODO: change later
  public static final int DRIVE_CANCODER_BACK_LEFT = 37; // TODO: change later
  public static final double DRIVE_PID_P = 1; // TODO: change later
  public static final double DRIVE_PID_I = 0; // TODO: change later
  public static final double DRIVE_PID_D = 0; // TODO: change later
  public static final double DRIVE_PID_FF_S = 1; // TODO: change later
  public static final double DRIVE_PID_FF_V = 2.8; // TODO: change later
  public static final double STEER_PID_P = 0.3; // TODO: change later
  public static final double STEER_PID_I = 0; // TODO: change later
  public static final double STEER_PID_D = 0.005; // TODO: change later
  public static final double STEER_PID_FF_S = 0; // 0.2; //TODO: change later
  public static final double STEER_PID_FF_V = 0; // 0.8; //TODO: change later
  public static final double MAX_ANGULAR_SPEED = 6 * Math.PI; // TODO: change later
  public static final double WHEEL_RADIUS = 0.0508; // TODO: change later
  public static final SwerveModuleProfileV2 SWERVE_MODULE_PROFILE =
      SwerveModuleProfileV2.MK4; // TODO: change later
  public static final double ROBOT_WIDTH = 0.8636; // TODO: change later
  public static final double ROBOT_LENGTH = 0.8636; // TODO: change later
  public static final double MAX_VELOCITY = 4.8; // 4 meters per second //TODO: change later
  public static final int DRIVE_SMART_LIMIT = 38; // TODO: change later
  public static final int DRIVE_SECONDARY_LIMIT = 48; // TODO: change later
  public static final double DRIVE_RAMP_RATE_LIMIT = 0.1; // TODO: change later
}
