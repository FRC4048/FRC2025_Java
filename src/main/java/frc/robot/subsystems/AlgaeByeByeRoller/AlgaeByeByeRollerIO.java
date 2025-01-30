package frc.robot.subsystems.AlgaeByeByeRoller;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeRollerIO extends LoggableIO<AlgaeByeByeRollerInputs> {
    void setSpeed(double speed);

    void stopMotors();
}
