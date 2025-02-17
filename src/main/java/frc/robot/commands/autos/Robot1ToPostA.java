package frc.robot.commands.autos;

import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class Robot1ToPostA extends LoggableSequentialCommandGroup {
  public Robot1ToPostA() {
    super(
        LoggableCommandWrapper.wrap(Paths.getInstance().getRobot1ToPostICommand()),
        LoggableCommandWrapper.wrap(Paths.getInstance().getPoseItoStation1Command()),
        LoggableCommandWrapper.wrap(Paths.getInstance().getStation1ToPoseACommand()));
  }
}
