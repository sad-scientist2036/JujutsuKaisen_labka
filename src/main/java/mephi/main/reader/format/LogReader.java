package mephi.main.reader.format;

import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LogReader implements FileReader {

    @Override
    public Mission read(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        MissionBuilder builder = new DefaultMissionBuilder();

        // Для сбора данных enemyActivity
        String behaviorType = null;
        List<String> attackPatterns = new ArrayList<>();
        String mobility = null;
        String escalationRisk = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length < 2) continue;

            String type = parts[0].trim();

            switch (type) {
                case "MISSION_CREATED":
                    if (parts.length >= 4) {
                        builder.setMissionId(parts[1].trim());
                        builder.setDate(parts[2].trim());
                        builder.setLocation(parts[3].trim());
                    }
                    break;

                case "CURSE_DETECTED":
                    if (parts.length >= 3) {
                        CurseComponent curse = new CurseComponent.Builder()
                                .name(parts[1].trim())
                                .threatLevel(parts[2].trim())
                                .build();
                        builder.addComponent(curse);
                    }
                    break;

                case "SORCERER_ASSIGNED":
                    if (parts.length >= 3) {
                        SorcererComponent sorcerer = new SorcererComponent.Builder()
                                .name(parts[1].trim())
                                .rank(parts[2].trim())
                                .build();
                        builder.addSorcerer(sorcerer);
                    }
                    break;

                case "TECHNIQUE_USED":
                    if (parts.length >= 5) {
                        TechniqueComponent technique = new TechniqueComponent.Builder()
                                .name(parts[1].trim())
                                .type(parts[2].trim())
                                .owner(parts[3].trim())
                                .damage(parseLong(parts[4].trim()))
                                .build();
                        builder.addTechnique(technique);
                    }
                    break;

                case "TIMELINE_EVENT":
                    if (parts.length >= 4) {
                        OperationTimelineComponent timeline = new OperationTimelineComponent.Builder()
                                .timestamp(parts[1].trim())
                                .type(parts[2].trim())
                                .description(parts[3].trim())
                                .build();
                        builder.addTimelineEvent(timeline);
                    }
                    break;

                case "ENEMY_ACTION":
                    if (parts.length >= 3) {
                        String actionType = parts[1].trim();
                        String description = parts[2].trim();
                        if (behaviorType == null) {
                            behaviorType = actionType;
                        }
                        if (description != null && !description.isEmpty()) {
                            attackPatterns.add(description);
                        }
                    }
                    break;

                case "CIVILIAN_IMPACT":
                    if (parts.length >= 2) {
                        Integer evacuated = null;
                        Integer injured = null;
                        Integer missing = null;

                        for (int i = 1; i < parts.length; i++) {
                            String[] kv = parts[i].split("=");
                            if (kv.length == 2) {
                                switch (kv[0].trim()) {
                                    case "evacuated":
                                        evacuated = parseInt(kv[1].trim());
                                        break;
                                    case "injured":
                                        injured = parseInt(kv[1].trim());
                                        break;
                                    case "missing":
                                        missing = parseInt(kv[1].trim());
                                        break;
                                }
                            }
                        }

                        CivilianComponent civilian = new CivilianComponent.Builder()
                                .evacuated(evacuated)
                                .injured(injured)
                                .missing(missing)
                                .build();
                        builder.addComponent(civilian);
                    }
                    break;

                case "MISSION_RESULT":
                    if (parts.length >= 3) {
                        builder.setOutcome(parts[1].trim());
                        for (int i = 2; i < parts.length; i++) {
                            String[] kv = parts[i].split("=");
                            if (kv.length == 2 && kv[0].trim().equals("damageCost")) {
                                builder.setDamageCost(parseLong(kv[1].trim()));
                            }
                        }
                    }
                    break;
            }
        }
        if (behaviorType != null || !attackPatterns.isEmpty() || mobility != null || escalationRisk != null) {
            EnemyActivityComponent.Builder enemyBuilder = new EnemyActivityComponent.Builder();
            if (behaviorType != null) enemyBuilder.behaviorType(behaviorType);
            for (String pattern : attackPatterns) {
                enemyBuilder.addAttackPattern(pattern);
            }
            if (mobility != null) enemyBuilder.mobility(mobility);
            if (escalationRisk != null) enemyBuilder.escalationRisk(escalationRisk);

            builder.addComponent(enemyBuilder.build());
        }

        return builder.build();
    }

    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}