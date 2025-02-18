package frc.robot.autochooser.event;

import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;

/**
 * Wrapper Class, that Contains a {@link AutoAction} and a {@link
 * FieldLocation}
 */
public record AutoEvent(AutoAction action, FieldLocation location) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AutoEvent autoEvent = (AutoEvent) o;
    return action.equals(autoEvent.action) && location.equals(autoEvent.location);
  }

}
