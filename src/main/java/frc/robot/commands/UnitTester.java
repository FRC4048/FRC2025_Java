package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class UnitTester extends Command {
  @Override
  public void execute() {
    FindCorrectBranchFromPos.UnitTest1();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
