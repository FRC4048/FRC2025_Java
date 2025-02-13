// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkInputs;

public class HihiExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<HihiExtenderIO, MotorInputs<SparkMax>> system;

  /** Creates a new Extender. */
  public HihiExtenderSubsystem(HihiExtenderIO io) {
    SparkInputs.Builder<?> builder = new SparkInputs.Builder<>("HihiExtenderSubsystem");
    MotorInputs<SparkMax> inputs = builder.addAll().build();
    system = new LoggableSystem<>(io, inputs);
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
    return system.getInputs().getFwdLimit();
  }

  public boolean getReverseSwitchState() {
    return system.getInputs().getRevLimit();
  }

  public void resetEncoder() {
    system.getIO().resetExtenderEncoder();
  }
}
