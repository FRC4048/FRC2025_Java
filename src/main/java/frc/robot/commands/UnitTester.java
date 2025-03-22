package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class UnitTester extends Command {
  @Override
  public void execute() {
    SmartShuffleboard.put("Branch", "Branch", FindCorrectBranchFromPos.UnitTest1().toString());
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
