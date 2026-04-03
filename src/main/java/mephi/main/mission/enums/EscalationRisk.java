package mephi.main.mission.enums;

public enum EscalationRisk {
    HIGH,
    MEDIUM,
    LOW,
    UNKNOWN;

    public static EscalationRisk fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return EscalationRisk.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}