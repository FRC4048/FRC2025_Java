package frc.robot.subsystem.coral;
import frc.util.LoggableIO;

public interface CoralIO extends LoggableIO<CoralInputs> {
    void setShooterSpeed(double speed);
    void setTiltAngularVelocity(double angleSpeed);
    void stopShooterMotors();
    void stopTiltMotors();
    void resetTiltEncoder();
}