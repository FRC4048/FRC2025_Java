package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class RealCoralIO implements CoralIO {
  private final SparkMax shooterMotor1; // TODO: change later to whatever
  private final SparkMax shooterMotor2; // TODO: change later to whatever
  private final SparkMax shooterTiltMotor; // TODO: change later to whatever
   private final SparkBaseConfig coralConfig;

  public RealCoralIO(int RealCoralIO) {
    shooterMotor1 = new SparkMax(RealCoralIO, SparkMax.MotorType.kBrushless);
    shooterMotor2 = new SparkMax(RealCoralIO, SparkMax.MotorType.kBrushless);
    shooterTiltMotor = new SparkMax(RealCoralIO, SparkMax.MotorType.kBrushless);
    coralConfig = new SparkMaxConfig();
    configureMotor();
    resetTiltEncoder();
    
  }
  private void configureMotor() {
    coralConfig
      .idleMode(IdleMode.kBrake);
       shooterMotor1.configure(coralConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
       shooterMotor2.configure(coralConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
       shooterTiltMotor.configure(coralConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
      }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor1.set(speed);
    this.shooterMotor2.set(speed);
  }

  @Override
  public void setTiltAngularVelocity(double angleSpeed) {
    this.shooterTiltMotor.set(angleSpeed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotor1.set(0);
    this.shooterMotor2.set(0);
  }

  @Override
  public void stopTiltMotors() {
    this.shooterTiltMotor.set(0);
  }

  @Override
  public void resetTiltEncoder() {
    this.shooterTiltMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(CoralInputs inputs) {
    inputs.shooterSpeed = shooterMotor1.get();
    inputs.fwdTripped = shooterTiltMotor.getForwardLimitSwitch().isPressed();
    inputs.revTripped = shooterTiltMotor.getReverseLimitSwitch().isPressed();
    inputs.angleSpeed = shooterTiltMotor.get();
  }
}
