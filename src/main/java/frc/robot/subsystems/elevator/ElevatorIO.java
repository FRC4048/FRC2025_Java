package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.LoggableIO;

public interface ElevatorIO extends LoggableIO<ElevatorInputs> {
  void setSpeed(double spd);
}
