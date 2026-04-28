package mephi.archive.service;

import mephi.archive.report.ReportType;
import mephi.main.mission.Mission;
import mephi.main.report.*;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public String generateReport(Mission mission, ReportType type) {
        Report report = new SimpleReport();

        switch (type) {
            case SIMPLE:

                break;

            case DETAILED:
                report = new CursesDecorator(report);
                report = new SorcerersDecorator(report);
                report = new TechniquesDecorator(report);
                break;

            case RISK:
                report = new EnemyActivityDecorator(report);
                report = new EconomicDecorator(report);
                break;

            case FULL:
                report = new CursesDecorator(report);
                report = new SorcerersDecorator(report);
                report = new TechniquesDecorator(report);
                report = new EnemyActivityDecorator(report);
                report = new EconomicDecorator(report);
                report = new EnvironmentDecorator(report);
                report = new CivilianDecorator(report);
                report = new TimelineDecorator(report);
                break;
        }
        return report.generate(mission);
    }
}