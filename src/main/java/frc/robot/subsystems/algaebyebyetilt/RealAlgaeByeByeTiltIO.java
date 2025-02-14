// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

/** Add your docs here. */
public class RealAlgaeByeByeTiltIO implements AlgaeByeByeTiltIO {
  private final SparkMax removerTiltMotor; // SnowblowerMotor

  public RealAlgaeByeByeTiltIO() {

    this.removerTiltMotor =
        new SparkMax(Constants.ALGAE_BYEBYE_TILT_ID, SparkLowLevel.MotorType.kBrushless);
    configureMotor();
    resetEncoder();
  }

  public void configureMotor() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.limitSwitch.forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen);
    config.limitSwitch.reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen);
    config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    removerTiltMotor.configure(
        config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double speed) {
    this.removerTiltMotor.set(speed);
  }

  @Override
  public void stopMotors() {
    this.removerTiltMotor.set(0);
  }

  @Override
  public void resetEncoder() {
    this.removerTiltMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(MotorInputs<SparkMax> inputs) {
    inputs.process(removerTiltMotor);
  }
}
