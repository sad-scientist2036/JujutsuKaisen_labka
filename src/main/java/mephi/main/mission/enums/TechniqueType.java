package mephi.main.mission.enums;

public enum TechniqueType {
    INNATE,
    SHIKIGAMI,
    WEAPON,
    BODY,
    UNKNOWN;

    public static TechniqueType fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return TechniqueType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}