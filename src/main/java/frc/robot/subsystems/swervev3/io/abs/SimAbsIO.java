package frc.robot.subsystems.swervev3.io.abs;

import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimAbsIO implements SwerveAbsIO {
  private final SwerveModuleSimulation moduleSimulation;

  public SimAbsIO(SwerveModuleSimulation moduleSimulation) {
    this.moduleSimulation = moduleSimulation;
  }

  @Override
  public void updateInputs(SwerveAbsInput input) {
    input.absEncoderPosition = moduleSimulation.getSteerAbsoluteFacing().getRadians();
  }
}
