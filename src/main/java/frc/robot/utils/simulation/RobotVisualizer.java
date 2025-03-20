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
  //  private final LoggedMechanismLigament2d algaeHiHiTiltLigament;
  //  private final LoggedMechanismLigament2d algaeHiHiRollerLigament;
  private final LoggedMechanismLigament2d coralRollerLigament;

  private static final RobotVisualizer instance = new RobotVisualizer();

  public static RobotVisualizer getInstance() {
    return instance;
  }

  public RobotVisualizer() {
    LoggedMechanismRoot2d elevatorRoot =
        mech2d.getRoot(
            "Elevator Root", Constants.DRIVE_BASE_WIDTH / 2, Constants.INITIAL_ELEVATOR_HEIGHT);
    //    LoggedMechanismRoot2d algaeHiHiRoot =
    //        mech2d.getRoot("Algae HiHi Root", Constants.DRIVE_BASE_WIDTH - 0.05, 0.1);
    this.elevatorLigament =
        elevatorRoot.append(
            new LoggedMechanismLigament2d(
                "Elevator",
                Constants.MIN_ELEVATOR_HEIGHT_METERS,
                90,
                4,
                new Color8Bit(Color.kDarkGray)));
    LoggedMechanismLigament2d byeByeRiser =
        elevatorLigament.append(
            new LoggedMechanismLigament2d(
                "CoralRiser", 0.15, 0, 5, new Color8Bit(Color.kDarkGray)));
    this.algaeByeByeTiltLigament =
        byeByeRiser.append(
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
    //    this.algaeHiHiTiltLigament =
    //        algaeHiHiRoot.append(
    //            new LoggedMechanismLigament2d(
    //                "AlgaeHiHiTilt", 0.2, -90, 4, new Color8Bit(Color.kBlue)));
    //    this.algaeHiHiRollerLigament =
    //        algaeHiHiTiltLigament.append(
    //            new LoggedMechanismLigament2d(
    //                "AlgaeHiHiRoller", 0.05, 0, 5, new Color8Bit(Color.kGreen)));
    LoggedMechanismLigament2d coralRiser =
        byeByeRiser.append(
            new LoggedMechanismLigament2d(
                "CoralRiser", 0.15, 0, 5, new Color8Bit(Color.kDarkGray)));
    LoggedMechanismLigament2d coralExtension =
        coralRiser.append(
            new LoggedMechanismLigament2d(
                "CoralExtension", 0.05, -80, 5, new Color8Bit(Color.kDarkGray)));
    this.coralRollerLigament =
        coralExtension.append(
            new LoggedMechanismLigament2d(
                "CoralRoller", 0.05, 180, 5, new Color8Bit(Color.kGreen)));
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

  //  public LoggedMechanismLigament2d getAlgaeHiHiTiltLigament() {
  //    return algaeHiHiTiltLigament;
  //  }

  //  public LoggedMechanismLigament2d getAlgaeHiHiRollerLigament() {
  //    return algaeHiHiRollerLigament;
  //  }

  public LoggedMechanismLigament2d getCoralRollerLigament() {
    return coralRollerLigament;
  }

  public void logMechanism() {
    Logger.recordOutput("Mechanism2d/", mech2d);
  }

  public void close() {
    mech2d.close();
  }
}
