package frc.robot.utils.simulation;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;

public class SwerveSimulationUtils {
  private static final DCMotor driveMotor = DCMotor.getNEO(1);
  private static final DCMotor steerMotor = DCMotor.getNEO(1);

  public static DriveTrainSimulationConfig simulationConfig() {
    return DriveTrainSimulationConfig.Default()
        .withCustomModuleTranslations(SwerveDrivetrain.getKinematics().getModules())
        .withRobotMass(Kilograms.of(Constants.ROBOT_MASS))
        .withBumperSize(
            Meters.of(Constants.ROBOT_BUMPER_LENGTH), Meters.of(Constants.ROBOT_BUMPER_WIDTH))
        .withGyro(COTS.ofNav2X())
        .withSwerveModule(
            new SwerveModuleSimulationConfig(
                driveMotor,
                steerMotor,
                Constants.SWERVE_MODULE_PROFILE.getDriveGearRatio(),
                Constants.SWERVE_MODULE_PROFILE.getSteerGearRatio(),
                Volts.of(Constants.DRIVE_PID_FF_S),
                Volts.of(Constants.STEER_FRICTION_VOLTAGE),
                Meters.of(Constants.WHEEL_RADIUS),
                KilogramSquareMeters.of(Constants.STEER_ROTATIONAL_INERTIA),
                Constants.COEFFICIENT_OF_FRICTION));
  }
}
