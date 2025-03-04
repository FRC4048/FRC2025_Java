package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.DriveModuleSimInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimDriveMotorIO implements SwerveDriveMotorIO {
  private final SimulatedMotorController.GenericMotorController driveMotor;
  private final DCMotor driveGearbox = DCMotor.getNEO(1);
  private final SwerveModuleSimulation moduleSimulation;
  private final PIDController drivePIDController;
  private double driveAppliedVolts = 0;
  private boolean driveClosedLoop = false;
  private double driveFFVolts;
  private final int driveInverted;
  private final String moduleName;
  private final DriveModuleSimInputProvider driveInputProvider;

  public SimDriveMotorIO(
      int driveMotorIO,
      KinematicsConversionConfig conversionConfig,
      boolean driveInverted,
      SwerveModuleSimulation moduleSimulation,
      PIDController drivePIDController,
      String moduleName) {
    driveMotor =
        moduleSimulation
            .useGenericMotorControllerForDrive()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));

    driveInputProvider = new DriveModuleSimInputProvider(moduleSimulation, conversionConfig);
    this.moduleSimulation = moduleSimulation;
    this.drivePIDController = drivePIDController;
    this.moduleName = moduleName;

    this.driveInverted = driveInverted ? -1 : 1;
  }

  public void updateInputs(MotorInputs inputs) {
    if (driveClosedLoop) {
      driveAppliedVolts =
          driveFFVolts
              + drivePIDController.calculate(
                  moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
    } else {
      drivePIDController.reset();
    }
    inputs.process(driveInputProvider);
  }

  public SimulatedMotorController.GenericMotorController getDriveMotor() {
    return driveMotor;
  }

  public void setDriveVoltage(double volts) {
    driveMotor.requestVoltage(Volts.of(volts));
  }

  public void resetEncoder() {
    drivePIDController.reset();
  }

  public void setDriveOpenLoop(double output) {
    driveClosedLoop = false;
    driveAppliedVolts = output;
  }

  public void setDriveVelocity(double velocityRadPerSec) {
    double trueVelocityRadPerSec = velocityRadPerSec * driveInverted;
    driveClosedLoop = true;
    driveFFVolts =
        Constants.DRIVE_PID_FF_S * Math.signum(trueVelocityRadPerSec)
            + Constants.DRIVE_PID_FF_V * trueVelocityRadPerSec;
    drivePIDController.setSetpoint(trueVelocityRadPerSec);
  }
}
