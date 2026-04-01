package mephi.main.report;

import mephi.main.mission.Mission;

public abstract class ReportDecorator implements Report {
    protected Report wrapped;

    public ReportDecorator(Report report) {
        this.wrapped = report;
    }

    @Override
    public String generate(Mission mission) {
        return wrapped.generate(mission);
    }
}