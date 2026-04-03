package mephi.main.mission.builder;

import mephi.main.mission.Mission;
import mephi.main.mission.MissionComponent;
import mephi.main.mission.components.OperationTimelineComponent;
import mephi.main.mission.components.SorcererComponent;
import mephi.main.mission.components.TechniqueComponent;
import mephi.main.mission.enums.Outcome;

public class DefaultMissionBuilder implements MissionBuilder {

    private Mission mission;

    public DefaultMissionBuilder() {
        reset();
    }

    @Override
    public MissionBuilder reset() {
        mission = new Mission();
        return this;
    }

    @Override
    public MissionBuilder setMissionId(String missionId) {
        mission.setMissionId(missionId);
        return this;
    }

    @Override
    public MissionBuilder setDate(String date) {
        mission.setDate(date);
        return this;
    }

    @Override
    public MissionBuilder setLocation(String location) {
        mission.setLocation(location);
        return this;
    }

    @Override
    public MissionBuilder setOutcome(String outcome) {
        mission.setOutcome(Outcome.fromString(outcome));
        return this;
    }

    @Override
    public MissionBuilder setDamageCost(long damageCost) {
        mission.setDamageCost(damageCost);
        return this;
    }

    @Override
    public MissionBuilder setNote(String note) {
        mission.setNote(note);
        return this;
    }

    @Override
    public MissionBuilder addComponent(MissionComponent component) {
        mission.addComponent(component);
        return this;
    }

    @Override
    public MissionBuilder addSorcerer(SorcererComponent sorcerer) {
        mission.addSorcerer(sorcerer);
        return this;
    }

    @Override
    public MissionBuilder addTechnique(TechniqueComponent technique) {
        mission.addTechnique(technique);
        return this;
    }

    @Override
    public MissionBuilder addTimelineEvent(OperationTimelineComponent event) {
        mission.addTimelineEvent(event);
        return this;
    }

    @Override
    public Mission build() {
        if (mission.getMissionId() == null || mission.getMissionId().isEmpty()) {
            throw new IllegalStateException("missionId is required");
        }
        if (mission.getDate() == null || mission.getDate().isEmpty()) {
            throw new IllegalStateException("date is required");
        }
        if (mission.getLocation() == null || mission.getLocation().isEmpty()) {
            throw new IllegalStateException("location is required");
        }
        if (mission.getOutcome() == null) {
            throw new IllegalStateException("outcome is required");
        }
        return mission;
    }
}