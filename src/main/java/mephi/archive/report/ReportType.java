package mephi.archive.report;

public enum ReportType {
    SIMPLE("simple", "Краткое резюме"),
    DETAILED("detailed", "Детализированный отчёт"),
    RISK("risk", "Отчёт по рискам"),
    FULL("full", "Полный отчёт");

    private final String value;
    private final String description;

    ReportType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ReportType fromString(String text) {
        for (ReportType type : ReportType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return SIMPLE;
    }
}