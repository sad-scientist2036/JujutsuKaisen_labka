package mephi.main.mission.enums;

public enum Weather {
    HEAVY_RAIN,
    CLEAR,
    FOG,
    CLOUDY,
    UNKNOWN;

    public static Weather fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Weather.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}