package frc.robot.commands.climber;

import edu.wpi.first.math.MathUtil;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class Climb extends LoggableCommand {
  private final ClimberSubsystem climber;
  private DoubleSupplier supplier;

  public Climb(ClimberSubsystem climber, DoubleSupplier supplier) {
    this.climber = climber;
    this.supplier = supplier;
    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    double value = MathUtil.applyDeadband(supplier.getAsDouble(), Constants.CLIMBER_DEADBAND);

    if (value > 0 && climber.getEncoderPosition() < Constants.CLIMBER_PHASE1_POSITION) {
      // phase1 - deploying harpoon
      climber.setClimberSpeed(Constants.CLIMBER_PHASE1_SPEED);
    } else if (value < 0 && climber.getEncoderPosition() >= Constants.CLIMBER_PHASE1_POSITION) {
      // Phase2 - lifting robot
      climber.setClimberSpeed(Constants.CLIMBER_PHASE2_SPEED);
    } else {
      climber.stopClimber();
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
