// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class CoralSubsystem extends SubsystemBase {
  private final LoggableSystem<CoralIO, BuildableFolderMotorInputs<SparkMax>> coralSystemLeader;
  private final LoggableSystem<CoralIO, BuildableFolderMotorInputs<SparkMax>> coralSystemFollower;

  /** Creates a new Shooter. */
  public CoralSubsystem(CoralIO io) {
    SparkMaxInputBuilder followerBuilder = new SparkMaxInputBuilder("CoralSubsystem/Follower");
    BuildableFolderMotorInputs<SparkMax> followerInputs =
        followerBuilder.encoderVelocity().fwdLimit().addStatus().build();

    SparkMaxInputBuilder leaderBuilder = new SparkMaxInputBuilder("CoralSubsystem/Leader");
    BuildableFolderMotorInputs<SparkMax> leaderInputs =
        leaderBuilder.encoderVelocity().fwdLimit().addStatus().build();
    coralSystemFollower = new LoggableSystem<>(io, followerInputs);
    coralSystemLeader = new LoggableSystem<>(io, leaderInputs);
  }

  @Override
  public void periodic() {
    coralSystemLeader.updateInputs();
    coralSystemFollower.updateInputs();
    SmartShuffleboard.put("coral", "ForwardTripped?", coralSystemLeader.getInputs().fwdLimit());
  }

  public void setShooterSpeed(double speed) {
    coralSystemLeader.getIO().setShooterSpeed(speed);
    coralSystemFollower.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    coralSystemLeader.getIO().stopShooterMotors();
    coralSystemFollower.getIO().stopShooterMotors();
  }

  public void breakModeCoast(boolean breakMode) {
    coralSystemLeader.getIO().breakModeCoast(breakMode);
    coralSystemFollower.getIO().breakModeCoast(breakMode);
  }

  public void enableOrDisableLimitSwitch(boolean state) {
    coralSystemLeader.getIO().enableOrDisableLimitSwitch(state);
    coralSystemFollower.getIO().enableOrDisableLimitSwitch(state);
  }

  public boolean getForwardSwitchState() {
    return coralSystemLeader.getInputs().fwdLimit();
  }
}
