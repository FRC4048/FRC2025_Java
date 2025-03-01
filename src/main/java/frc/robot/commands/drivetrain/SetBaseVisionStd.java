package frc.robot.commands.drivetrain;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetBaseVisionStd extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final Vector<N3> baseVisionStd;

  public SetBaseVisionStd(SwerveDrivetrain drivetrain, Vector<N3> baseVisionStd) {
    this.drivetrain = drivetrain;
    this.baseVisionStd = baseVisionStd;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.setVisionBaseSTD(baseVisionStd);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
