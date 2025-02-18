package frc.robot.subsystems.swervev3;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;

public record SwervePidConfig(
    PID drivePid,
    PID steerPid,
    Gain driveGain,
    Gain steerGain,
    TrapezoidProfile.Constraints goalConstraint) {}
