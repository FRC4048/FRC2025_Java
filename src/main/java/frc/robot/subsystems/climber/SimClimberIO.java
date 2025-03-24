// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.ClimberSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

/** Add your docs here. */
public class SimClimberIO extends RealClimberIO {
  private final ClimberSimulator climberSimulator;

  public SimClimberIO(LoggedMechanismLigament2d ligament) {
    super();
    this.climberSimulator = new ClimberSimulator(climberMotor, ligament);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      climberSimulator.stepSimulation();
    }
  }
}
