package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.system.plant.DCMotor;

public class AlgaeByeByeRollerSimulator {
  private static final double RPM_PER_VOLT = 100;
  // Gearbox represents a gearbox (1:1 conversion rate) with 1 or motors connected
  private final DCMotor gearbox = DCMotor.getNEO(1);
  private final SparkMax motor;
  // The simulated motor controller wrapping the actual motor
  private final SparkMaxSim motorSim;

  // The encoder simulator from the simulated motor
  public AlgaeByeByeRollerSimulator(SparkMax motor) {
    this.motor = motor;
    motorSim = new SparkMaxSim(motor, gearbox);
    motorSim.setPosition(0);
  }

  /** Advance the simulation. */
  public void simulationPeriodic() {
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    double motorOut = motorSim.getAppliedOutput() * 12.0; // * RoboRioSim.getVInVoltage();
    // Finally, we set our simulated encoder's readings and simulated battery voltage
    // We use a very simplistic formula to calculate the no-load motor speed
    double rpm = motorOut * RPM_PER_VOLT;
    motorSim.iterate(rpm, 12, 0.020);
  }

  public void close() {
    motor.close();
  }
}
