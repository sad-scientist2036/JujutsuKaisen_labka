package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;

public class CivilianComponent implements MissionComponent {
    private final Integer evacuated;
    private final Integer injured;
    private final Integer missing;
    private final String publicExposureRisk;

    private CivilianComponent(Builder builder) {
        this.evacuated = builder.evacuated;
        this.injured = builder.injured;
        this.missing = builder.missing;
        this.publicExposureRisk = builder.publicExposureRisk;
    }

    @Override
    public String getComponentType() {
        return "civilian";
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder("Влияние на гражданских: ");
        if (evacuated != null) sb.append("эвакуировано=").append(evacuated);
        if (injured != null) sb.append(", пострадало=").append(injured);
        if (missing != null) sb.append(", пропало=").append(missing);
        if (publicExposureRisk != null) sb.append(", риск раскрытия=").append(publicExposureRisk);
        return sb.toString();
    }

    public Integer getEvacuated() { return evacuated; }
    public Integer getInjured() { return injured; }
    public Integer getMissing() { return missing; }
    public String getPublicExposureRisk() { return publicExposureRisk; }

    public static class Builder {
        private Integer evacuated;
        private Integer injured;
        private Integer missing;
        private String publicExposureRisk;

        public Builder evacuated(Integer value) { this.evacuated = value; return this; }
        public Builder injured(Integer value) { this.injured = value; return this; }
        public Builder missing(Integer value) { this.missing = value; return this; }
        public Builder publicExposureRisk(String value) { this.publicExposureRisk = value; return this; }

        public CivilianComponent build() {
            return new CivilianComponent(this);
        }
    }
}