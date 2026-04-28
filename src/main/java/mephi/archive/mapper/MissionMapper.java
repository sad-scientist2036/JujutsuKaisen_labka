package mephi.archive.mapper;

import mephi.archive.entity.*;
import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.components.*;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    public MissionEntity toEntity(Mission mission) {
        MissionEntity entity = new MissionEntity();
        entity.setMissionId(mission.getMissionId());
        entity.setDate(mission.getDate());
        entity.setLocation(mission.getLocation());
        entity.setOutcome(mission.getOutcome().name());
        entity.setDamageCost(mission.getDamageCost());
        entity.setNote(mission.getNote());

        // Curse
        mission.getComponent(CurseComponent.class).ifPresent(curse -> {
            CurseEntity curseEntity = new CurseEntity();
            curseEntity.setName(curse.getName());
            curseEntity.setThreatLevel(curse.getThreatLevel().name());
            entity.setCurse(curseEntity);
        });

        // Economic
        mission.getComponent(EconomicComponent.class).ifPresent(economic -> {
            EconomicEntity economicEntity = new EconomicEntity();
            economicEntity.setTotalDamageCost(economic.getTotalDamageCost());
            economicEntity.setInfrastructureDamage(economic.getInfrastructureDamage());
            economicEntity.setCommercialDamage(economic.getCommercialDamage());
            economicEntity.setTransportDamage(economic.getTransportDamage());
            economicEntity.setRecoveryEstimateDays(economic.getRecoveryEstimateDays());
            economicEntity.setInsuranceCovered(economic.getInsuranceCovered());
            entity.setEconomic(economicEntity);
        });

        // EnemyActivity
        mission.getComponent(EnemyActivityComponent.class).ifPresent(enemy -> {
            EnemyActivityEntity enemyEntity = new EnemyActivityEntity();
            if (enemy.getBehaviorType() != null) enemyEntity.setBehaviorType(enemy.getBehaviorType().name());
            if (enemy.getMobility() != null) enemyEntity.setMobility(enemy.getMobility().name());
            if (enemy.getEscalationRisk() != null) enemyEntity.setEscalationRisk(enemy.getEscalationRisk().name());
            enemyEntity.getTargetPriority().addAll(enemy.getTargetPriority());
            enemyEntity.getAttackPatterns().addAll(enemy.getAttackPatterns());
            enemyEntity.getCountermeasuresUsed().addAll(enemy.getCountermeasuresUsed());
            entity.setEnemyActivity(enemyEntity);
        });

        // Environment
        mission.getComponent(EnvironmentComponent.class).ifPresent(env -> {
            EnvironmentEntity envEntity = new EnvironmentEntity();
            if (env.getWeather() != null) envEntity.setWeather(env.getWeather().name());
            if (env.getTimeOfDay() != null) envEntity.setTimeOfDay(env.getTimeOfDay().name());
            if (env.getVisibility() != null) envEntity.setVisibility(env.getVisibility().name());
            envEntity.setCursedEnergyDensity(env.getCursedEnergyDensity());
            entity.setEnvironment(envEntity);
        });

        // Civilian
        mission.getComponent(CivilianComponent.class).ifPresent(civilian -> {
            CivilianEntity civilianEntity = new CivilianEntity();
            civilianEntity.setEvacuated(civilian.getEvacuated());
            civilianEntity.setInjured(civilian.getInjured());
            civilianEntity.setMissing(civilian.getMissing());
            civilianEntity.setPublicExposureRisk(civilian.getPublicExposureRisk());
            entity.setCivilian(civilianEntity);
        });

        // Sorcerers
        for (SorcererComponent s : mission.getSorcerers()) {
            SorcererEntity sorcererEntity = new SorcererEntity();
            sorcererEntity.setName(s.getName());
            sorcererEntity.setRank(s.getRank().name());
            entity.getSorcerers().add(sorcererEntity);
        }

        // Techniques
        for (TechniqueComponent t : mission.getTechniques()) {
            TechniqueEntity techniqueEntity = new TechniqueEntity();
            techniqueEntity.setName(t.getName());
            techniqueEntity.setType(t.getType().name());
            techniqueEntity.setOwner(t.getOwner());
            techniqueEntity.setDamage(t.getDamage());
            entity.getTechniques().add(techniqueEntity);
        }

        // Timeline
        for (OperationTimelineComponent event : mission.getTimeline()) {
            TimelineEventEntity eventEntity = new TimelineEventEntity();
            eventEntity.setTimestamp(event.getTimestamp());
            eventEntity.setType(event.getType());
            eventEntity.setDescription(event.getDescription());
            entity.getTimelineEvents().add(eventEntity);
        }

        return entity;
    }

    public Mission toModel(MissionEntity entity) {
        DefaultMissionBuilder builder = new DefaultMissionBuilder();

        builder.setMissionId(entity.getMissionId())
                .setDate(entity.getDate())
                .setLocation(entity.getLocation())
                .setOutcome(entity.getOutcome())
                .setDamageCost(entity.getDamageCost())
                .setNote(entity.getNote());

        // Curse
        if (entity.getCurse() != null) {
            CurseComponent curse = new CurseComponent.Builder()
                    .name(entity.getCurse().getName())
                    .threatLevel(entity.getCurse().getThreatLevel())
                    .build();
            builder.addComponent(curse);
        }

        // Economic
        if (entity.getEconomic() != null) {
            EconomicComponent economic = new EconomicComponent.Builder()
                    .totalDamageCost(entity.getEconomic().getTotalDamageCost())
                    .infrastructureDamage(entity.getEconomic().getInfrastructureDamage())
                    .commercialDamage(entity.getEconomic().getCommercialDamage())
                    .transportDamage(entity.getEconomic().getTransportDamage())
                    .recoveryEstimateDays(entity.getEconomic().getRecoveryEstimateDays())
                    .insuranceCovered(entity.getEconomic().getInsuranceCovered())
                    .build();
            builder.addComponent(economic);
        }

        // EnemyActivity
        if (entity.getEnemyActivity() != null) {
            EnemyActivityComponent.Builder enemyBuilder = new EnemyActivityComponent.Builder();
            if (entity.getEnemyActivity().getBehaviorType() != null) {
                enemyBuilder.behaviorType(entity.getEnemyActivity().getBehaviorType());
            }
            if (entity.getEnemyActivity().getMobility() != null) {
                enemyBuilder.mobility(entity.getEnemyActivity().getMobility());
            }
            if (entity.getEnemyActivity().getEscalationRisk() != null) {
                enemyBuilder.escalationRisk(entity.getEnemyActivity().getEscalationRisk());
            }
            for (String priority : entity.getEnemyActivity().getTargetPriority()) {
                enemyBuilder.addTargetPriority(priority);
            }
            for (String pattern : entity.getEnemyActivity().getAttackPatterns()) {
                enemyBuilder.addAttackPattern(pattern);
            }
            for (String measure : entity.getEnemyActivity().getCountermeasuresUsed()) {
                enemyBuilder.addCountermeasure(measure);
            }
            builder.addComponent(enemyBuilder.build());
        }

        // Environment
        if (entity.getEnvironment() != null) {
            EnvironmentComponent environment = new EnvironmentComponent.Builder()
                    .weather(entity.getEnvironment().getWeather())
                    .timeOfDay(entity.getEnvironment().getTimeOfDay())
                    .visibility(entity.getEnvironment().getVisibility())
                    .cursedEnergyDensity(entity.getEnvironment().getCursedEnergyDensity())
                    .build();
            builder.addComponent(environment);
        }

        // Civilian
        if (entity.getCivilian() != null) {
            CivilianComponent civilian = new CivilianComponent.Builder()
                    .evacuated(entity.getCivilian().getEvacuated())
                    .injured(entity.getCivilian().getInjured())
                    .missing(entity.getCivilian().getMissing())
                    .publicExposureRisk(entity.getCivilian().getPublicExposureRisk())
                    .build();
            builder.addComponent(civilian);
        }

        // Sorcerers
        for (SorcererEntity se : entity.getSorcerers()) {
            SorcererComponent sorcerer = new SorcererComponent.Builder()
                    .name(se.getName())
                    .rank(se.getRank())
                    .build();
            builder.addSorcerer(sorcerer);
        }

        // Techniques
        for (TechniqueEntity te : entity.getTechniques()) {
            TechniqueComponent technique = new TechniqueComponent.Builder()
                    .name(te.getName())
                    .type(te.getType())
                    .owner(te.getOwner())
                    .damage(te.getDamage())
                    .build();
            builder.addTechnique(technique);
        }

        // Timeline
        for (TimelineEventEntity tee : entity.getTimelineEvents()) {
            OperationTimelineComponent timeline = new OperationTimelineComponent.Builder()
                    .timestamp(tee.getTimestamp())
                    .type(tee.getType())
                    .description(tee.getDescription())
                    .build();
            builder.addTimelineEvent(timeline);
        }

        return builder.build();
    }
}