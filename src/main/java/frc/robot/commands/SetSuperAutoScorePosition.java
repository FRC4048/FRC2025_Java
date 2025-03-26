package frc.robot.commands;

import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.limelight.Vision;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.Arrays;
import java.util.Comparator;

public class SetSuperAutoScorePosition extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final ElevatorSubsystem elevatorSubsystem;
  private final Vision vision;

  public SetSuperAutoScorePosition(
      SwerveDrivetrain drivetrain, ElevatorSubsystem elevatorSubsystem, Vision vision) {
    this.drivetrain = drivetrain;
    this.elevatorSubsystem = elevatorSubsystem;
    this.vision = vision;
  }

  @Override
  public void execute() {
    AlignmentPosition closest = AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    BranchPositions[] relevantPoses =
        Arrays.stream(vision.getAllBranchPosition())
            .filter(b -> b.getTrunk() == closest)
            .sorted(Comparator.comparingInt(v -> v.getElevatorLevel().getWeight()))
            .toArray(BranchPositions[]::new);
    if (relevantPoses.length == 0
        || relevantPoses[0].getElevatorLevel() != ElevatorPosition.LEVEL4) {
      elevatorSubsystem.setElevatorPosition(ElevatorPosition.LEVEL4.getElevatorHeight());
    } else if (relevantPoses.length == 1
        || relevantPoses[1].getElevatorLevel() != ElevatorPosition.LEVEL3) {
      elevatorSubsystem.setElevatorPosition(ElevatorPosition.LEVEL3.getElevatorHeight());
    } else if (relevantPoses.length == 2
        || relevantPoses[2].getElevatorLevel() != ElevatorPosition.LEVEL2) {
      elevatorSubsystem.setElevatorPosition(ElevatorPosition.LEVEL3.getElevatorHeight());
    } else if (relevantPoses.length == 3
        || relevantPoses[3].getElevatorLevel() != ElevatorPosition.LEVEL1) {
      elevatorSubsystem.setElevatorPosition(ElevatorPosition.LEVEL3.getElevatorHeight());
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
