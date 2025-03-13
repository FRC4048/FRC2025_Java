// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

/** Add your docs here. */
public enum BargePoints {
    BLUELOWER(8.9246,0),
    BLUEHIGHER(8.9246,4),
    REDLOWER(7.56,4),
    REDHIGHER(7.56,8)
    ;


    private final double x;
    private final double y;
    BargePoints(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getY(){
        return y;
    }
    public double getx(){
        return x;
    }
}
