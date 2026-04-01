package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.CurseComponent;

public class CursesDecorator extends ReportDecorator {

    public CursesDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);
        sb.append("\n--- Проклятие ---\n");

        mission.getComponent(CurseComponent.class).ifPresent(curse -> {
            sb.append("  Имя:    ").append(curse.getName()).append("\n");
            sb.append("  Уровень: ").append(curse.getThreatLevel()).append("\n");
        });

        return sb.toString();
    }
}