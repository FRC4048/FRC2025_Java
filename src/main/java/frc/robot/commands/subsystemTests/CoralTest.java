package frc.robot.commands.subsystemTests;
import frc.robot.subsystems.CoralShooter.CoralShooterSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class CoralTest extends LoggableCommand {
    private final CoralShooterSubsystem coral;
    private final double speedMotors;
    
    public CoralTest(CoralShooterSubsystem coral, double speedMotors) {
        this.speedMotors = speedMotors;
        this.coral = coral;
        addRequirements(coral);
    }
    
    @Override
    public void initialize() {
        coral.setShooterSpeed(speedMotors);
    }
    
    @Override
    public void execute() {}
    
    @Override
    public void end(boolean interrupted) {
        coral.stopShooterMotors();
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
    

}
