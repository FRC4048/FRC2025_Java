// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.NeoPidMotorParams;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class HihiExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiExtenderIO, MotorInputs> system;

  private double lastPidP = Constants.HIHI_EXTENDER_PID_P;
  private double lastPidI = 0.0;
  private double lastPidD = 0.0;
  private double lastPidFF = 0.0;
  private double lastMaxVelocity = Constants.HIHI_EXTENDER_MAX_VELOCITY;
  private double lastMaxAccel = Constants.HIHI_EXTENDER_MAX_ACCEL;
  private double lastAllowedError = Constants.HIHI_EXTENDER_ALLOWED_ERROR;

  /** Creates a new Extender. */
  public HihiExtenderSubsystem(HihiExtenderIO io) {
    MotorInputs inputs = new PidMotorInputBuilder<>("HihiExtenderSubsystem").addAll().build();
    system = new LoggableSystem<>(io, inputs);

    if (Constants.HIHI_EXTEND_DEBUG) {
      SmartShuffleboard.put("HiHiExtender/Config", "pidP", lastPidP);
      SmartShuffleboard.put("HiHiExtender/Config", "pidI", lastPidI);
      SmartShuffleboard.put("HiHiExtender/Config", "pidD", lastPidD);
      SmartShuffleboard.put("HiHiExtender/Config", "pidFF", lastPidFF);
      SmartShuffleboard.put("HiHiExtender/Config", "maxVelocity", lastMaxVelocity);
      SmartShuffleboard.put("HiHiExtender/Config", "maxAccel", lastMaxAccel);
      SmartShuffleboard.put("HiHiExtender/Config", "allowedError", lastAllowedError);
    }
  }

  @Override
  public void periodic() {
    system.updateInputs();

    if (Constants.HIHI_EXTEND_DEBUG) {
      double newPidP = SmartShuffleboard.getDouble("HiHiExtender/Config", "pidP", lastPidP);
      double newPidI = SmartShuffleboard.getDouble("HiHiExtender/Config", "pidI", lastPidI);
      double newPidD = SmartShuffleboard.getDouble("HiHiExtender/Config", "pidD", lastPidD);
      double newPidFF = SmartShuffleboard.getDouble("HiHiExtender/Config", "pidFF", lastPidFF);
      double newMaxVelocity =
          SmartShuffleboard.getDouble("HiHiExtender/Config", "maxVelocity", lastMaxVelocity);
      double newMaxAccel =
          SmartShuffleboard.getDouble("HiHiExtender/Config", "maxAccel", lastMaxAccel);
      double newAllowedError =
          SmartShuffleboard.getDouble("HiHiExtender/Config", "allowedError", lastAllowedError);

      if (newPidP != lastPidP
          || newPidI != lastPidI
          || newPidD != lastPidD
          || newPidFF != lastPidFF
          || newMaxVelocity != lastMaxVelocity
          || newMaxAccel != lastMaxAccel
          || newAllowedError != lastAllowedError) {

        lastPidP = newPidP;
        lastPidI = newPidI;
        lastPidD = newPidD;
        lastPidFF = newPidFF;
        lastMaxVelocity = newMaxVelocity;
        lastMaxAccel = newMaxAccel;
        lastAllowedError = newAllowedError;

        NeoPidMotorParams params = new NeoPidMotorParams();
        params.pidP = newPidP;
        params.pidI = newPidI;
        params.pidD = newPidD;
        params.pidFF = newPidFF;
        params.maxAccel = newMaxAccel;
        params.maxVelocity = newMaxVelocity;
        params.allowedError = newAllowedError;

        system.getIO().configurePID(params);
      }
    }
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

  public void configurePID(double P, double I, double D) {
    system.getIO().configurePID(P, I, D);
  }
}
