package frc.robot.subsystems.swervev3.io.steer;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.ProfiledPIDController;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SimSteerMotorInputProvider;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

public class SimSteerMotorIO implements SwerveSteerMotorIO {
  private final SimulatedMotorController.GenericMotorController steerMotor;
  private final ProfiledPIDController turningController;
  private final SimSteerMotorInputProvider steerInputProvider;

  public SimSteerMotorIO(
      SwerveModuleSimulation moduleSimulation, ProfiledPIDController turningController) {
    steerMotor =
        moduleSimulation
            .useGenericControllerForSteer()
            .withCurrentLimit(Amps.of(Constants.DRIVE_SMART_LIMIT));
    this.turningController = turningController;
    steerInputProvider = new SimSteerMotorInputProvider(moduleSimulation);
    resetEncoder();
  }

  public void updateInputs(MotorInputs inputs) {
    inputs.process(steerInputProvider);
  }

  public void setSteerVoltage(double volts) {
    steerMotor.requestVoltage(Volts.of(volts));
  }

  public void resetEncoder() {
    turningController.reset(0);
  }
}
