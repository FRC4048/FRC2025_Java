package frc.robot.utils;

public enum ReefLevel {
    LEVEL1(30),
    LEVEL2(30),
    LEVEL3(30),
    LEVEL4(30);
    private final double shooterAngleInDeg;

    ReefLevel(double shooterAngleInDeg) {
        this.shooterAngleInDeg = shooterAngleInDeg;
    }

    public double getShooterAngleInDeg() {
        return shooterAngleInDeg;
    }
}
