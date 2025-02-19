package frc.robot.commands.sequences;

import frc.robot.commands.hihi.RetractHiHi;
import frc.robot.commands.hihi.RollHiHiRollerIn;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;

public class IntakeAlgae extends LoggableParallelCommandGroup{
    public IntakeAlgae(HihiExtenderSubsystem hihiExtenderSubsystem, HihiRollerSubsystem hihiRollerSubsystem) {
        super(
            new RetractHiHi(hihiExtenderSubsystem),
            new RollHiHiRollerIn(hihiRollerSubsystem)
        );
    }
}
