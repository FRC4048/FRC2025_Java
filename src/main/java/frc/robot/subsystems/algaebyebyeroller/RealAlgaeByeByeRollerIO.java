// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

/** Add your docs here. */
public class RealAlgaeByeByeRollerIO implements AlgaeByeByeRollerIO {
  protected final SparkMax removerMotor;

  public RealAlgaeByeByeRollerIO() {
    this.removerMotor =
        new SparkMax(Constants.ALGAE_BYEBYE_SPINING_ID, SparkLowLevel.MotorType.kBrushless);
    configureMotor();
  }

  private void configureMotor() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    removerMotor.configure(
        config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double speed) {
    this.removerMotor.set(speed);
  }

  @Override
  public void stopMotors() {
    this.removerMotor.set(0);
  }

  public void updateInputs(AlgaeByeByeRollerInputs inputs) {
    inputs.rollerMotorVelocity = removerMotor.getEncoder().getVelocity();
  }
}
