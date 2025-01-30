package frc.robot.subsystems.hihiRoller;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealHihiRollerIO implements HihiRollerIO {
    private final SparkMax hihiRollerMotor;

    public RealHihiRollerIO() {
        this.hihiRollerMotor =
                new SparkMax(Constants.ALGAE_ROLLER_CAN_ID, SparkLowLevel.MotorType.kBrushless);
        configureMotor();
    }

    public void configureMotor() {
        SparkMaxConfig hihiRollerConfig = new SparkMaxConfig();
        hihiRollerConfig.idleMode(IdleMode.kBrake);
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
    public void updateInputs(HihiRollerInputs inputs) {
        inputs.hihiRollerEncoder = hihiRollerMotor.getEncoder().getPosition();
        inputs.hihiRollerVelocity = hihiRollerMotor.getEncoder().getVelocity();
    }
}
