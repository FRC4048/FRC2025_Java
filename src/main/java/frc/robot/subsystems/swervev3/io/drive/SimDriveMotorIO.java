package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SimDriveMotorInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimDriveMotorIO implements SwerveDriveMotorIO {
  private final SimulatedMotorController.GenericMotorController driveMotor;
  private final SimDriveMotorInputProvider driveInputProvider;
  private final PIDController drivePIDController;

  public SimDriveMotorIO(
      KinematicsConversionConfig conversionConfig,
      SwerveModuleSimulation moduleSimulation,
      PIDController drivePIDController) {
    driveMotor =
        moduleSimulation
            .useGenericMotorControllerForDrive()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));

    driveInputProvider = new SimDriveMotorInputProvider(moduleSimulation, conversionConfig);
    this.drivePIDController = drivePIDController;
  }

  public void updateInputs(MotorInputs inputs) {
    inputs.process(driveInputProvider);
  }

  public void setDriveVoltage(double volts) {
    driveMotor.requestVoltage(Volts.of(volts));
  }

  public void resetEncoder() {
    drivePIDController.reset();
  }
}
