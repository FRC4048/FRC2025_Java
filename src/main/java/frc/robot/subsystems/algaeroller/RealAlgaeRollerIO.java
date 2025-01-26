package frc.robot.subsystems.algaeroller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.constants.Constants;

public class RealAlgaeRollerIO implements AlgaeRollerIO {
  private final SparkMax algaeRollerMotor;
  private final SparkMaxConfig algaeRollerConfig;
  private final WPI_TalonSRX algaeAngleMotor;
  public RealAlgaeRollerIO() {
    this.algaeAngleMotor = 
        new WPI_TalonSRX(Constants.ALGAE_ANGLE_MOTOR_ID);
    this.algaeRollerMotor =
        new SparkMax(Constants.ALGAE_ROLLER_CAN_ID, SparkLowLevel.MotorType.kBrushless);
    algaeRollerConfig = new SparkMaxConfig();
    
  
      }

  public void configureMotor(){
    this.algaeAngleMotor.setNeutralMode(NeutralMode.Brake);
   
    algaeRollerConfig
      .idleMode(IdleMode.kBrake);
      algaeRollerMotor.configure(algaeRollerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

  }
  @Override
  public void setSpeedRoller(double speed) {
    algaeRollerMotor.set(speed);
  }

  @Override
  public void stopRollerMotor() {
    algaeRollerMotor.set(0);
  }
  @Override
  public void setSpeedAngle(double speed) {
   algaeAngleMotor.set(speed);
  }
  @Override
  public void stopAngleMotor() {
   algaeAngleMotor.set(0);
  }
  @Override
  public void updateInputs(AlgaeRollerInputs inputs) {
    inputs.algaeRollerEncoder = algaeRollerMotor.getEncoder().getPosition();
    inputs.algaeAngleEncoder = algaeAngleMotor.getSelectedSensorPosition();
  }
}
