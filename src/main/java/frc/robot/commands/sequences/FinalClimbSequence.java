package frc.robot.commands.sequences;

import frc.robot.commands.climber.SetClimberSpeed;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class FinalClimbSequence extends LoggableSequentialCommandGroup {
  public FinalClimbSequence(ClimberSubsystem climber) {
    super(new SetClimberSpeed(climber, Constants.CLIMBER_PHASE2_SPEED));
  }
}
