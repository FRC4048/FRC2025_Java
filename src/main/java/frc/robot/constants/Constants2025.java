package frc.robot.constants;

import edu.wpi.first.math.util.Units;
import frc.robot.utils.SwerveModuleProfileV2;

public class Constants2025 extends GameConstants {

  // Elevator
  public static final int ELEVATOR_MOTOR_1_ID = 0; // TODO: change later
  public static final int ELEVATOR_MOTOR_2_ID = 1; // TODO: change later
  public static final double ELEVATOR_GEARING = 10; // TODO: change later
  public static final double CARRIAGE_MASS = 0.5; // In Kg, change later
  public static final double ELEVATOR_DRUM_RADIUS =
      Units.inchesToMeters(1); // In M(in), change later
  public static final double MIN_ELEVATOR_HEIGHT_METERS = 0; // in m
  public static final double MAX_ELEVATOR_HEIGHT_METERS = 1; // in m
  // Get from hardwareg

  // Coral Shooter
  public static final int SHOOTER_MOTOR_1_ID = 0; // TODO: change later
  public static final int SHOOTER_MOTOR_2_ID = 1; // TODO: change later
  public static final int SHOOTER_TILT_MOTOR_ID = 2; // TODO: change later

  // Algae Roller
  public static final int ALGAE_ROLLER_CAN_ID = 7; // TODO: change later

  // Algae Extender
  public static final int ALGAE_EXTENDER_MOTOR_ID = 3; // TODO: change later

  // Algae Bye-Bye
  public static final int ALGAE_BYEBYE_SPINING_ID = 1; // TODO: change later
  public static final int ALGAE_BYEBYE_TILT_ID = 2; // TODO: change later

  // Climber
  public static final int CLIMBER_MOTOR_ID = 7; // TODO: change later

  // DriveTrain
  public static final double BACK_RIGHT_ABS_ENCODER_ZERO = 0.47119; // TODO: change later
  public static final double FRONT_LEFT_ABS_ENCODER_ZERO = 0.2773; // TODO: change later
  public static final double BACK_LEFT_ABS_ENCODER_ZERO = -0.031; // TODO: change later
  public static final double FRONT_RIGHT_ABS_ENCODER_ZERO = -0.3974; // TODO: change later
  public static final SwerveModuleProfileV2 SWERVE_MODULE_PROFILE =
      SwerveModuleProfileV2.MK4; // TODO: change later

  // Drive
  public static final int DRIVE_FRONT_RIGHT_D = 59; // TODO: change later
  public static final int DRIVE_BACK_RIGHT_D = 60; // TODO: change later
  public static final int DRIVE_FRONT_LEFT_D = 58; // TODO: change later
  public static final int DRIVE_BACK_LEFT_D = 57; // TODO: change later
  public static final int DRIVE_CANCODER_FRONT_RIGHT = 39; // TODO: change later
  public static final int DRIVE_CANCODER_BACK_RIGHT = 40; // TODO: change later
  public static final int DRIVE_CANCODER_FRONT_LEFT = 38; // TODO: change later
  public static final int DRIVE_CANCODER_BACK_LEFT = 37; // TODO: change later

  // Steer
  public static final int DRIVE_FRONT_RIGHT_S = 29; // TODO: change later
  public static final int DRIVE_BACK_RIGHT_S = 30; // TODO: change later
  public static final int DRIVE_FRONT_LEFT_S = 28; // TODO: change later
  public static final int DRIVE_BACK_LEFT_S = 27; // TODO: change later

  // Hardware Specs
  public static final double WHEEL_RADIUS = 0.0508; // TODO: change later
  public static final double ROBOT_WIDTH = 0.8636; // TODO: change later
  public static final double ROBOT_LENGTH = 0.8636; // TODO: change later
  public static final double MAX_VELOCITY = 4.8; // 4 meters per second //TODO: change later
  public static final double MAX_ANGULAR_SPEED = 6 * Math.PI; // TODO: change later
}
