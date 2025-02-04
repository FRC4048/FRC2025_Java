package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class TestCommand extends Command {

    @Override
    public void initialize() {
        System.out.println("Initialized");
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("End");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
