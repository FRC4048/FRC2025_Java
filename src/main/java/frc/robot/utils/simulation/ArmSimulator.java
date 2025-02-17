// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.utils.simulation;

import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.sim.SparkRelativeEncoderSim;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A class to encapsulate the behavior of a simulated single jointed arm. Wraps around the motor and
 * the physical model to simulate the behavior. Send information to ShuffleBoard with physics
 * information. Does not interfere with production behavior.
 */
public class ArmSimulator {
  // Gearbox represents a gearbox (1:1 conversion rate) with 1 motor connected
  private final DCMotor gearbox = DCMotor.getNEO(1);
  // The motor (that sits underneath the motor simulator)
  // In case of follower/leader, this should be the leader
  private final SparkMax motor;
  // The simulated motor controller wrapping the actual motor
  private final SparkMaxSim motorSim;
  // The encoder simulator from the simulated motor
  private final SparkRelativeEncoderSim encoderSim;
  // The forward switch simulator
  private final SparkLimitSwitchSim forwardSwitchSim;
  // The reverse switch simulator
  private final SparkLimitSwitchSim reverseSwitchSim;
  // Elevator physical model, simulating movement based on physics, motor load and gravity
  private final SingleJointedArmSim armSim;
  // The mechanism simulation used for visualization
  private final Mechanism2d mech2d = new Mechanism2d(3, 5);
  private final MechanismRoot2d mech2dRoot = mech2d.getRoot("Arm Root", 1, 1);
  private final MechanismLigament2d armMech2d =
      mech2dRoot.append(new MechanismLigament2d("Arm", 1, -90));
  private final double armGearing;
  private final String name;

  /** Constructor. */
  public ArmSimulator(SparkMax motor, ArmParameters params) {
    armSim =
        new SingleJointedArmSim(
            gearbox,
            params.armGearing,
            params.armInertia,
            params.armLength,
            params.armMinAngle.getRadians(),
            params.armMaxAngle.getRadians(),
            params.armSimulateGravity,
            0);
    this.motor = motor;
    armGearing = params.armGearing;
    this.name = params.name;
    motorSim = new SparkMaxSim(motor, gearbox);
    encoderSim = motorSim.getRelativeEncoderSim();
    forwardSwitchSim = motorSim.getForwardLimitSwitchSim();
    reverseSwitchSim = motorSim.getReverseLimitSwitchSim();
    encoderSim.setPositionConversionFactor(1.0);
    encoderSim.setPosition(0.0);
    encoderSim.setInverted(false);
    // Publish Mechanism2d to SmartDashboard
    // To view the Elevator visualization, select Network Tables -> SmartDashboard -> Elevator Sim
    SmartDashboard.putData(name + " Arm Sim", mech2d);
  }

  /** Advance the simulation. */
  public void simulationPeriodic() {
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    double motorOut = motorSim.getAppliedOutput() * 12.0; // * RoboRioSim.getVInVoltage();
    armSim.setInput(motorOut);
    // Next, we update it. The standard loop time is 20ms.
    armSim.update(0.020);
    // Finally, we set our simulated encoder's readings and simulated battery voltage
    Rotation2d velocityRadsPerSecond = Rotation2d.fromRadians(armSim.getVelocityRadPerSec());
    double rpm = velocityRadsPerSecond.getRotations() * 60;
    motorSim.iterate(
        rpm, 12, // RoboRioSim.getVInVoltage(),
        0.020);
    // SimBattery estimates loaded battery voltages
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(armSim.getCurrentDrawAmps()));
    // Update elevator visualization with position
    Rotation2d positionRadians = Rotation2d.fromRadians(armSim.getAngleRads());
    armMech2d.setAngle(positionRadians);
    forwardSwitchSim.setPressed(armSim.hasHitUpperLimit());
    reverseSwitchSim.setPressed(armSim.hasHitLowerLimit());
    SmartDashboard.putNumber(name + " Arm Motor out voltage", motorOut);
    SmartDashboard.putNumber(name + " Arm Velocity rads/s", velocityRadsPerSecond.getRadians());
    SmartDashboard.putNumber(name + " Arm RPM", rpm);
    SmartDashboard.putNumber(name + " Arm actual position", armSim.getAngleRads());
    SmartDashboard.putNumber(name + " Arm Mechanism angle", armMech2d.getAngle());
    SmartDashboard.putBoolean(name + " Arm Forward switch", forwardSwitchSim.getPressed());
    SmartDashboard.putBoolean(name + " Arm Reverse switch", reverseSwitchSim.getPressed());
    SmartDashboard.putNumber(name + " Arm Encoder", encoderSim.getPosition());
  }

  /**
   * Convert {@link Angle} into RPM
   *
   * @param angle Rotation angle, in Radians.
   * @return {@link Angle} equivalent to rotations of the motor.
   */
  private Rotation2d convertAngleToRotations(Rotation2d angle) {
    return angle.times(armGearing);
  }

  public void close() {
    motor.close();
    mech2d.close();
  }
}
