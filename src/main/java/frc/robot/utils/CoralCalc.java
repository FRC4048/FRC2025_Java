// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

public class CoralCalc {
  private double angularVelocity;
  private double y;
  private double x;
  private double theta;
  private double r;
  private double yVelocity;
  private double xVelocity;

  public CoralCalc(double angularVelocity, double y, double x, double theta) {
    this.angularVelocity = angularVelocity;
    this.y = y;
    this.x = x;
    this.theta = theta;
    this.r = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    calculateVelocities();
  }

  private void calculateVelocities() {
    this.yVelocity = (Math.pow(1/Math.cos(this.theta),2) * Math.pow(this.x,2) * this.angularVelocity);
    this.yVelocity /= (this.x + (Math.pow(this.y,2) / this.x));
    this.xVelocity = this.yVelocity * -1 * (this.y/this.x);
  }

  public double getVelocity() {
    return Math.sqrt(Math.pow(this.xVelocity,2), Math.pow(this.yVelocity,2));
  }

  public double getAngle() {
    return Math.atan(this.yVelocity/this.xVelocity);
  }
}