package frc.robot.commands.lightStrip;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.BooleanSupplier;

public class SetLedOnCoralIntake extends LoggableCommand {
  private final LightStrip lightStrip;
  private final BlinkinPattern pattern = BlinkinPattern.GREEN;
  private final double seconds = 1;
  private final Timer timer;
  private final BooleanSupplier limitSwitchState;

  public SetLedOnCoralIntake(BooleanSupplier limitSwitchState, LightStrip lightStrip) {
    this.lightStrip = lightStrip;
    this.limitSwitchState = limitSwitchState;
    this.timer = new Timer();
    addRequirements(lightStrip);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    if (limitSwitchState.getAsBoolean()) {
      lightStrip.setPattern(pattern);
    }
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.INTAKE_LED_STRIP_TIME) || !limitSwitchState.getAsBoolean();
  }
}
