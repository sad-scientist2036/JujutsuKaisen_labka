package mephi.main.mission.enums;

public enum Visibility {
    HIGH,
    MEDIUM,
    LOW,
    UNKNOWN;

    public static Visibility fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Visibility.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}