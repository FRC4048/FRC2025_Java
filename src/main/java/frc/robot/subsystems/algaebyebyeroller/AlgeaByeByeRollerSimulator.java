package frc.robot.subsystems.algaebyebyeroller;

import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.system.plant.DCMotor;

public class AlgeaByeByeRollerSimulator {
  private static final double RPM_PER_VOLT = 100;
  // Gearbox represents a gearbox (1:1 conversion rate) with 1 or motors connected
  private final DCMotor gearbox = DCMotor.getNEO(1);
  private final WPI_TalonSRX motor;
  // The simulated motor controller wrapping the actual motor
  private final TalonSRXSimCollection motorSim;

  // The encoder simulator from the simulated motor
  public AlgeaByeByeRollerSimulator(WPI_TalonSRX motor) {
    this.motor = motor;
    motorSim = new TalonSRXSimCollection(motor); // no gearbox
    motorSim.setAnalogPosition(0);
  }

  /** Advance the simulation. */
  public void simulationPeriodic() {
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    double motorOut = motorSim.getMotorOutputLeadVoltage() * 12.0; // * RoboRioSim.getVInVoltage();
    // Finally, we set our simulated encoder's readings and simulated battery voltage
    // We use a very simplistic formula to calculate the no-load motor speed
    double rpm = motorOut * RPM_PER_VOLT;
    // IS THERE NO ITERATION??
  }

  public void close() {
    motor.close();
  }
}
