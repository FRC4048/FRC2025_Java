package frc.robot.subsystems.limelight;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.diag.DiagLimelight;
import java.util.Map;

public class RealVisionIO implements VisionIO {
  private final NetworkTableEntry tv;
  private final NetworkTableEntry tx;
  private final NetworkTableEntry ty;
  private final SwerveDrivetrain drivetrain;

  public RealVisionIO(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    tv = table.getEntry("tv");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    Robot.getDiagnostics().addDiagnosable(new DiagLimelight("Vision", "Piece Seen"));

    HttpCamera limelightFeed =
        new HttpCamera(
            "limelight", "http://" + Constants.LIMELIGHT_IP_ADDRESS + ":5800/stream.mjpg");
    ShuffleboardTab dashboardTab = Shuffleboard.getTab("Driver");
    dashboardTab
        .add("Limelight feed", limelightFeed)
        .withSize(6, 4)
        .withPosition(2, 0)
        .withProperties(Map.of("Show Crosshair", false, "Show Controls", false));
  }

  @Override
  public void updateInputs(VisionInputs inputs) {
    inputs.tv = tv.getDouble(0);
    inputs.tx = tx.getDouble(0);
    inputs.ty = ty.getDouble(0);
    if (inputs.tv != 0) {
      BranchPositions[] newCoralArray = new BranchPositions[inputs.coralSeen.length + 1];
      AlgaePositions[] newAlgaeArray = new AlgaePositions[inputs.algaeSeen.length + 1];
      int oldCoralArrayLength = inputs.coralSeen.length;
      int oldAlgaeArrayLength = inputs.algaeSeen.length;
      for (int i = 0; i < oldCoralArrayLength; i++) {
        newCoralArray[i] = inputs.coralSeen[i];
      }
      newCoralArray[oldCoralArrayLength+1] =
          GamePieceLocate.findCoralBranch(
              drivetrain.getPose(), VecBuilder.fill(tx.getDouble(0), ty.getDouble(0)));

      for (int i = 0; i < oldAlgaeArrayLength; i++) {
        newAlgaeArray[i] = inputs.algaeSeen[i];
      }
      newAlgaeArray[oldAlgaeArrayLength+1] =
          GamePieceLocate.findAlgaePos(
              drivetrain.getPose(), VecBuilder.fill(tx.getDouble(0), ty.getDouble(0)));

      inputs.coralSeen = newCoralArray;
      inputs.algaeSeen = newAlgaeArray;
    }
  }
}
