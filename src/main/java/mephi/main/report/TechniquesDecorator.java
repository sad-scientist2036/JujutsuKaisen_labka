package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.TechniqueComponent;

public class TechniquesDecorator extends ReportDecorator {

    public TechniquesDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);
        sb.append("\n--- Техники ---\n");

        if (mission.getTechniques().isEmpty()) {
            sb.append("  Нет данных о техниках\n");
        } else {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                TechniqueComponent t = mission.getTechniques().get(i);
                sb.append("Техника #").append(i + 1).append(":\n");
                sb.append("  Название: ").append(t.getName()).append("\n");
                sb.append("  Тип:      ").append(t.getType() != null ? t.getType() : "Не указано").append("\n");
                sb.append("  Урон:     ").append(t.getDamage()).append(" ед.\n");
                if (t.getOwner() != null) {
                    sb.append("  Владелец: ").append(t.getOwner()).append("\n");
                }
            }
        }

        return sb.toString();
    }
}