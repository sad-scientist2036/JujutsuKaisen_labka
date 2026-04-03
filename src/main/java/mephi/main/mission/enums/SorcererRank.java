package mephi.main.mission.enums;

public enum SorcererRank {
    GRADE_1,
    GRADE_2,
    GRADE_3,
    GRADE_4,
    SPECIAL_GRADE,
    SEMI_GRADE_1,
    UNKNOWN;

    public static SorcererRank fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return SorcererRank.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}