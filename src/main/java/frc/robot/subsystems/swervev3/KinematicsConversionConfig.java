package frc.robot.subsystems.swervev3;

import frc.robot.utils.SwerveModuleProfileV2;

public class KinematicsConversionConfig {
    private final double wheelRadius;
    private final SwerveModuleProfileV2 profile;

    public KinematicsConversionConfig(double wheelRadius, SwerveModuleProfileV2 profile) {
        this.wheelRadius = wheelRadius;
        this.profile = profile;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public SwerveModuleProfileV2 getProfile() {
        return profile;
    }
}