package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import mephi.main.mission.enums.Weather;
import mephi.main.mission.enums.TimeOfDay;
import mephi.main.mission.enums.Visibility;

public class EnvironmentComponent implements MissionComponent {
    private final Weather weather;
    private final TimeOfDay timeOfDay;
    private final Visibility visibility;
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

    public Weather getWeather() { return weather; }
    public TimeOfDay getTimeOfDay() { return timeOfDay; }
    public Visibility getVisibility() { return visibility; }
    public Integer getCursedEnergyDensity() { return cursedEnergyDensity; }

    public static class Builder {
        private Weather weather;
        private TimeOfDay timeOfDay;
        private Visibility visibility;
        private Integer cursedEnergyDensity;

        public Builder weather(String value) {
            this.weather = Weather.fromString(value);
            return this;
        }
        public Builder timeOfDay(String value) {
            this.timeOfDay = TimeOfDay.fromString(value);
            return this;
        }
        public Builder visibility(String value) {
            this.visibility = Visibility.fromString(value);
            return this;
        }
        public Builder cursedEnergyDensity(Integer value) {
            this.cursedEnergyDensity = value;
            return this;
        }

        public EnvironmentComponent build() {
            return new EnvironmentComponent(this);
        }
    }
}