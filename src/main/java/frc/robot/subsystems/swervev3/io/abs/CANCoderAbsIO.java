package frc.robot.subsystems.swervev3.io.abs;

// import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.ctre.phoenix6.hardware.CANcoder;

public class CANCoderAbsIO implements SwerveAbsIO {
  // private final WPI_CANCoder absEncoder;
  private final CANcoder absEncoder;

  public CANCoderAbsIO(int canCoderID) {
    // absEncoder = new WPI_CANCoder(canCoderID);
    absEncoder = new CANcoder(canCoderID);
  }

  @Override
  public void updateInputs(SwerveAbsInput input) {
    // input.absEncoderPosition = absEncoder.getAbsolutePosition();
    input.absEncoderPosition = absEncoder.getAbsolutePosition().getValueAsDouble();
  }
}
