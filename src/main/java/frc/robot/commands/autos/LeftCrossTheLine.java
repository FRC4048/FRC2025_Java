package frc.robot.commands.autos;

import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftCrossTheLine extends LoggableSequentialCommandGroup {
  public LeftCrossTheLine() {
    super(
        new LoggableSequentialCommandGroup(
          new LoggableParallelCommandGroup(
            Paths.getInstance().get
          )
        )

  }
}
