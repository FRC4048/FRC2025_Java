package frc.robot.subsystems.swervev3.io.steer;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import frc.robot.utils.logging.subsystem.providers.SteerModuleSimInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimSteerMotorIO implements SimSwerveSteerMotorIO {
  private final SimulatedMotorController.GenericMotorController steerMotor;
  private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
  private double steerPosConvFactor;
  private final SwerveModuleSimulation moduleSimulation;
  private final ProfiledPIDController turningController;
  private double turnAppliedVolts = 0;
  private boolean steerClosedLoop = false;
  private double steerFFVolts;
  private final double steerInvertedFactor;
  private final SteerModuleSimInputProvider steerInputProvider;

  public SimSteerMotorIO(
      int steerMotorId,
      KinematicsConversionConfig conversionConfig,
      boolean steerInverted,
      SwerveModuleSimulation moduleSimulation,
      ProfiledPIDController turningController,
      LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem,) {
    steerMotor =
        moduleSimulation
            .useGenericControllerForSteer()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));
    this.moduleSimulation = moduleSimulation;
    this.steerInvertedFactor = steerInverted ? -1 : 1;
    this.turningController = turningController;
    this.absSystem = absSystem;
    steerInputProvider = new SteerModuleSimInputProvider(moduleSimulation, conversionConfig);
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
    inputs.process(steerInputProvider);
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    steerPosConvFactor = 1 / conversionConfig.getProfile().getSteerGearRatio();
    SmartDashboard.putNumber("steerPosConvFactor", steerPosConvFactor);
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
