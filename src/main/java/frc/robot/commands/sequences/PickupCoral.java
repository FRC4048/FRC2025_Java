package frc.robot.commands.sequences;

import frc.robot.commands.byebye.ByeByeToRevLimit;
import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.elevator.SetElevatorTargetPosition;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class PickupCoral extends LoggableSequentialCommandGroup{
    public PickupCoral(AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem, CoralSubsystem coralSubsystem, ElevatorSubsystem elevatorSubsystem) {
        super(
            new ByeByeToRevLimit(algaeByeByeTiltSubsystem),
            new SetElevatorTargetPosition(null, elevatorSubsystem),
            new IntakeCoral(coralSubsystem)
        );
    }
}
