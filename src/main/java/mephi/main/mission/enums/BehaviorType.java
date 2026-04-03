package mephi.main.mission.enums;

public enum BehaviorType {
    AMBUSH_PREDATOR,
    DIRECT_ASSAULT,
    UNKNOWN;

    public static BehaviorType fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return BehaviorType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}