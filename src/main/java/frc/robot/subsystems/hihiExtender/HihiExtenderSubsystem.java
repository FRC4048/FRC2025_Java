// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiExtender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class HihiExtenderSubsystem extends SubsystemBase {
    private final LoggableSystem<HihiExtenderIO, HihiExtenderInputs> system;

    /**
     * Creates a new Extender.
     */
    public HihiExtenderSubsystem(HihiExtenderIO io) {
        system = new LoggableSystem<>(io, new HihiExtenderInputs());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        system.updateInputs();
    }

    public void setExtenderSpeed(double speed) {
        system.getIO().setHihiExtenderSpeed(speed);
    }

    public void stopExtenderMotors() {
        system.getIO().stopHihiExtenderMotor();
    }

    public boolean getForwardSwitchState() {
        return system.getInputs().fwdTripped;
    }

    public boolean getReverseSwitchState() {
        return system.getInputs().revTripped;
    }

    public void resetEncoder() {
        system.getIO().resetExtenderEncoder();
    }
}
