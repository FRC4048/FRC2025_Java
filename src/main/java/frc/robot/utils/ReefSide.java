package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

public enum ReefSide {
    AB(new Pose2d(3.15, 4.00, Rotation2d.fromDegrees(0))),
    CD(new Pose2d(3.85, 5.15, Rotation2d.fromDegrees(300))),
    EF(new Pose2d(5.15, 5.15, Rotation2d.fromDegrees(240))),
    GH(new Pose2d(5.80, 4.00, Rotation2d.fromDegrees(180))),
    IJ(new Pose2d(5.15, 2.90, Rotation2d.fromDegrees(120))),
    KL(new Pose2d(3.85, 2.90, Rotation2d.fromDegrees(60)));

    private final Pose2d robotCenter;
    private static final double branchOffset = 0.4;

    ReefSide(Pose2d robotCenter) {
        this.robotCenter = robotCenter;
    }

    public Pose2d getRobotCenter() {
        return robotCenter;
    }
    public Pose2d getBranch1Pose(){
        double modX = branchOffset * robotCenter.getRotation().getSin();
        double modY = branchOffset * robotCenter.getRotation().getSin();
        Transform2d transform2d = new Transform2d(modX,modY,Rotation2d.kZero);
        return robotCenter.plus(transform2d);
    }
    public Pose2d getBranch2Pose(){
        double modX = -branchOffset * robotCenter.getRotation().getSin();
        double modY = -branchOffset * robotCenter.getRotation().getSin();
        Transform2d transform2d = new Transform2d(modX,modY,Rotation2d.kZero);
        return robotCenter.plus(transform2d);
    }
}
