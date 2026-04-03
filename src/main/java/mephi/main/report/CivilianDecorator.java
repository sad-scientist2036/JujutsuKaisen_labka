package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.CivilianComponent;

public class CivilianDecorator extends ReportDecorator {

    public CivilianDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);

        mission.getComponent(CivilianComponent.class).ifPresent(civilian -> {
            sb.append("\n--- Влияние на гражданских ---\n");
            if (civilian.getEvacuated() != null)
                sb.append("  Эвакуировано: ").append(civilian.getEvacuated()).append("\n");
            if (civilian.getInjured() != null)
                sb.append("  Пострадало: ").append(civilian.getInjured()).append("\n");
            if (civilian.getMissing() != null)
                sb.append("  Пропало: ").append(civilian.getMissing()).append("\n");
            if (civilian.getPublicExposureRisk() != null)
                sb.append("  Риск раскрытия: ").append(civilian.getPublicExposureRisk()).append("\n");
        });

        return sb.toString();
    }
}