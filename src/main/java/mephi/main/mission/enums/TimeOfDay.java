package mephi.main.mission.enums;

public enum TimeOfDay {
    NIGHT,
    DAY,
    DAWN,
    DUSK,
    UNKNOWN;

    public static TimeOfDay fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return TimeOfDay.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}