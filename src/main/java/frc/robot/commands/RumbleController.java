// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

import java.util.function.DoubleSupplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RumbleController extends LoggableCommand {

  private final DoubleSupplier X;
  private final DoubleSupplier Y;
  private final SwerveDrivetrain swerve;
  private final XboxController controller;

  public RumbleController(SwerveDrivetrain swerve, DoubleSupplier X, DoubleSupplier Y, XboxController controller) {
    this.Y = Y;
    this.X = X;
    this.swerve = swerve;
    this.controller = controller;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(swerve.isInBarge(X.getAsDouble(),Y.getAsDouble())){
controller.setRumble(RumbleType.kBothRumble, 2);
    };
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
