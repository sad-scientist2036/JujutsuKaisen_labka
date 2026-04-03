package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.EnvironmentComponent;

public class EnvironmentDecorator extends ReportDecorator {

    public EnvironmentDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);

        mission.getComponent(EnvironmentComponent.class).ifPresent(env -> {
            sb.append("\n--- Условия среды ---\n");
            if (env.getWeather() != null)
                sb.append("  Погода: ").append(env.getWeather()).append("\n");
            if (env.getTimeOfDay() != null)
                sb.append("  Время суток: ").append(env.getTimeOfDay()).append("\n");
            if (env.getVisibility() != null)
                sb.append("  Видимость: ").append(env.getVisibility()).append("\n");
            if (env.getCursedEnergyDensity() != null)
                sb.append("  Плотность проклятой энергии: ").append(env.getCursedEnergyDensity()).append("\n");
        });

        return sb.toString();
    }
}