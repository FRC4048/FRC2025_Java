// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.logging.subsystem.providers;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.utils.BlinkinPattern;

/** Add your docs here. */
public class LightStripInputProvider implements LightStripProvider{
    
    private final Spark spark;

    public LightStripInputProvider(Spark spark) {
    this.spark = spark;
  }

    @Override
    public BlinkinPattern getPatternPWM() {
        return BlinkinPattern.of(spark.get());
    }}
