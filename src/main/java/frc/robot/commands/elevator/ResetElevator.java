package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.LoggableCommand;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ResetElevator extends LoggableCommand {
    private final ElevatorSubsystem elevator;
    private double startTime;

    public ResetElevator(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        elevator.setElevatorMotorSpeed(-Constants.ELEVATOR_RISE_SPEED); // assuming positive is forward
    }

    @Override
    public void end(boolean interrupted) {
        // elevator.stopMotor();
        // elevator.resetEncoder();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // if (elevator.getReversedSwitchState()) {
        //   return true;
        // }
        // else if ((Timer.getFPGATimestamp() - startTime) >= Constants.ELEVATOR_TIMEOUT) {
        //   return true;
        // }
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
