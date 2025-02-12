// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkInputs;

public class AlgaeByeByeTiltSubsystem extends SubsystemBase {
  /** Creates a new AlgaeByeByeTiltSubsystem. */
  private final LoggableSystem<AlgaeByeByeTiltIO, MotorInputs<SparkMax>> algaeTiltSystem;

  public AlgaeByeByeTiltSubsystem(AlgaeByeByeTiltIO io) {
    SparkInputs.Builder<?> builder = new SparkInputs.Builder<>("AlgaeByeByeTiltSubsystem");
    MotorInputs<SparkMax> inputs = builder.addAll().build();
    algaeTiltSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    algaeTiltSystem.updateInputs();
  }

  public void setSpeed(double speed) {
    algaeTiltSystem.getIO().setSpeed(speed);
  }

  public void stopMotors() {
    algaeTiltSystem.getIO().stopMotors();
  }

  public void resetEncoder() {
    algaeTiltSystem.getIO().resetEncoder();
  }

  public double getAngle() {
    return algaeTiltSystem.getInputs().getEncoderPosition();
  }

  public boolean getForwardSwitchState() {
    return algaeTiltSystem.getInputs().fwdLimit();
  }

  public boolean getReverseSwitchState() {
    return algaeTiltSystem.getInputs().revLimit();
  }
}
