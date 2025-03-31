package frc.robot.commands.sequences;

import frc.robot.commands.climber.DeployClimber;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.commands.lightStrip.SetLedPattern;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class DeployHarpoon extends LoggableSequentialCommandGroup {
  public DeployHarpoon(
      ClimberSubsystem climber,
      ElevatorSubsystem elevator,
      LightStrip lightstrip,
      ElevatorPosition safeElevatorPosition, LightStrip lightStrip) {
    super(
        new SetElevatorStoredPosition(safeElevatorPosition, elevator, lightstrip),
        new ElevatorToStoredPosition(elevator),
        new WaitTillElevatorAtPosition(elevator, safeElevatorPosition.getElevatorHeight()),
        new DeployClimber(climber),
        new SetLedPattern(lightStrip, BlinkinPattern.ORANGE));
  }
}
