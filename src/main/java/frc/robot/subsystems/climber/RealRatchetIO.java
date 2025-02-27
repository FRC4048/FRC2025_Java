// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.ServoInputs;

/** Add your docs here. */
public class RealRatchetIO implements RatchetIO {
  protected final Servo ratchetServo;

  public RealRatchetIO() {
    this.ratchetServo = new Servo(Constants.RATCHET_ID);
    this.ratchetServo.setBoundsMicroseconds(2200, 0, 1500, 0, 800);
  }

  @Override
  public void engageRatchet() {
    ratchetServo.setPosition(Constants.RATCHET_ENGAGE);
  }

  @Override
  public void disengageRatchet() {
    ratchetServo.setPosition(Constants.RATCHET_DISENGAGE);
  }

  @Override
  public void updateInputs(ServoInputs inputs) {
    inputs.process(null);
  }
}
