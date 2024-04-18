package uk.gov.dwp.health.atw.msclaim.models.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClaimType {
  EQUIPMENT_OR_ADAPTATION("EA"),
  TRAVEL_TO_WORK("TW"),
  SUPPORT_WORKER("SW"),
  ADAPTATION_TO_VEHICLE("AV"),
  TRAVEL_IN_WORK("TIW");

  private static final Map<String, ClaimType> BY_LABEL = new HashMap<>();
  public final String label;

  ClaimType(String label) {
    this.label = label;
  }

  static {
    for (ClaimType e : values()) {
      BY_LABEL.put(e.label, e);
    }
  }

  public static ClaimType valueOfLabel(String label) {
    return BY_LABEL.get(label);
  }
}