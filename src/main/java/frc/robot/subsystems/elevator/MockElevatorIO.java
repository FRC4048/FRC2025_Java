package frc.robot.subsystems.elevator;

public class MockElevatorIO implements ElevatorIO{
    @Override
    public void setSpeed(double spd) {
        
    }
    @Override
    public void updateInputs(ElevatorInputs inputs) {
        
    }    
    @Override
    public double getEncoderValue1(){
            return 0;

    }
    @Override
    public double getEncoderValue2(){
        return 0;
    }
}
