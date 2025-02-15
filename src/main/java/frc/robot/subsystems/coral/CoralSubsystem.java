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
  private final LoggableSystem<CoralIO, MotorInputs> coralSystemLeader;
  private final LoggableSystem<CoralIO, MotorInputs> coralSystemFollower;

  /** Creates a new Shooter. */
  public CoralSubsystem(CoralIO ioFollower, CoralIO ioLeader) {
    MotorInputs followerInputs =
        new MotorInputBuilder<>("ClimberSubsystem/Follower")
            .encoderVelocity()
            .fwdLimit()
            .addStatus()
            .build();
    MotorInputs leaderInputs =
        new MotorInputBuilder<>("ClimberSubsystem/Leader")
            .encoderVelocity()
            .fwdLimit()
            .addStatus()
            .build();
    coralSystemFollower = new LoggableSystem<>(ioFollower, followerInputs);
    coralSystemLeader = new LoggableSystem<>(ioLeader, leaderInputs);
  }

  @Override
  public void periodic() {
    coralSystemLeader.updateInputs();
    coralSystemFollower.updateInputs();
  }

  public void setShooterSpeed(double speed) {
    coralSystemLeader.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    coralSystemLeader.getIO().stopShooterMotors();
  }

  public void SetidleMode(IdleMode mode) {
    coralSystemLeader.getIO().SetIdleMode(mode);
    coralSystemFollower.getIO().SetIdleMode(mode);
  }

  public void SetLimitSwitchState(boolean state) {
    coralSystemLeader.getIO().enableOrDisableLimitSwitch(state);
  }

  public boolean getForwardSwitchState() {
    return coralSystemLeader.getInputs().getFwdLimit();
  }
}
