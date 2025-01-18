package frc.robot.subsystems.algaeextender;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants2025;

public class RealAgaeExtenderIO implements AlgaeExtenderIO{
    private final WPI_TalonSRX extenderMotor; //TODO: change later to whatever
    public RealAgaeExtenderIO() {
        this.extenderMotor = new WPI_TalonSRX(Constants.ALGAE_EXTENDER_MOTOR_ID);
        configureMotor();
        resetExtenderEncoder();
    }
    private void configureMotor() {
        this.extenderMotor.setNeutralMode(NeutralMode.Brake);
        this.extenderMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        this.extenderMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    }
    @Override
    public void extendAlgae() {
        this.extenderMotor.set(algaeSpeed);
    }
    @Override
    public void retractAlgae() {
        this.extenderMotor.set(algaeSpeed*-1);
    }
    @Override
    public void stopAlgae() {
        this.extenderMotor.set(0);
    }
    @Override
    public void setAlgaeExtenderSpeed(double speed) {
        this.algaeSpeed = speed;
    }
    @Override
    public void resetExtenderEncoder() {
        this.extenderMotor.setSelectedSensorPosition(0);
    }
    @Override
    public void updateInputs(CoralInputs inputs) {
        inputs.algaeExtenderEncoderPos = extenderMotor.get();
        inputs.lowerTripped = extenderMotor.getSensorCollection().isFwdLimitSwitchClosed();
        inputs.upperTripped = extenderMotor.getSensorCollection().isRevLimitSwitchClosed();
    }

}
