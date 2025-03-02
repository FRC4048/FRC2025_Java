package frc.robot.autochooser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AutoAction {
  DO_NOTHING("Do Nothing"),
  TWO_PIECE_FORK("2 Piece Auto - Fork"),
  TWO_PIECE_LINE("2 Piece Auto - Line"),
  ONE_PIECE("1 Piece"),
  CROSS_THE_LINE("Cross The Line"),
  INVALID("INVALID");
  private final String name;
  private static final HashMap<String, AutoAction> nameMap =
      new HashMap<>(
          Arrays.stream(AutoAction.values())
              .collect(Collectors.toMap(AutoAction::getName, Function.identity())));

  AutoAction(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }

  public static AutoAction fromName(String name) {
    return nameMap.get(name);
  }
}
