// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.logging;

import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class TimeoutLogger {
  private int timeoutCounter = 0; // per command
  private static int totalTimeouts = 0; // every timeout
  private final String commandName;

  public TimeoutLogger(String commandName) {
    this.commandName = commandName;
    Logger.recordOutput("Timeouts/" + commandName, timeoutCounter);
  }

  public double getTimeoutCount() {
    return timeoutCounter;
  }

  public void increaseTimeoutCount() {
    timeoutCounter++;
    totalTimeouts++;
    Logger.recordOutput("Timeouts/" + commandName, timeoutCounter);
  }

  public String getCommandName() {
    return commandName;
  }

  public void resetCounter() {
    timeoutCounter = 0;
    Logger.recordOutput("Timeouts/" + commandName, timeoutCounter);
  }

  public static int getTotalTimeouts() {
    return totalTimeouts;
  }
}
