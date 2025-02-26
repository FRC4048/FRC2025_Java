// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.LightStripInputs;

;

public class LightStripInputsBuilder<T extends LightStripInputsBuilder<T>> {

    private boolean logPatternPWM;

    private final String folder;
public LightStripInputsBuilder(String folder){
    this.folder = folder;
}


T self() {
    return (T) this;
}

public LightStripInputs Build(){
    return new LightStripInputs(this);
}

public String getFolder(){
    return folder;
}
public T reset(){
    logPatternPWM = false;
return self();
}

public T lightstripPatternPWM(){
    logPatternPWM = true;
    return self();
}
}
