package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;

public class EnvironmentComponent implements MissionComponent {
    private final String weather;
    private final String timeOfDay;
    private final String visibility;
    private final Integer cursedEnergyDensity;

    private EnvironmentComponent(Builder builder) {
        this.weather = builder.weather;
        this.timeOfDay = builder.timeOfDay;
        this.visibility = builder.visibility;
        this.cursedEnergyDensity = builder.cursedEnergyDensity;
    }

    @Override
    public String getComponentType() {
        return "environment";
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder("Условия среды: ");
        if (weather != null) sb.append("погода=").append(weather);
        if (timeOfDay != null) sb.append(", время=").append(timeOfDay);
        if (visibility != null) sb.append(", видимость=").append(visibility);
        if (cursedEnergyDensity != null) sb.append(", плотность=").append(cursedEnergyDensity);
        return sb.toString();
    }

    public String getWeather() { return weather; }
    public String getTimeOfDay() { return timeOfDay; }
    public String getVisibility() { return visibility; }
    public Integer getCursedEnergyDensity() { return cursedEnergyDensity; }

    public static class Builder {
        private String weather;
        private String timeOfDay;
        private String visibility;
        private Integer cursedEnergyDensity;

        public Builder weather(String value) { this.weather = value; return this; }
        public Builder timeOfDay(String value) { this.timeOfDay = value; return this; }
        public Builder visibility(String value) { this.visibility = value; return this; }
        public Builder cursedEnergyDensity(Integer value) { this.cursedEnergyDensity = value; return this; }

        public EnvironmentComponent build() {
            return new EnvironmentComponent(this);
        }
    }
}