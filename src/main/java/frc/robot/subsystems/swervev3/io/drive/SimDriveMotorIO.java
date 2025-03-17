package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SimDriveMotorInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimDriveMotorIO implements SwerveDriveMotorIO {
  private final SimulatedMotorController.GenericMotorController driveMotor;
  private final SimDriveMotorInputProvider inputProvider;

  public SimDriveMotorIO(
      KinematicsConversionConfig conversionConfig, SwerveModuleSimulation moduleSimulation) {
    driveMotor =
        moduleSimulation
            .useGenericMotorControllerForDrive()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));

    inputProvider = new SimDriveMotorInputProvider(moduleSimulation, conversionConfig);
  }

  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }

  public void setDriveVoltage(double volts) {
    driveMotor.requestVoltage(Volts.of(volts));
  }

  public void resetEncoder() {
    // TODO: not implemented yet
  }
}
