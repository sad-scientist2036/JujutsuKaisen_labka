package mephi.main.report;

import mephi.main.mission.Mission;

public interface Report {
    String generate(Mission mission);
}