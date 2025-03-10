package frc.robot.utils.diag;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.utils.RobotMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The subsystem that handles diagnostics. This subsystem will hold the list of Diagnosables and
 * make sure they go through their lifecycle: refresh and reset. Use addDiagnosable() to add new
 * components to the list - NOTE: Make sure they are only added to the list ONCE in the Robot's
 * lifetime (e.g. in the subsystem constructor).
 */
public class Diagnostics extends SubsystemBase {

  public static final String SHUFFLEBOARD_TAB_NAME = "Diagnostics";
  public static final int width = 1, height = 4;

  private final ShuffleboardTab shuffleBoardTab;

  private final List<Diagnosable> diagnosables;

  public Diagnostics() {
    shuffleBoardTab = Shuffleboard.getTab(SHUFFLEBOARD_TAB_NAME);
    diagnosables = new ArrayList<>();

    // This simulates adding components by other subsystems...
  }

  public void addDiagnosable(Diagnosable diagnosable) {
    diagnosable.setShuffleBoardTab(shuffleBoardTab, width, height);
    diagnosables.add(diagnosable);
  }

  /**
   * Refresh the display with current values. This would go through the range of components and test
   * their state, and then refresh the shuffleboard display This method should be called
   * periodically (e.g. in test mode) to ensure the components are all tested
   */
  public void refresh() {
    if (Robot.getMode() == RobotMode.TEST) diagnosables.forEach(Diagnosable::refresh);
  }

  /**
   * Reset the diagnostics to the initial values. This would have the effect of getting all the
   * diagnosable items to their initial state (as if they were not tested yet) This can be done when
   * disabling the robot, so enable->disable would allow for a retest
   */
  public void reset() {
    diagnosables.forEach(Diagnosable::reset);
  }
}
