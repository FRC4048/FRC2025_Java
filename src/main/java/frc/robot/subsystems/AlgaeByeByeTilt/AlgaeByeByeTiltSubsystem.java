// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeByeByeTilt;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeByeByeTiltSubsystem extends SubsystemBase {
    /**
     * Creates a new AlgaeByeByeTiltSubsystem.
     */
    private final LoggableSystem<AlgaeByeByeTiltIO, AlgaeByeByeTiltInputs> algaeTiltSystem;

    public AlgaeByeByeTiltSubsystem(AlgaeByeByeTiltIO io) {
        algaeTiltSystem = new LoggableSystem<>(io, new AlgaeByeByeTiltInputs());
    }

    @Override
    public void periodic() {
        algaeTiltSystem.updateInputs();
    }

    public void setSpeed(double speed) {
        algaeTiltSystem.getIO().setSpeed(speed);
    }

    public void stopMotors() {
        algaeTiltSystem.getIO().stopMotors();
    }

    public void resetEncoder() {
        algaeTiltSystem.getIO().resetEncoder();
    }

    public double getAngle() {
        return algaeTiltSystem.getInputs().tiltMotorEncoderPosition;
    }

    public boolean getForwardSwitchState() {
        return algaeTiltSystem.getInputs().forwardLimitSwitchState;
    }

    public boolean getReverseSwitchState() {
        return algaeTiltSystem.getInputs().backLimitSwitchState;
  }
}
