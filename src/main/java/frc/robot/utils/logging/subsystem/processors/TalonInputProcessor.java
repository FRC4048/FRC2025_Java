package frc.robot.utils.logging.subsystem.processors;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;

/** Talon Input Builder implementation */
public class TalonInputProcessor implements MotorInputProcessor<WPI_TalonSRX> {

  @Override
  public InputSource<Double, WPI_TalonSRX> currentFromSource() {
    return BaseTalon::getStatorCurrent;
  }

  @Override
  public InputSource<Double, WPI_TalonSRX> motorTemperatureFromSource() {
    return BaseMotorController::getTemperature;
  }

  @Override
  public InputSource<Double, WPI_TalonSRX> encoderPositionFromSource() {
    return BaseMotorController::getSelectedSensorPosition;
  }

  @Override
  public InputSource<Double, WPI_TalonSRX> encoderVelocityFromSource() {
    return BaseMotorController::getSelectedSensorVelocity;
  }

  @Override
  public InputSource<Boolean, WPI_TalonSRX> fwdLimitFromSource() {
    return talon -> talon.isFwdLimitSwitchClosed() == 1;
  }

  @Override
  public InputSource<Boolean, WPI_TalonSRX> revLimitFromSource() {
    return talon -> talon.isRevLimitSwitchClosed() == 1;
  }
}
