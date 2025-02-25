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
    if (supplier.getAsDouble() > 0) { // Phase2
      climber.setClimberSpeed(Constants.CLIMBER_PHASE2_SPEED);
    } else if (climber.getEncoderPosition() < Constants.CLIMBER_PHASE1_POSITION) { // phase2
      climber.setClimberSpeed(Constants.CLIMBER_PHASE2_SPEED);
    } else {
      climber.setClimberSpeed(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
