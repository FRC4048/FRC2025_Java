package frc.robot.subsystems.swervev3.io.abs;

import com.ctre.phoenix6.hardware.CANcoder;

public class CANCoderAbsIO implements SwerveAbsIO {
  private final CANcoder absEncoder;

  public CANCoderAbsIO(int canCoderID) {
    absEncoder = new CANcoder(canCoderID);
  }

  @Override
  public void updateInputs(SwerveAbsInput input) {
    input.absEncoderPosition = absEncoder.getAbsolutePosition().getValueAsDouble();
  }
}
