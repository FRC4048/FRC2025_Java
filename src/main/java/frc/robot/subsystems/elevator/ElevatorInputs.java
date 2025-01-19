package frc.robot.subsystems.elevator;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;


public class ElevatorInputs implements LoggableInputs{
    public static double elevatorMotor1EncoderValue = 0;
    public static double elevatorMotor2EncoderValue = 0;
    @Override
    public void toLog(LogTable table){
        table.put("elevatorMotor1EncoderValue", elevatorMotor1EncoderValue);
        table.put("elevatorMotor2EncoderValue", elevatorMotor2EncoderValue);
    }
    @Override
    public void fromLog(LogTable table){
        elevatorMotor1EncoderValue = table.get("elevatorMotor1EncoderValue", elevatorMotor1EncoderValue);
        elevatorMotor2EncoderValue = table.get("elevatorMotor2EncoderValue", elevatorMotor2EncoderValue);
    }
}
