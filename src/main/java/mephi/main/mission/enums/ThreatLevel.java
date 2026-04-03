package mephi.main.mission.enums;

public enum ThreatLevel {
    HIGH,
    SPECIAL_GRADE,
    MEDIUM,
    LOW,
    UNKNOWN;

    public static ThreatLevel fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return ThreatLevel.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}