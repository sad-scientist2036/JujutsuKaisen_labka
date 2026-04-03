package mephi.main.mission.enums;

public enum PublicExposureRisk {
    HIGH,
    MEDIUM,
    LOW,
    UNKNOWN;

    public static PublicExposureRisk fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return PublicExposureRisk.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}