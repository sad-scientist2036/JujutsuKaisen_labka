package mephi.main.reader.format;

import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionTxtReader implements FileReader {

    @Override
    public Mission read(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        MissionBuilder builder = new DefaultMissionBuilder();

        String currentSection = null;
        Map<String, String> currentData = new HashMap<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                processSection(currentSection, currentData, builder);
                currentSection = line.substring(1, line.length() - 1);
                currentData.clear();
                continue;
            }

            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                currentData.put(parts[0].trim(), parts[1].trim());
            }
        }

        processSection(currentSection, currentData, builder);
        return builder.build();
    }

    private void processSection(String section, Map<String, String> data, MissionBuilder builder) {
        if (section == null || data.isEmpty()) return;

        switch (section.toUpperCase()) {
            case "MISSION":
                builder.setMissionId(data.get("missionId"));
                builder.setDate(data.get("date"));
                builder.setLocation(data.get("location"));
                builder.setOutcome(data.get("outcome"));
                String cost = data.get("damageCost");
                if (cost != null) {
                    try {
                        builder.setDamageCost(Long.parseLong(cost));
                    } catch (NumberFormatException e) {}
                }
                break;

            case "CURSE":
                CurseComponent curse = new CurseComponent.Builder()
                        .name(data.get("name"))
                        .threatLevel(data.get("threatLevel"))
                        .build();
                builder.addComponent(curse);
                break;

            case "SORCERER":
                SorcererComponent sorcerer = new SorcererComponent.Builder()
                        .name(data.get("name"))
                        .rank(data.get("rank"))
                        .build();
                builder.addSorcerer(sorcerer);
                break;

            case "TECHNIQUE":
                TechniqueComponent technique = new TechniqueComponent.Builder()
                        .name(data.get("name"))
                        .type(data.get("type"))
                        .owner(data.get("owner"))
                        .damage(parseLong(data.get("damage")))
                        .build();
                builder.addTechnique(technique);
                break;

            case "ENVIRONMENT":
                EnvironmentComponent environment = new EnvironmentComponent.Builder()
                        .weather(data.get("weather"))
                        .timeOfDay(data.get("timeOfDay"))
                        .visibility(data.get("visibility"))
                        .cursedEnergyDensity(parseInt(data.get("cursedEnergyDensity")))
                        .build();
                builder.addComponent(environment);
                break;
        }
    }

    private long parseLong(String value) {
        if (value == null) return 0;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer parseInt(String value) {
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}