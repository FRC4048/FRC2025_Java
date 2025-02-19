package frc.robot.commands.sequences;

import frc.robot.commands.hihi.RetractHiHi;
import frc.robot.commands.hihi.ShootHiHiRollerOut;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;

public class ShootAlgae extends LoggableSequentialCommandGroup{
    public ShootAlgae(HihiExtenderSubsystem hihiExtenderSubsystem, HihiRollerSubsystem hihiRollerSubsystem) {
        super(
            new ShootHiHiRollerOut(hihiRollerSubsystem),
            new RetractHiHi(hihiExtenderSubsystem)
        );
    }
}
