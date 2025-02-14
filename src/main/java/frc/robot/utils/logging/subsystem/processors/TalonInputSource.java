package frc.robot.utils.logging.subsystem.processors;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.function.Function;

/** Talon Input Builder implementation */
public class TalonInputSource implements MotorInputSource<WPI_TalonSRX> {

  @Override
  public Function<WPI_TalonSRX, Double> currentFromSource() {
    return BaseTalon::getStatorCurrent;
  }

  @Override
  public Function<WPI_TalonSRX, Double> motorTemperatureFromSource() {
    return BaseMotorController::getTemperature;
  }

  @Override
  public Function<WPI_TalonSRX, Double> encoderPositionFromSource() {
    return BaseMotorController::getSelectedSensorPosition;
  }

  @Override
  public Function<WPI_TalonSRX, Double> encoderVelocityFromSource() {
    return BaseMotorController::getSelectedSensorVelocity;
  }

  @Override
  public Function<WPI_TalonSRX, Boolean> fwdLimitFromSource() {
    return talon -> talon.isFwdLimitSwitchClosed() == 1;
  }

  @Override
  public Function<WPI_TalonSRX, Boolean> revLimitFromSource() {
    return talon -> talon.isRevLimitSwitchClosed() == 1;
  }
}
