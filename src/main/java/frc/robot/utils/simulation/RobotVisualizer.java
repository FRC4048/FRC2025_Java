package frc.robot.utils.simulation;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.constants.Constants;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

public class RobotVisualizer {
  private final LoggedMechanism2d mech2d = new LoggedMechanism2d(2, Units.feetToMeters(7));
  private final LoggedMechanismLigament2d elevatorLigament;
  private final LoggedMechanismLigament2d algaeByeByeTiltLigament;
  private final LoggedMechanismLigament2d algaeByeByeRollerLigament;
  private final LoggedMechanismLigament2d algaeHiHiTiltLigament;
  private final LoggedMechanismLigament2d algaeHiHiRollerLigament;

  private static final RobotVisualizer instance = new RobotVisualizer();

  public static RobotVisualizer getInstance() {
    return instance;
  }

  public RobotVisualizer() {
    LoggedMechanismRoot2d elevatorRoot =
        mech2d.getRoot(
            "Elevator Root", Constants.DRIVE_BASE_WIDTH / 2, Constants.INITIAL_ELEVATOR_HEIGHT);
    LoggedMechanismRoot2d algaeHiHiRoot =
        mech2d.getRoot("Algae HiHi Root", Constants.DRIVE_BASE_WIDTH - 0.05, 0.1);
    this.elevatorLigament =
        elevatorRoot.append(
            new LoggedMechanismLigament2d(
                "Elevator",
                Constants.MIN_ELEVATOR_HEIGHT_METERS,
                90,
                4,
                new Color8Bit(Color.kDarkGray)));
    this.algaeByeByeTiltLigament =
        this.elevatorLigament.append(
            new LoggedMechanismLigament2d(
                "AlgaeByeByeTilt",
                Constants.BYEBYE_TILT_LENGTH,
                90,
                4,
                new Color8Bit(Color.kLightBlue)));
    this.algaeByeByeRollerLigament =
        this.algaeByeByeTiltLigament.append(
            new LoggedMechanismLigament2d(
                "AlgaeByeByeRoller", 0.05, 180, 5, new Color8Bit(Color.kGreen)));
    this.algaeHiHiTiltLigament =
        algaeHiHiRoot.append(
            new LoggedMechanismLigament2d(
                "AlgaeHiHiTilt", 0.2, -90, 4, new Color8Bit(Color.kBlue)));
    this.algaeHiHiRollerLigament =
        algaeHiHiTiltLigament.append(
            new LoggedMechanismLigament2d(
                "AlgaeHiHiRoller", 0.05, 0, 5, new Color8Bit(Color.kGreen)));
  }

  public LoggedMechanismLigament2d getElevatorLigament() {
    return elevatorLigament;
  }

  public LoggedMechanismLigament2d getAlgaeByeByeTiltLigament() {
    return algaeByeByeTiltLigament;
  }

  public LoggedMechanismLigament2d getAlgaeByeByeRollerLigament() {
    return algaeByeByeRollerLigament;
  }

  public LoggedMechanismLigament2d getAlgaeHiHiTiltLigament() {
    return algaeHiHiTiltLigament;
  }

  public LoggedMechanismLigament2d getAlgaeHiHiRollerLigament() {
    return algaeHiHiRollerLigament;
  }

  public void updateElevator(double length) {
    elevatorLigament.setLength(length);
  }

  /**
   * @param degrees ranging from 0 to 360
   */
  public void setAlgaeByeByeTiltAngle(double degrees) {
    algaeByeByeTiltLigament.setAngle(degrees);
  }

  public void spinAlgaeByeByeRollerAngle() {
    algaeByeByeRollerLigament.setAngle(algaeByeByeRollerLigament.getAngle() + 5);
  }

  public void spinAlgaeHiHiRollerAngle() {
    algaeHiHiRollerLigament.setAngle(algaeHiHiRollerLigament.getAngle() + 5);
  }

  public void setAlgaeHiHiTiltAngle(double degrees) {
    algaeHiHiTiltLigament.setAngle(degrees);
  }

  public void logMechanism() {
    Logger.recordOutput("Mechanism2d/", mech2d);
  }

  public void close() {
    mech2d.close();
  }
}
