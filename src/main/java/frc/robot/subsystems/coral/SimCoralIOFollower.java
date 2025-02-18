// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coral;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;

public class SimCoralIOFollower extends RealCoralIOFollower {
  private final MotorSimulator SimCoralIOFollowerSim;

  public SimCoralIOFollower() {
    super();
    SimCoralIOFollowerSim = new MotorSimulator(shooterMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      SimCoralIOFollowerSim.stepSimulation();
    }
  }
}
