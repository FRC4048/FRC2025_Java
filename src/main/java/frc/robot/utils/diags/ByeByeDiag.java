package frc.robot.utils.diags;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;

public class ByeByeDiag implements Diagnosable {
  private String title;
  private String nameRev;
  private String nameFwd;
  private boolean retracted;
  private boolean extended;
  private GenericEntry networkTableEntryRev;
  private GenericEntry networkTableEntryFwd;
  private final AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem;

  public ByeByeDiag(
      String title,
      String nameRev,
      String nameFwd,
      AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem) {
    this.title = title;
    this.nameRev = nameRev;
    this.nameFwd = nameFwd;
    this.algaeByeByeTiltSubsystem = algaeByeByeTiltSubsystem;
    reset();
  }

  @Override
  public void reset() {
    retracted = true;
    extended = false;
  }

  @Override
  public void refresh() {
    if (networkTableEntryRev != null) {
      retracted = getDiagRevResult();
      networkTableEntryRev.setBoolean(retracted);
    }
    if (networkTableEntryFwd != null) {
      extended = getDiagFwdResult();
      networkTableEntryFwd.setBoolean(extended);
    }
  }

  boolean getDiagRevResult() {
    return algaeByeByeTiltSubsystem.getReverseSwitchState();
  }

  boolean getDiagFwdResult() {
    return algaeByeByeTiltSubsystem.getForwardSwitchState();
  }

  @Override
  public void setShuffleBoardTab(ShuffleboardTab shuffleboardTab, int width, int height) {
    networkTableEntryRev =
        shuffleboardTab
            .getLayout(title, BuiltInLayouts.kList)
            .withSize(width, height)
            .add(nameRev, true)
            .getEntry();
    networkTableEntryFwd =
        shuffleboardTab
            .getLayout(title, BuiltInLayouts.kList)
            .withSize(width, height)
            .add(nameFwd, false)
            .getEntry();
  }
}
