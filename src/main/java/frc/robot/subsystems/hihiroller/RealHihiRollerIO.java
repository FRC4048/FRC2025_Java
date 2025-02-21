package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxSwitch;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

public class RealHihiRollerIO implements HihiRollerIO {
  protected final SparkMax hihiRollerMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealHihiRollerIO() {
    this.hihiRollerMotor =
        new SparkMax(Constants.ALGAE_ROLLER_CAN_ID, SparkLowLevel.MotorType.kBrushless);
    this.inputProvider = new SparkMaxInputProvider(hihiRollerMotor);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "HiHiRoller", "Forward", hihiRollerMotor, DiagSparkMaxSwitch.Direction.FORWARD));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "HiHiRoller", "Reverse", hihiRollerMotor, DiagSparkMaxSwitch.Direction.REVERSE));
  }

  public void configureMotor() {
    SparkMaxConfig hihiRollerConfig = new SparkMaxConfig();
    hihiRollerConfig.idleMode(IdleMode.kBrake);
    hihiRollerConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    hihiRollerMotor.configure(
        hihiRollerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setRollerSpeed(double speed) {
    hihiRollerMotor.set(speed);
  }

  @Override
  public void stopRollerMotor() {
    hihiRollerMotor.set(0);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
