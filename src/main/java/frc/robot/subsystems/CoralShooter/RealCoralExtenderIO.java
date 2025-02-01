package frc.robot.subsystems.CoralShooter;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.constants.Constants;

import com.revrobotics.spark.config.SparkMaxConfig;

public class RealCoralExtenderIO implements CoralShooterIO {
  private final SparkMax shooterMotor1; // TODO: change later to whatever
  private final SparkMax shooterMotor2; // TODO: change later to whatever

  public RealCoralExtenderIO() {
    shooterMotor1 = new SparkMax(Constants.SHOOTER_MOTOR_1_ID, SparkMax.MotorType.kBrushless);
    shooterMotor2 = new SparkMax(Constants.SHOOTER_MOTOR_2_ID, SparkMax.MotorType.kBrushless);


  }

  private void configureMotor() {
    SparkMaxConfig coralConfig = new SparkMaxConfig();
    coralConfig.idleMode(IdleMode.kBrake);
    shooterMotor1.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
    shooterMotor2.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor1.set(speed);
    this.shooterMotor2.set(speed);
  }


  @Override
  public void stopShooterMotors() {
    this.shooterMotor1.set(0);
    this.shooterMotor2.set(0);
  }

 @Override
  public void updateInputs(CoralShooterInput inputs) {

  }
  

}
