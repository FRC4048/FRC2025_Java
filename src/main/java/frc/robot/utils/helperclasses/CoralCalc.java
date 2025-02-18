// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.helperclasses;

/** Only to be used in "reef mode" */
public class CoralCalc {
  private final double angularVelocity; // how many degrees per second (can be chosen)
  private final double y; // y position from odometry
  private final double x; // x position from odometry
  private final double theta; // angle in relation to reef center
  private double yVelocity; // instantaneous x velocity to calculate
  private double xVelocity; // instantaneous y velocity to calculate

  public CoralCalc(double angularVelocity, double y, double x, double theta) {
    this.angularVelocity = angularVelocity;
    this.y = y;
    this.x = x;
    this.theta = theta;
      // distance to reef center
      double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // distance calculation
      calculateVelocities();
  }

  private void calculateVelocities() {
    this.yVelocity =
        (Math.pow(1 / Math.cos(this.theta), 2) * Math.pow(this.x, 2) * this.angularVelocity);
    this.yVelocity /= (this.x + (Math.pow(this.y, 2) / this.x));
    this.xVelocity = this.yVelocity * -1 * (this.y / this.x);
    // weird math stuff (don't ask me to show you it's wonky)
  }

  /** returns velocity robot should travel at */
  public double getVelocity() {
    return Math.sqrt(Math.pow(this.xVelocity, 2) + Math.pow(this.yVelocity, 2));
  }

  /** returns angle in comparison to reef robot should travel at */
  public double getAngle() {
    return Math.atan(this.yVelocity / this.xVelocity);
  }
}
