// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxEncoder;
import frc.robot.utils.diag.DiagSparkMaxSwitch;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

/** Add your docs here. */
public class RealAlgaeByeByeRollerIO implements AlgaeByeByeRollerIO {
  protected final SparkMax removerMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealAlgaeByeByeRollerIO() {
    this.removerMotor =
        new SparkMax(Constants.ALGAE_BYEBYE_SPINING_ID, SparkLowLevel.MotorType.kBrushless);
    inputProvider = new SparkMaxInputProvider(removerMotor);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "ByeBye", "RollerForward", 10, removerMotor));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "ByeBye", "RollerReverse", 10, removerMotor));
  }

  private void configureMotor() {
    SparkMaxConfig removerMotorConfig = new SparkMaxConfig();
    removerMotorConfig.idleMode(IdleMode.kBrake);
    removerMotorConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    removerMotor.configure(
        removerMotorConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double speed) {
    this.removerMotor.set(speed);
  }

  @Override
  public void stopMotors() {
    this.removerMotor.set(0);
  }

  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
