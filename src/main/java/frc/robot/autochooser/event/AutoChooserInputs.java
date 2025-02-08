package frc.robot.autochooser.event;

import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class AutoChooserInputs extends KeyedLoggableInputs {
  AutoAction action = AutoAction.INVALID;
  FieldLocation location = FieldLocation.INVALID;
  AutoAction defaultAction = AutoAction.INVALID;
  FieldLocation defaultLocation = FieldLocation.INVALID;
  AutoAction feedbackAction = AutoAction.INVALID;
  FieldLocation feedbackLocation = FieldLocation.INVALID;

  public AutoChooserInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("action", action);
    table.put("location", location);
    table.put("defaultAction", defaultAction);
    table.put("defaultLocation", defaultLocation);
    table.put("feedbackAction", feedbackAction);
    table.put("feedbackLocation", feedbackLocation);
  }

  @Override
  public void fromLog(LogTable table) {
    action = table.get("action", action);
    location = table.get("location", location);
    defaultAction = table.get("defaultAction", defaultAction);
    defaultLocation = table.get("defaultLocation", defaultLocation);
    feedbackAction = table.get("feedbackAction", feedbackAction);
    feedbackLocation = table.get("feedbackLocation", feedbackLocation);
  }
}
