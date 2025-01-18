// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.CoralSubsystem;
public class ShootCoral extends Command {
  private CoralSubsystem shooter;

  private double startTime;
  private double speedMotors;

  public ShootCoral(CoralSubsystem shooter, double Speedmotors){
  public ShootCoral(CoralSubsystem shooter, double Speedmotors) {

    this.speedMotors = Speedmotors;
    this.shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterSpeed(speedMotors);
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooterMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return (Timer.getFPGATimestamp() - startTime >= 10);
  }
}*/
