package frc.robot.subsystems.AlgaeByeByeTilt;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeTiltIO extends LoggableIO<AlgaeByeByeTiltInputs> {
    void setSpeed(double speed);

    void stopMotors();

    void resetEncoder();
}
