package frc.robot.subsystems.swervev3;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.apriltags.ApriltagInputs;
import frc.robot.constants.Constants;
// import frc.robot.subsystems.gyro.GyroIO;
// import frc.robot.subsystems.gyro.GyroInputs;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.utils.DriveMode;
import frc.robot.utils.SwerveModuleProfile;
import frc.robot.utils.advanced.Alignable;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import org.littletonrobotics.junction.Logger;

public class SwerveDrivetrain extends SubsystemBase {
    public static final SwerveModuleProfile SWERVE_MODULE_PROFILE = SwerveModuleProfile.MK4;
    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final Translation2d frontLeftLocation =
            new Translation2d(Constants.ROBOT_LENGTH / 2, Constants.ROBOT_WIDTH / 2);
    private final Translation2d frontRightLocation =
            new Translation2d(Constants.ROBOT_LENGTH / 2, -Constants.ROBOT_WIDTH / 2);
    private final Translation2d backLeftLocation =
            new Translation2d(-Constants.ROBOT_LENGTH / 2, Constants.ROBOT_WIDTH / 2);
    private final Translation2d backRightLocation =
            new Translation2d(-Constants.ROBOT_LENGTH / 2, -Constants.ROBOT_WIDTH / 2);
    private final SwerveDriveKinematics kinematics =
            new SwerveDriveKinematics(
                    frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);
    // private final LoggableSystem<GyroIO, GyroInputs> gyroSystem;
    private DriveMode driveMode = DriveMode.FIELD_CENTRIC;
    // private final PoseEstimator poseEstimator;
//    private final PIDController alignableTurnPid =
//            new PIDController(
//                    Constants.ALIGNABLE_PID_P, Constants.ALIGNABLE_PID_I, Constants.ALIGNABLE_PID_D);
    // private Alignable alignable = null;
    private boolean facingTarget = false;

    public SwerveDrivetrain(
            SwerveModule frontLeftModule,
            SwerveModule frontRightModule,
            SwerveModule backLeftModule,
            SwerveModule backRightModule
            // GyroIO gyroIO,
            /* LoggableIO<ApriltagInputs> apriltagIO*/) {
        this.frontLeft = frontLeftModule;
        this.frontRight = frontRightModule;
        this.backLeft = backLeftModule;
        this.backRight = backRightModule;
        // this.gyroSystem = new LoggableSystem<>(gyroIO, new GyroInputs());
        // this.poseEstimator =
        //     new PoseEstimator(
        //         frontLeft, frontRight, backLeft, backRight, apriltagIO, kinematics, getLastGyro());
        // alignableTurnPid.enableContinuousInput(-180, 180);
    }

    @Override
    public void periodic() {
        // poseEstimator.updateInputs();
        processInputs();
        // OdometryMeasurement odom =
        //     new OdometryMeasurement(
        //         new SwerveModulePosition[] {
        //           frontLeft.getPosition(),
        //           frontRight.getPosition(),
        //           backLeft.getPosition(),
        //           backRight.getPosition()
        //         },
        //         getLastGyro());
        // Logger.recordOutput("LastOdomModPoses", odom.modulePosition());
        // poseEstimator.updatePosition(odom);
        // poseEstimator.updateVision();
        Logger.recordOutput(
                "realSwerveStates",
                frontLeft.getLatestState(),
                frontRight.getLatestState(),
                backLeft.getLatestState(),
                backRight.getLatestState());
        if (Constants.SWERVE_DEBUG) {
            SmartShuffleboard.put("Drive", "FL ABS Pos", frontLeft.getAbsPosition());
            SmartShuffleboard.put("Drive", "FR ABS Pos", frontRight.getAbsPosition());
            SmartShuffleboard.put("Drive", "BL ABS Pos", backLeft.getAbsPosition());
            SmartShuffleboard.put("Drive", "BR ABS Pos", backRight.getAbsPosition());
        }
    }

    private void processInputs() {
        frontLeft.updateInputs();
        frontRight.updateInputs();
        backLeft.updateInputs();
        backRight.updateInputs();
        // gyroSystem.updateInputs();
    }

    public ChassisSpeeds createChassisSpeeds(
            double xSpeed, double ySpeed, double rotation, DriveMode driveMode) {
        return driveMode.equals(DriveMode.FIELD_CENTRIC)
                ? ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeed,
                ySpeed,
                rotation,
                Rotation2d.fromDegrees(0) /*Rotation2d.fromDegrees(getLastGyro())*/)
                : new ChassisSpeeds(xSpeed, ySpeed, rotation);
    }

    public void drive(ChassisSpeeds speeds) {
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.MAX_VELOCITY);
        setModuleStates(swerveModuleStates);
    }

    public ChassisSpeeds speedsFromStates() {
        return kinematics.toChassisSpeeds(
                frontLeft.getLatestState(),
                frontRight.getLatestState(),
                backLeft.getLatestState(),
                backRight.getLatestState());
    }

    private void setModuleStates(SwerveModuleState[] desiredStates) {
        Logger.recordOutput("desiredStates", desiredStates);
        frontLeft.setState(desiredStates[0]);
        frontRight.setState(desiredStates[1]);
        backLeft.setState(desiredStates[2]);
        backRight.setState(desiredStates[3]);
    }

    public void stopMotor() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }

    public void zeroRelativeEncoders() {
        frontLeft.resetRelativeEnc();
        frontRight.resetRelativeEnc();
        backLeft.resetRelativeEnc();
        backRight.resetRelativeEnc();
    }

    public void setSteerOffset(
            double absEncoderZeroFL,
            double absEncoderZeroFR,
            double absEncoderZeroBL,
            double absEncoderZeroBR) {
        frontLeft.setSteerOffset(absEncoderZeroFL);
        frontRight.setSteerOffset(absEncoderZeroFR);
        backLeft.setSteerOffset(absEncoderZeroBL);
        backRight.setSteerOffset(absEncoderZeroBR);
    }

    // public void resetGyro() {
    //   gyroSystem.getIO().resetGyro();
    // }

    // public double getLastGyro() {
    //   return gyroSystem.getInputs().anglesInDeg;
    // }

    public void setDriveMode(DriveMode driveMode) {
        this.driveMode = driveMode;
    }

    public DriveMode getDriveMode() {
        return driveMode;
    }

    // public Pose2d getPose() {
    //   return poseEstimator.getEstimatedPose();
    // }

    // public void setGyroOffset(double offset) {
    //   gyroSystem.getIO().setAngleOffset(offset);
    // }

    // public void resetOdometry(Pose2d startingPosition) {
    //   poseEstimator.resetOdometry(
    //       startingPosition.getRotation().getRadians(), startingPosition.getTranslation());
    // }

    // public Rotation2d getGyroAngle() {
    //   return Rotation2d.fromDegrees(getLastGyro());
    // }

    public ChassisSpeeds getChassisSpeeds() {
        return kinematics.toChassisSpeeds(
                frontLeft.getLatestState(),
                frontRight.getLatestState(),
                backLeft.getLatestState(),
                backRight.getLatestState());
    }

    // public ChassisSpeeds getFieldChassisSpeeds() {
    //   return ChassisSpeeds.fromRobotRelativeSpeeds(getChassisSpeeds(), getPose().getRotation());
    // }

//    public Alignable getAlignable() {
//        return alignable;
//    }

//    public PIDController getAlignableTurnPid() {
//        return alignableTurnPid;
//    }

    public void setFacingTarget(boolean facingTarget) {
        this.facingTarget = facingTarget;
    }

    public boolean isFacingTarget() {
        return facingTarget;
    }

//    public void setAlignable(Alignable alignable) {
//        this.alignable = alignable;
//    }
}