// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.motor.MotorSimulator;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

/** Add your docs here. */
public class SimClimberIO extends RealClimberIO {
  private final MotorSimulator motorSimulator;

  public SimClimberIO() {
    super();
    this.motorSimulator = new MotorSimulator(climberMotor);
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      motorSimulator.stepSimulation();
    }
    SmartShuffleboard.put("Climber", "Location", motorSimulator.getEncoder().getPosition());
    SmartShuffleboard.put("Climber", "speed", motorSimulator.getEncoder().getVelocity());
  }
}
