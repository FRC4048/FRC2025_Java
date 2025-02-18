package frc.robot.utils.diags;

import edu.wpi.first.networktables.GenericEntry;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.utils.diags.Diagnosable;

public class ByeByeDiag implements Diagnosable{
    private String title;
    private String name;
    private GenericEntry networkTableEntry;
    private final AlgaeByeByeRollerSubsystem algaeByeByeRollerSubsystem;
    private final AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem;
    public ByeByeDiag(String title, String name, AlgaeByeByeRollerSubsystem algaeByeByeRollerSubsystem, AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem) {
        this.title = title;
        this.name = name;
        this.algaeByeByeRollerSubsystem = algaeByeByeRollerSubsystem;
        this.algaeByeByeTiltSubsystem = algaeByeByeTiltSubsystem;
        reset();
    }
}   
