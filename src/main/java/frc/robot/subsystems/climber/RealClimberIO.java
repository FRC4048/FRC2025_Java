// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.constants.Constants;

/** Add your docs here. */
public class RealClimberIO implements ClimberIO{
    private final SparkMax climberMotor;
    private final SparkMaxConfig climberConfig;

    public RealClimberIO(){
        this.climberMotor = new SparkMax(Constants.CLIMBER_MOTOR_ID, SparkMax.MotorType.kBrushless);
        climberConfig = new SparkMaxConfig();
        configureMotor();
       
    }

    public void configureMotor(){
        climberConfig
            .idleMode(IdleMode.kBrake);
            climberMotor.configure(climberConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

    }
    @Override
    public void setClimberSpeed(double speed) {
        climberMotor.set(speed);
    }

    @Override
    public void stopClimber() {
     climberMotor.set(0);
    }
    
    @Override
    public void updateInputs(ClimberInputs inputs) {
        inputs.climberMotorEncoder = climberMotor.getEncoder().getPosition();
    }
}
