// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyetilt;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class AlgaeByeByeTiltSubsystem extends SubsystemBase {
  /** Creates a new AlgaeByeByeTiltSubsystem. */
  private final LoggableSystem<AlgaeByeByeTiltIO, MotorInputs> algaeTiltSystem;

  public AlgaeByeByeTiltSubsystem(AlgaeByeByeTiltIO io) {
    MotorInputs inputs = new MotorInputBuilder<>("AlgaeByeByeTiltSubsystem").addAll().build();
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
    return algaeTiltSystem.getInputs().getFwdLimit();
  }

  public boolean getReverseSwitchState() {
    return algaeTiltSystem.getInputs().getRevLimit();
  }
}
