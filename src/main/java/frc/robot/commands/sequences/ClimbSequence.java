package frc.robot.commands.sequences;

import frc.robot.commands.climber.Climb;
import frc.robot.commands.climber.EngageRatchet;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import java.util.function.DoubleSupplier;

public class ClimbSequence extends LoggableSequentialCommandGroup {

  public ClimbSequence(ClimberSubsystem climber, DoubleSupplier supplier) {
    super(new EngageRatchet(climber), new Climb(climber, supplier));
  }
}
