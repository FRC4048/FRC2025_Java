// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class AlgaeByeByeTiltSubsystem extends SubsystemBase {
  /** Creates a new AlgaeByeByeTiltSubsystem. */
  private final LoggableSystem<AlgaeByeByeTiltIO, BuildableFolderMotorInputs<SparkMax>>
      algaeTiltSystem;

  public AlgaeByeByeTiltSubsystem(AlgaeByeByeTiltIO io) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("AlgaeByeByeTiltSubsystem");
    BuildableFolderMotorInputs<SparkMax> inputs = builder.addAll().build();
    algaeTiltSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    algaeTiltSystem.updateInputs();
    SmartShuffleboard.put("Bye Bye", "ForwardTripped?", algaeTiltSystem.getInputs().fwdLimit());
    SmartShuffleboard.put("Bye Bye", "ReverseTripped?", algaeTiltSystem.getInputs().revLimit());
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
