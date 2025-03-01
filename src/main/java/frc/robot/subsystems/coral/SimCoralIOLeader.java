// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coral;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.IntakeSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

/** Add your docs here. */
public class SimCoralIOLeader extends RealCoralIOLeader {
  private final IntakeSimulator simCoralIOLeader;

  public SimCoralIOLeader(LoggedMechanismLigament2d ligament) {
    super();
    simCoralIOLeader = new IntakeSimulator(shooterMotorLeader, ligament);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      simCoralIOLeader.stepSimulation();
    }
  }
}
