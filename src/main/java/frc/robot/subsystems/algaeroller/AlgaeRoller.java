package frc.robot.subsystems.algaeroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.subsystems.LoggableSystem;

public class AlgaeRoller extends SubsystemBase {
    private final LoggableSystem<AlgaeRollerIO, AlgaeRollerInputs> algaeRollerSystem;

    public AlgaeRoller(AlgaeRollerIO algaeRollerIO) {
        this.algaeRollerSystem = new LoggableSystem<>(algaeRollerIO, new AlgaeRollerInputs());
    }

    @Override
    public void setRollerMotorSpeed(double speed) {
        algaeRollerSystem.getIO().setSpeed(speed);
    }

    @Override
    public double getRollerMotorPosition() {
        return algaeRollerSystem.getInputs().algaeRollerEncoder;
    }

    @Override
    public void stopAlgaeRollerMotor() {
        algaeRollerSystem.getIO().stop();
    }

    @Override
    public void periodic() {
        algaeRollerSystem.updateInputs();
    }
}