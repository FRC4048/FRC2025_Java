// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.LoggedTunableNumber;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.PIDTunableConfig;

public class HihiExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiExtenderIO, PidMotorInputs> system;

  private static final LoggedTunableNumber PID_P =
      new LoggedTunableNumber("HiHi/PID_P", Constants.HIHI_PID_P);
  private static final LoggedTunableNumber PID_I =
      new LoggedTunableNumber("HiHi/PID_I", Constants.HIHI_PID_I);
  private static final LoggedTunableNumber PID_D =
      new LoggedTunableNumber("HiHi/PID_D", Constants.HIHI_PID_D);

  private final PIDTunableConfig pidConfig;

  /** Creates a new Extender. */
  public HihiExtenderSubsystem(HihiExtenderIO io) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("HihiExtenderSubsystem").addAll().build();
    system = new LoggableSystem<>(io, inputs);
    pidConfig = new PIDTunableConfig("HiHi", new NeoPidConfig(), io, PID_P, PID_I, PID_D);
  }

  @Override
  public void periodic() {
    pidConfig.periodic();
    system.updateInputs();
  }

  public void setExtenderSpeed(double speed) {
    system.getIO().setHihiExtenderSpeed(speed);
  }

  public void stopExtenderMotors() {
    system.getIO().stopHihiExtenderMotor();
  }

  public boolean getForwardSwitchState() {
    return system.getInputs().getFwdLimit();
  }

  public boolean getReverseSwitchState() {
    return system.getInputs().getRevLimit();
  }

  public void resetEncoder() {
    system.getIO().resetExtenderEncoder();
  }

  public void setExtenderPosition(double encoderPos) {
    system.getIO().setExtenderPosition(encoderPos);
  }
}
