package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetRumble extends LoggableCommand {
  private final CommandXboxController controller;
  private final boolean rumble;

  public SetRumble(CommandXboxController controller, boolean rumble) {
    this.controller = controller;
    this.rumble = rumble;
  }

  @Override
  public void initialize() {
    controller.setRumble(GenericHID.RumbleType.kBothRumble, rumble ? 1 : 0);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
