package mephi.main.reader.format;

import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.CurseComponent;
import mephi.main.mission.components.SorcererComponent;
import mephi.main.mission.components.TechniqueComponent;
import mephi.main.mission.enums.SorcererRank;
import mephi.main.mission.enums.TechniqueType;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtReader implements FileReader {

    @Override
    public Mission read(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        MissionBuilder builder = new DefaultMissionBuilder();
        Map<String, SorcererComponent> sorcerersMap = new HashMap<>();
        Map<String, TechniqueComponent> techniquesMap = new HashMap<>();
        String currentSorcererKey = null;
        String currentTechniqueKey = null;

        String curseName = null;
        String curseThreatLevel = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(":", 2);
            if (parts.length < 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "missionId":
                    builder.setMissionId(value);
                    continue;
                case "date":
                    builder.setDate(value);
                    continue;
                case "location":
                    builder.setLocation(value);
                    continue;
                case "outcome":
                    builder.setOutcome(value);
                    continue;
                case "damageCost":
                    try {
                        builder.setDamageCost(Long.parseLong(value));
                    } catch (NumberFormatException e) {}
                    continue;
                case "curse.name":
                    curseName = value;
                    continue;
                case "curse.threatLevel":
                    curseThreatLevel = value;
                    continue;
                case "note":
                    builder.setNote(value);
                    continue;
            }

            if (key.startsWith("sorcerer[")) {
                if (key.endsWith(".name")) {
                    currentSorcererKey = key.substring(0, key.indexOf(".name"));
                    SorcererComponent s = new SorcererComponent.Builder()
                            .name(value)
                            .rank("UNKNOWN")
                            .build();
                    sorcerersMap.put(currentSorcererKey, s);
                } else if (key.endsWith(".rank") && currentSorcererKey != null) {
                    SorcererComponent s = sorcerersMap.get(currentSorcererKey);
                    if (s != null) {
                        s.setRank(SorcererRank.fromString(value));
                    }
                }
                continue;
            }

            if (key.startsWith("technique[")) {
                if (key.endsWith(".name")) {
                    currentTechniqueKey = key.substring(0, key.indexOf(".name"));
                    TechniqueComponent t = new TechniqueComponent.Builder()
                            .name(value)
                            .type("UNKNOWN")
                            .damage(0)
                            .build();
                    techniquesMap.put(currentTechniqueKey, t);
                } else if (key.endsWith(".type") && currentTechniqueKey != null) {
                    TechniqueComponent t = techniquesMap.get(currentTechniqueKey);
                    if (t != null) {
                        t.setType(TechniqueType.fromString(value));
                    }
                } else if (key.endsWith(".damage") && currentTechniqueKey != null) {
                    TechniqueComponent t = techniquesMap.get(currentTechniqueKey);
                    if (t != null) {
                        try {
                            t.setDamage(Long.parseLong(value));
                        } catch (NumberFormatException e) {}
                    }
                } else if (key.endsWith(".owner") && currentTechniqueKey != null) {
                    String ownerName = value;
                    TechniqueComponent t = techniquesMap.get(currentTechniqueKey);
                    if (t != null) {
                        t.setOwner(ownerName);
                    }
                }
            }
        }

        if (curseName != null) {
            CurseComponent curse = new CurseComponent.Builder()
                    .name(curseName)
                    .threatLevel(curseThreatLevel != null ? curseThreatLevel : "UNKNOWN")
                    .build();
            builder.addComponent(curse);
        }

        for (SorcererComponent s : sorcerersMap.values()) {
            builder.addSorcerer(s);
        }

        for (TechniqueComponent t : techniquesMap.values()) {
            builder.addTechnique(t);
        }

        return builder.build();
    }
}