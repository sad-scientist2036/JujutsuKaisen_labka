package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.SorcererComponent;

public class SorcerersDecorator extends ReportDecorator {

    public SorcerersDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);
        sb.append("\n--- Участники ---\n");

        if (mission.getSorcerers().isEmpty()) {
            sb.append("  Нет данных об участниках\n");
        } else {
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                SorcererComponent s = mission.getSorcerers().get(i);
                sb.append("Маг #").append(i + 1).append(":\n");
                sb.append("  Имя:    ").append(s.getName()).append("\n");
                sb.append("  Ранг:   ").append(s.getRank() != null ? s.getRank() : "Не указано").append("\n");
            }
        }

        return sb.toString();
    }
}