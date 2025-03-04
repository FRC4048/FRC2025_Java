package frc.robot.subsystems.swervev3.io.steer;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import frc.robot.utils.motor.SparkUtil;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimSteerMotorIO implements SimSwerveSteerMotorIO {
  private final SimulatedMotorController.GenericMotorController steerMotor;
  private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
  private final DCMotor steerGearbox = DCMotor.getNEO(1);
  private double steerPosConvFactor;
  private final SwerveModuleSimulation moduleSimulation;
  private final ProfiledPIDController turningController;
  private double turnAppliedVolts = 0;
  private boolean steerClosedLoop = false;
  private double steerFFVolts;
  private boolean steerInverted;
  private double steerInvertedFactor;
  private String moduleName;

  public SimSteerMotorIO(
      int steerMotorId,
      KinematicsConversionConfig conversionConfig,
      boolean steerInverted,
      SwerveModuleSimulation moduleSimulation,
      ProfiledPIDController turningController,
      LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem,
      String moduleName) {
    steerMotor =
        moduleSimulation
            .useGenericControllerForSteer()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));
    this.moduleName = moduleName;
    this.moduleSimulation = moduleSimulation;
    this.steerInverted = steerInverted;
    this.steerInvertedFactor = steerInverted ? -1 : 1;
    this.turningController = turningController;
    this.absSystem = absSystem;
    setConversionFactors(conversionConfig);
    resetEncoder();
  }

  public void updateInputs(SteerMotorInputs inputs) {
    if (steerClosedLoop) {
      turnAppliedVolts =
          steerFFVolts
              + turningController.calculate(
                  moduleSimulation.getSteerRelativeEncoderVelocity().in(RadiansPerSecond));
    } else {
      turningController.reset(absSystem.getInputs().absEncoderPosition); // TODO: might be wrong
    }
    steerMotor.requestVoltage(Volts.of(turnAppliedVolts));
    inputs.setSteerConnected(true);
    inputs.setEncoderPosition(
        moduleSimulation.getSteerAbsoluteFacing().getRadians() * steerPosConvFactor);
    inputs.setEncoderVelocity(
        moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond)
            * steerPosConvFactor
            / 60);
    inputs.setAppliedOutput(turnAppliedVolts);
    inputs.setMotorCurrent(Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps)));
    inputs.setOdometryTimestamps(SparkUtil.getSimulationOdometryTimeStamps());
    inputs.setOdometryTurnPositions(moduleSimulation.getCachedSteerAbsolutePositions());
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    steerPosConvFactor = 2 * Math.PI / conversionConfig.getProfile().getSteerGearRatio();
  }

  public void setSteerVoltage(double volts) {
    steerMotor.requestVoltage(Volts.of(volts));
  }

  public void resetEncoder() {
    turningController.reset(0);
  }

  public void setSteerOpenLoop(double output) {
    steerClosedLoop = false;
    turnAppliedVolts = output;
  }

  public void setTurnPosition(Rotation2d rotation) {
    steerClosedLoop = true;
    turningController.setGoal(rotation.getRadians());
  }

  public void setSteerVelocity(double velocityRadPerSec) {
    double trueVelocityRadPerSec = velocityRadPerSec * steerInvertedFactor;
    steerClosedLoop = true;
    steerFFVolts =
        Constants.DRIVE_PID_FF_S * Math.signum(trueVelocityRadPerSec)
            + Constants.DRIVE_PID_FF_V * trueVelocityRadPerSec;
    turningController.setGoal(trueVelocityRadPerSec);
  }
}
