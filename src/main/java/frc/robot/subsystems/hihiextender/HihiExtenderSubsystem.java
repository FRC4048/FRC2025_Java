// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;
import frc.robot.utils.motor.TunablePIDManager;

public class HihiExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiExtenderIO, PidMotorInputs> system;

  private final TunablePIDManager pidConfig;

  public final NeoPidConfig initConfig;

  /** Creates a new Extender. */
  public HihiExtenderSubsystem(HihiExtenderIO io) {
    PidMotorInputs inputs = new PidMotorInputBuilder<>("HihiExtenderSubsystem").addAll().build();
    system = new LoggableSystem<>(io, inputs);
    this.initConfig =
        new NeoPidConfig(Constants.HIHI_USE_MAX_MOTION)
            .setP(Constants.HIHI_PID_P)
            .setI(Constants.HIHI_PID_I)
            .setD(Constants.HIHI_PID_D)
            .setIZone(Constants.HIHI_PID_I_ZONE)
            .setFF(Constants.HIHI_PID_FF)
            .setMaxVelocity(Constants.HIHI_PID_MAX_VEL)
            .setMaxAccel(Constants.HIHI_PID_MAX_ACC)
            .setAllowedError(Constants.HIHI_PID_ALLOWED_ERROR);
    initConfig.setCurrentLimit(Constants.HIHI_CURRENT_LIMIT);
    io.configurePID(initConfig);
    pidConfig = new TunablePIDManager("HiHi", io, initConfig);
    setExtenderPosition(0.0);
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

  public NeoPidConfig getInitConfig() {
    return initConfig;
  }
}
