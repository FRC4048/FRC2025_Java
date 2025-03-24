// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.sim.SparkRelativeEncoderSim;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.robot.constants.Constants;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

/**
 * A class to encapsulate the behavior of a simulated elevator. Wraps around the motor and the
 * physical model to simulate the behavior. Send information to ShuffleBoard with physics
 * information. Does not interfere with production behavior.
 */
public class ElevatorSimulator {
  // Gearbox represents a gearbox (1:1 conversion rate) with 2 or motors connected
  private final DCMotor elevatorGearbox = DCMotor.getNEO(2);
  // The motor (that sits underneath the motor simulator)
  // In case of follower/leader, this should be the leader
  private final SparkMax motor;

  // The simulated motor controller wrapping the actual motor
  private final SparkMaxSim motorSim;
  private final LoggedMechanismLigament2d elevatorLigament;
  // Whether the elevator is "inverted" - going down onn positives motor
  private final boolean inverted;
  // The encoder simulator from the simulated motor
  private final SparkRelativeEncoderSim encoderSim;
  // The forward switch simulator
  private final SparkLimitSwitchSim forwardSwitchSim;
  // The reverse switch simulator
  private final SparkLimitSwitchSim reverseSwitchSim;

  // Elevator physical model, simulating movement based on physics, motor load and gravity
  private final ElevatorSim m_elevatorSim =
      new ElevatorSim(
          elevatorGearbox,
          Constants.ELEVATOR_GEARING,
          Constants.CARRIAGE_MASS,
          Constants.ELEVATOR_DRUM_RADIUS,
          Constants.MIN_ELEVATOR_HEIGHT_METERS,
          Constants.MAX_ELEVATOR_HEIGHT_METERS,
          true,
          0);

  /** Constructor. */
  public ElevatorSimulator(SparkMax motor, LoggedMechanismLigament2d elevatorLigament) {
    this(motor, elevatorLigament, false);
  }

  public ElevatorSimulator(
      SparkMax motor, LoggedMechanismLigament2d elevatorLigament, boolean inverted) {
    this.motor = motor;
    motorSim = new SparkMaxSim(motor, elevatorGearbox);
    this.elevatorLigament = elevatorLigament;
    this.inverted = inverted;
    encoderSim = motorSim.getRelativeEncoderSim();
    forwardSwitchSim = motorSim.getForwardLimitSwitchSim();
    reverseSwitchSim = motorSim.getReverseLimitSwitchSim();
  }

  /** Advance the simulation. */
  public void stepSimulation() {
    final double direction = inverted ? -1.0 : 1.0;
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    double motorOut =
        direction * motorSim.getAppliedOutput() * 12.0; // * RoboRioSim.getVInVoltage();
    m_elevatorSim.setInput(motorOut);
    // Next, we update it. The standard loop time is 20ms.
    m_elevatorSim.update(0.020);

    // Finally, we set our simulated encoder's readings and simulated battery voltage
    double velocityMetersPerSecond = m_elevatorSim.getVelocityMetersPerSecond();
    double rpm = convertDistanceToRotations(Meters.of(velocityMetersPerSecond)).per(Second).in(RPM);
    motorSim.iterate(direction * rpm, 12, 0.020);
    // SimBattery estimates loaded battery voltages
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(m_elevatorSim.getCurrentDrawAmps()));

    // Update elevator visualization with position
    double positionMeters = m_elevatorSim.getPositionMeters();

    if (inverted) {
      forwardSwitchSim.setPressed(
          MathUtil.isNear(Constants.MIN_ELEVATOR_HEIGHT_METERS, positionMeters, 0.1));
      reverseSwitchSim.setPressed(
          MathUtil.isNear(Constants.MAX_ELEVATOR_HEIGHT_METERS, positionMeters, 0.1));
    } else {
      forwardSwitchSim.setPressed(
          MathUtil.isNear(Constants.MAX_ELEVATOR_HEIGHT_METERS, positionMeters, 0.1));
      reverseSwitchSim.setPressed(
          MathUtil.isNear(Constants.MIN_ELEVATOR_HEIGHT_METERS, positionMeters, 0.1));
    }

    if (Constants.ELEVATOR_DEBUG) {
      Logger.recordOutput("ElevatorSubsystem/MotorCommandedVoltage", motorOut);
      Logger.recordOutput("ElevatorSubsystem/VelocityMPS", velocityMetersPerSecond);
      Logger.recordOutput(
          "ElevatorSubsystem/ElevatorActualPosition", m_elevatorSim.getPositionMeters());
      if (elevatorLigament != null) {
        Logger.recordOutput(
            "ElevatorSubsystem/ElevatorMechanismLength", elevatorLigament.getLength());
      }
    }

    if (elevatorLigament != null) {
      elevatorLigament.setLength(positionMeters);
    }
  }

  /**
   * Convert {@link Distance} into {@link Angle}
   *
   * @param distance Distance, usually Meters.
   * @return {@link Angle} equivalent to rotations of the motor.
   */
  private Angle convertDistanceToRotations(Distance distance) {
    return Rotations.of(
        distance.in(Meters)
            / (Constants.ELEVATOR_DRUM_RADIUS * 2 * Math.PI)
            * Constants.ELEVATOR_GEARING);
  }

  public void close() {
    // m_encoder.close();
    motor.close();
  }
}
