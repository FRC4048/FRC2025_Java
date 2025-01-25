package frc.robot.subsystems.AlgaeRemover;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeRemoverIO extends LoggableIO<AlgaeRemoverInputs>{
    void setRemoverSpeed(double speed);
    
    void setTiltMotorSpeed(double tiltSpeed);

    void stopRemoverMotors();

    void stopTiltMotors();

    void resetTiltEncoder();

}
