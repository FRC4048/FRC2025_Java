// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeassist;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;

/** Add your docs here. */
public class SimIntakeAssistIO extends RealIntakeAssistIO {
  private final MotorSimulator motorSimulator;

  public SimIntakeAssistIO() {
    super();
    this.motorSimulator = new MotorSimulator(intakeAssistMotor, null);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      motorSimulator.stepSimulation();
    }
  }
}
