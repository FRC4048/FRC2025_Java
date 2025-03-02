package frc.robot.commands.autos;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LightlessPickup extends LoggableSequentialCommandGroup {
  public LightlessPickup(ElevatorSubsystem elevator, CoralSubsystem coral) {
    super(
        new ResetElevator(elevator),
        new WaitTillElevatorAtPosition(elevator, ElevatorPosition.CORAL_INTAKE.getElevatorHeight()),
        new IntakeCoral(coral));
  }
}
