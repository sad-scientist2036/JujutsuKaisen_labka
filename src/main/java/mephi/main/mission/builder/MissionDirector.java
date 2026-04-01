package mephi.main.mission.builder;

import mephi.main.mission.Mission;
import mephi.main.mission.components.*;

import java.util.List;

public class MissionDirector {

    private final MissionBuilder builder;

    public MissionDirector(MissionBuilder builder) {
        this.builder = builder;
    }

    public Mission constructSimpleMission(String missionId, String date,
                                          String location, String outcome,
                                          long damageCost, CurseComponent curse) {
        return builder.reset()
                .setMissionId(missionId)
                .setDate(date)
                .setLocation(location)
                .setOutcome(outcome)
                .setDamageCost(damageCost)
                .addComponent(curse)
                .build();
    }

    public Mission constructMissionWithSorcerers(String missionId, String date,
                                                 String location, String outcome,
                                                 long damageCost, CurseComponent curse,
                                                 SorcererComponent... sorcerers) {
        MissionBuilder b = builder.reset()
                .setMissionId(missionId)
                .setDate(date)
                .setLocation(location)
                .setOutcome(outcome)
                .setDamageCost(damageCost)
                .addComponent(curse);

        for (SorcererComponent s : sorcerers) {
            b.addSorcerer(s);
        }

        return b.build();
    }

    public Mission constructFullMission(String missionId, String date,
                                        String location, String outcome,
                                        long damageCost, CurseComponent curse,
                                        List<SorcererComponent> sorcerers,
                                        List<TechniqueComponent> techniques,
                                        EconomicComponent economic,
                                        EnemyActivityComponent enemy,
                                        EnvironmentComponent environment,
                                        CivilianComponent civilian) {
        MissionBuilder b = builder.reset()
                .setMissionId(missionId)
                .setDate(date)
                .setLocation(location)
                .setOutcome(outcome)
                .setDamageCost(damageCost)
                .addComponent(curse);

        if (sorcerers != null) {
            for (SorcererComponent s : sorcerers) {
                b.addSorcerer(s);
            }
        }

        if (techniques != null) {
            for (TechniqueComponent t : techniques) {
                b.addTechnique(t);
            }
        }

        if (economic != null) b.addComponent(economic);
        if (enemy != null) b.addComponent(enemy);
        if (environment != null) b.addComponent(environment);
        if (civilian != null) b.addComponent(civilian);

        return b.build();
    }

    public Mission getResult() {
        return builder.build();
    }
}