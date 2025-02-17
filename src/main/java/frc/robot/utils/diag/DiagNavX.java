package frc.robot.utils.diag;

import com.studica.frc.AHRS;

/**
 * Add your docs here.
 */
public class DiagNavX extends DiagDistanceTraveled {
    private AHRS navX;

    public DiagNavX(String title, String name, double requiredTravel, AHRS navX) {
        super(title, name, requiredTravel);
        this.navX = navX;

        reset();
    }

    @Override
    protected double getCurrentValue() {
        return navX.getAngle();
    }
}
