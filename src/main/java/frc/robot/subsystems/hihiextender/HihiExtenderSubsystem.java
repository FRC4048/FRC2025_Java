// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;
import frc.robot.utils.logging.LoggableSystem;

public class HihiExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiExtenderIO, LimitedEncodedMotorInput> system;

  /** Creates a new Extender. */
  public HihiExtenderSubsystem(HihiExtenderIO io) {
    system = new LoggableSystem<>(io, new LimitedEncodedMotorInput(), HihiExtenderSubsystem.class);
  }

  @Override
  public void periodic() {
    system.updateInputs();
  }

  public void setExtenderSpeed(double speed) {
    system.getIO().setHihiExtenderSpeed(speed);
  }

  public void stopExtenderMotors() {
    system.getIO().stopHihiExtenderMotor();
  }

  public boolean getForwardSwitchState() {
    return system.getInputs().isFwdLimit();
  }

  public boolean getReverseSwitchState() {
    return system.getInputs().isRevLimit();
  }

  public void resetEncoder() {
    system.getIO().resetExtenderEncoder();
  }
}
