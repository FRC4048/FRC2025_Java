package frc.robot.utils.logging.commands;

import edu.wpi.first.math.geometry.Pose2d;

public class DoNothingCommand extends LoggableCommand {
  
  @Override
  public boolean isFinished() {
    return true;

  @Override
  public Pose2d getStartingPosition() {
    return provider.getSelectedLocation().getLocation();
  }
  }
}
