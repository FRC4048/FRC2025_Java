package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;
import frc.robot.utils.motor.SparkUtil;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import java.util.Arrays;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimDriveMotorIO implements SimSwerveDriveMotorIO {
  private final SimulatedMotorController.GenericMotorController driveMotor;
  private final DCMotor driveGearbox = DCMotor.getNEO(1);
  private final SwerveModuleSimulation moduleSimulation;
  private final PIDController drivePIDController;
  private double driveVelConvFactor;
  private double drivePosConvFactor;
  private double driveAppliedVolts = 0;
  private boolean driveClosedLoop = false;
  private double driveFFVolts;
  private int driveInverted;
  private String moduleName;

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

    driveMotor.requestVoltage(Volts.of(driveAppliedVolts));
    inputs.setDriveConnected(true);
    inputs.setEncoderPosition(
        moduleSimulation.getDriveWheelFinalPosition().in(Radians) * drivePosConvFactor);
    inputs.setEncoderVelocity(
        moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond) * driveVelConvFactor);
    inputs.setAppliedOutput(driveAppliedVolts);
    inputs.setMotorCurrent(Math.abs(moduleSimulation.getDriveMotorStatorCurrent().in(Amps)));
    inputs.setOdometryTimestamps(SparkUtil.getSimulationOdometryTimeStamps());
    inputs.setOdometryDrivePositionsRad(
        Arrays.stream(moduleSimulation.getCachedDriveWheelFinalPositions())
            .mapToDouble(angle -> angle.in(Radians))
            .toArray());
  }

  public SimulatedMotorController.GenericMotorController getDriveMotor() {
    return driveMotor;
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    driveVelConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio() * 60);
    drivePosConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio());
  }

  public void setDriveVoltage(double volts) {
    driveMotor.requestVoltage(Volts.of(volts));
    SmartShuffleboard.put("Commands", "Drive Voltage" + moduleName, volts);
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
    SmartShuffleboard.put("Commands", "Drive Velocity", trueVelocityRadPerSec);
  }
}
