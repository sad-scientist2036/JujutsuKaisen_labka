package mephi.main.report;

import mephi.main.mission.Mission;

public class SimpleReport implements Report {

    @Override
    public String generate(Mission mission) {
        StringBuilder sb = new StringBuilder();

        sb.append("        Информация о миссии\n");
        sb.append("ID миссии:    ").append(mission.getMissionId() != null ? mission.getMissionId() : "Не указано").append("\n");
        sb.append("Дата:         ").append(mission.getDate() != null ? mission.getDate() : "Не указана").append("\n");
        sb.append("Локация:      ").append(mission.getLocation() != null ? mission.getLocation() : "Не указано").append("\n");
        sb.append("Итог:         ").append(mission.getOutcome() != null ? mission.getOutcome() : "Не указано").append("\n");
        sb.append("Ущерб:        ").append(mission.getDamageCost()).append(" йен\n");

        if (mission.getNote() != null && !mission.getNote().isEmpty()) {
            sb.append("\n--- Примечание ---\n");
            sb.append(mission.getNote()).append("\n");
        }

        return sb.toString();
    }
}