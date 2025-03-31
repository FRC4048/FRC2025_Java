package frc.robot.commands.lightStrip;

import static frc.robot.constants.ElevatorPosition.LEVEL1;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.Supplier;

public class SetLedFromElevatorPosition extends LoggableCommand {

  private final Supplier<ElevatorPosition> position;
  private final LightStrip lightStrip;

  public SetLedFromElevatorPosition(
      Supplier<ElevatorPosition> positionSupplier, LightStrip lightStrip) {
    this.position = positionSupplier;
    this.lightStrip = lightStrip;
    addRequirements(lightStrip);
  }

  @Override
  public void execute() {
    switch (position.get()) {
      case LEVEL1:
        lightStrip.setPattern(BlinkinPattern.WHITE);
        break;
      case LEVEL2:
        lightStrip.setPattern(BlinkinPattern.BLUE);
        break;
      case LEVEL3:
        lightStrip.setPattern(BlinkinPattern.HOT_PINK);
        break;
      case LEVEL4:
        lightStrip.setPattern(BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
        break;
      case CLIMB:
        break;
      default:
        DriverStation.reportWarning("Invalid Reef Position selected", true);
        break;
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
