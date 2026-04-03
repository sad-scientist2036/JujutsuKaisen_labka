package mephi.main.mission.enums;

public enum Mobility {
    HIGH,
    MEDIUM,
    LOW,
    UNKNOWN;

    public static Mobility fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Mobility.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}