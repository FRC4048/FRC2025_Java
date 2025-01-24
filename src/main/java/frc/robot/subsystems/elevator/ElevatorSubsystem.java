package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class ElevatorSubsystem extends SubsystemBase {
  private final LoggableSystem<ElevatorIO, ElevatorInputs> elevatorSystem;

public class ElevatorSubsystem extends SubsystemBase{
    private final LoggableSystem<ElevatorIO, ElevatorInputs>elevatorSystem;
    public ElevatorSubsystem(ElevatorIO ElevatorIO){
        this.elevatorSystem = new LoggableSystem<>(ElevatorIO, new ElevatorInputs());
    }
    public void setElevatorMotorSpeed(double speed) {
        elevatorSystem.getIO().setSpeed(speed);
    }
    public double getEncoderValue1(){
        return elevatorSystem.getInputs().elevatorMotor1EncoderValue;
    }
    public double getEncoderValue2(){
        return elevatorSystem.getInputs().elevatorMotor2EncoderValue;
    }
    
    @Override
    public void periodic() {
        elevatorSystem.updateInputs();
    }

}
