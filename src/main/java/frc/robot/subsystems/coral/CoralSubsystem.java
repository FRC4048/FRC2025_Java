// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coral;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class CoralSubsystem extends SubsystemBase {
  private final LoggableSystem<CoralIOLeader, MotorInputs> coralSystemLeader;
  private final LoggableSystem<CoralIOFollower, MotorInputs> coralSystemFollower;
  private final LoggableSystem<CoralIOAligner, MotorInputs> coralSystemAligner;

  /** Creates a new Shooter. */
  public CoralSubsystem(
      CoralIOFollower ioFollower, CoralIOLeader ioLeader, CoralIOAligner ioAligner) {
    MotorInputs followerInputs =
        new MotorInputBuilder<>("CoralSubsystem/Follower")
            .encoderVelocity()
            .encoderPosition()
            .fwdLimit()
            .addStatus()
            .build();
    MotorInputs leaderInputs =
        new MotorInputBuilder<>("CoralSubsystem/Leader")
            .encoderVelocity()
            .encoderPosition()
            .fwdLimit()
            .addStatus()
            .build();
    MotorInputs alignerInputs =
        new MotorInputBuilder<>("CoralSubsystem/Aligner")
            .encoderVelocity()
            .encoderPosition()
            .addStatus()
            .build();
    coralSystemFollower = new LoggableSystem<>(ioFollower, followerInputs);
    coralSystemLeader = new LoggableSystem<>(ioLeader, leaderInputs);
    coralSystemAligner = new LoggableSystem<>(ioAligner, alignerInputs);
  }

  @Override
  public void periodic() {
    coralSystemLeader.updateInputs();
    coralSystemFollower.updateInputs();
    coralSystemAligner.updateInputs();
  }

  public void setAlignerSpeed(double speed) {
    coralSystemAligner.getIO().setAlignerSpeed(speed);
  }

  public void stopAligner() {
    coralSystemAligner.getIO().setAlignerSpeed(0);
  }

  public void setShooterSpeed(double speed) {
    coralSystemLeader.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    coralSystemLeader.getIO().stopShooterMotors();
  }

  public void setidleMode(IdleMode mode) {
    coralSystemLeader.getIO().setIdleMode(mode);
    coralSystemFollower.getIO().setIdleMode(mode);
  }

  public void setLimitSwitchState(boolean state) {
    coralSystemLeader.getIO().enableOrDisableLimitSwitch(state);
  }

  public boolean getForwardSwitchState() {
    return coralSystemLeader.getInputs().getFwdLimit();
  }
}
