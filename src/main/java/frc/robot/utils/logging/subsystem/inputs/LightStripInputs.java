// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.logging.subsystem.inputs;

import org.littletonrobotics.junction.LogTable;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.builders.LightStripInputsBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.utils.logging.subsystem.providers.LightStripProvider;

/** Add your docs here. */
public class LightStripInputs extends FolderInputs{

     private final LightStripInputsBuilder<?> builder;

     private BlinkinPattern patternPWM;

    public LightStripInputs(LightStripInputsBuilder<?> builder){
        super(builder.getFolder());
        this.patternPWM = builder.islogPatternPWM() ? BlinkinPattern.BLACK : null; 
        this.builder = builder;
    }
    @Override
    public void toLog(LogTable table) {
     if(builder.islogPatternPWM()){
        table.put("PatternPWM", patternPWM);
     }
    }

    @Override
    public void fromLog(LogTable table) {
        if(builder.islogPatternPWM()){
            patternPWM = table.get("PatternPWM", patternPWM);
         }
    }

    @Override
    protected boolean process(InputProvider inputProvider) {
    if (inputProvider instanceof LightStripProvider LightStripProvider){
        if (builder.islogPatternPWM()) {
            patternPWM = LightStripProvider.getPatternPWM();
          }
        return true;
    }else{
            DriverStation.reportError("inputProvider must be of type LightStripProvider", true);
        return false;
    }
    
    }}
