package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.OperationTimelineComponent;

public class TimelineDecorator extends ReportDecorator {

    public TimelineDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);

        if (!mission.getTimeline().isEmpty()) {
            sb.append("\n--- Хронология событий ---\n");
            for (OperationTimelineComponent event : mission.getTimeline()) {
                sb.append("  ").append(event.getInfo()).append("\n");
            }
        }

        return sb.toString();
    }
}