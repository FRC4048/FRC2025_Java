// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.TestCommand;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class RobotContainer {
  public RobotContainer() {
    // Shuffleboard.getTab("New Tab Test").add("Testing Number1", 1).withWidget(BuiltInWidgets.kNumberSlider); // wpilib API create tab
    // SmartDashboard.putNumber("Testing Number", 75); // wpilib API smartdashboard
    // SmartDashboard.putData(new TestCommand()); // wpilib API smartdashboard
    SmartShuffleboard.put("Testing number", "One", 100);
    configureBindings();
  }

  private void configureBindings() {
    SmartShuffleboard.putCommand("TestCommandTab", "TestCommand", new TestCommand());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}