package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;
import frc.robot.utils.logging.subsystem.providers.DriveModuleSimInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimDriveMotorIO implements SimSwerveDriveMotorIO {
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
    setConversionFactors(conversionConfig);
  }

  public void updateInputs(DriveMotorInputs inputs) {
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

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double driveVelConvFactor =
        (conversionConfig.getWheelRadius())
            / (conversionConfig.getProfile().getDriveGearRatio() * 60);
    SmartDashboard.putNumber(moduleName + " driveVelConvFactor", driveVelConvFactor);
    double drivePosConvFactor =
        (conversionConfig.getWheelRadius())
            / (conversionConfig.getProfile().getDriveGearRatio());
    SmartDashboard.putNumber(moduleName + " drivePosConvFactor", drivePosConvFactor);
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
