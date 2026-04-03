package mephi.main.mission.builder;

import mephi.main.mission.Mission;
import mephi.main.mission.MissionComponent;
import mephi.main.mission.components.OperationTimelineComponent;
import mephi.main.mission.components.SorcererComponent;
import mephi.main.mission.components.TechniqueComponent;

public interface MissionBuilder {

    MissionBuilder setMissionId(String missionId);
    MissionBuilder setDate(String date);
    MissionBuilder setLocation(String location);
    MissionBuilder setOutcome(String outcome);
    MissionBuilder setDamageCost(long damageCost);
    MissionBuilder setNote(String note);

    MissionBuilder addComponent(MissionComponent component);
    MissionBuilder addSorcerer(SorcererComponent sorcerer);
    MissionBuilder addTechnique(TechniqueComponent technique);
    MissionBuilder addTimelineEvent(OperationTimelineComponent event);

    MissionBuilder reset();
    Mission build();
}