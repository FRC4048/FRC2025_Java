package frc.robot.commands;

import frc.robot.subsystems.limelight.RealVisionIO;
import frc.robot.subsystems.limelight.VisionIO;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ChangeLimelightLight extends LoggableCommand {
    private final VisionIO io;
    private boolean on;
    public ChangeLimelightLight(boolean on, VisionIO io) {
        this.io = io;
    }

    @Override
    public void initialize() {
        io.setLedMode(on ? 3 : 1);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
