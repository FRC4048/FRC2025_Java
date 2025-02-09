package frc.robot.utils.logging.subsystem.builders;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.InputSource;

/** SparkMax Input Builder implementation */
public class TalonSRXInputBuilder extends MotorInputBuilder<WPI_TalonSRX> {

  public TalonSRXInputBuilder(String folder) {
    super(folder);
  }

  @Override
  protected InputSource<Double, WPI_TalonSRX> currentFromSource() {
    return BaseTalon::getStatorCurrent;
  }

  @Override
  protected InputSource<Double, WPI_TalonSRX> motorTemperatureFromSource() {
    return BaseMotorController::getTemperature;
  }

  @Override
  protected InputSource<Double, WPI_TalonSRX> encoderPositionFromSource() {
    return BaseMotorController::getSelectedSensorPosition;
  }

  @Override
  protected InputSource<Double, WPI_TalonSRX> encoderVelocityFromSource() {
    return BaseMotorController::getSelectedSensorVelocity;
  }

  @Override
  protected InputSource<Boolean, WPI_TalonSRX> fwdLimitFromSource() {
    return talon -> talon.isFwdLimitSwitchClosed() == 1;
  }

  @Override
  protected InputSource<Boolean, WPI_TalonSRX> revLimitFromSource() {
    return talon -> talon.isRevLimitSwitchClosed() == 1;
  }
}
