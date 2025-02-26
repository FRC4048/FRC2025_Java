// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.logging.subsystem.inputs;

import org.littletonrobotics.junction.LogTable;

import frc.robot.utils.logging.subsystem.builders.LightStripInputsBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;

/** Add your docs here. */
public class LightStripInputs extends FolderInputs{

    public LightStripInputs(LightStripInputsBuilder<?> builder){
        super(builder.getFolder());
    }
    @Override
    public void toLog(LogTable table) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toLog'");
    }

    @Override
    public void fromLog(LogTable table) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromLog'");
    }

    @Override
    protected boolean process(InputProvider inputProvider) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }}
