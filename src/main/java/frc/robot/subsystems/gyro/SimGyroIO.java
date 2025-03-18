package frc.robot.subsystems.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import org.ironmaple.simulation.drivesims.GyroSimulation;

public class SimGyroIO implements GyroIO {
    private final GyroSimulation gyro;

    public SimGyroIO(GyroSimulation gyro) {
        this.gyro = gyro;
    }

    @Override
    public void setAngleOffset(double offset) {
        gyro.setRotation(new Rotation2d(offset));
    }

    @Override
    public void resetGyro() {
        gyro.setRotation(new Rotation2d(0));
    }
    @Override
    public void updateInputs(GyroInputs inputs) {
        inputs.anglesInDeg = (gyro.getGyroReading().getDegrees() % 360)* -1;
    }
}
