package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.io.steer.SparkMaxSteerMotorIO;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

import static edu.wpi.first.units.Units.*;

public class SimSteerMotorIO extends SparkMaxSteerMotorIO {


    public SimSteerMotorIO(int steerMotorIO, KinematicsConversionConfig conversionConfig, boolean steerInverted) {
        super(steerMotorIO, conversionConfig, steerInverted);
    }
    public void updateInputs(SteerMotorInputs inputs, SwerveModuleSimulation moduleSimulation, double steerAppliedVolts){
        inputs.setSteerConnected(true);
        inputs.setEncoderPosition(moduleSimulation.getSteerAbsoluteFacing().getRadians());
        inputs.setEncoderVelocity(moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond));
        inputs.setAppliedOutput(steerAppliedVolts);
        inputs.setMotorCurrent(Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps)));
        super.updateInputs(inputs);
    }
}
