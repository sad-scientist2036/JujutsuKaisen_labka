package mephi.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtReader implements FileReader {

    public TxtReader() {}

    @Override
    public Mission read(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        Mission mission = new Mission();

        Map<String, Sorcerer> sorcerers = new HashMap<>();
        Map<String, Technique> techniques = new HashMap<>();
        String currentSorcererKey = null;
        String currentTechniqueKey = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(":", 2);
            if (parts.length < 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            if (key.equals("missionId")) {
                mission.setMissionId(value);
            }
            else if (key.equals("date")) {
                mission.setDate(value);
            }
            else if (key.equals("location")) {
                mission.setLocation(value);
            }
            else if (key.equals("outcome")) {
                mission.setOutcome(value);
            }
            else if (key.equals("damageCost")) {
                mission.setDamageCost(Long.parseLong(value));
            }
            else if (key.equals("note")) {
                mission.setNote(value);
            }
            else if (key.equals("curse.name")) {
                Curse curse = new Curse(value, "");
                mission.addCurse(curse);
            }
            else if (key.equals("curse.threatLevel")) {
                if (mission.getCurse() != null && !mission.getCurse().isEmpty()) {
                    mission.getCurse().get(mission.getCurse().size() - 1).setThreatLevel(value);
                }
            }
            else if (key.startsWith("sorcerer[")) {
                if (key.endsWith(".name")) {
                    currentSorcererKey = key.substring(0, key.indexOf(".name"));
                    Sorcerer s = new Sorcerer(value, "");
                    sorcerers.put(currentSorcererKey, s);
                }
                else if (key.endsWith(".rank") && currentSorcererKey != null) {
                    Sorcerer s = sorcerers.get(currentSorcererKey);
                    if (s != null) {
                        s.setRank(value);
                    }
                }
            }
            else if (key.startsWith("technique[")) {
                if (key.endsWith(".name")) {
                    currentTechniqueKey = key.substring(0, key.indexOf(".name"));
                    Technique t = new Technique();
                    t.setName(value);
                    techniques.put(currentTechniqueKey, t);
                }
                else if (key.endsWith(".type") && currentTechniqueKey != null) {
                    Technique t = techniques.get(currentTechniqueKey);
                    if (t != null) {
                        t.setType(value);
                    }
                }
                else if (key.endsWith(".damage") && currentTechniqueKey != null) {
                    Technique t = techniques.get(currentTechniqueKey);
                    if (t != null) {
                        t.setDamage(Long.parseLong(value));
                    }
                }
                else if (key.endsWith(".owner") && currentTechniqueKey != null) {
                    String ownerName = value;
                    Technique t = techniques.get(currentTechniqueKey);
                    if (t != null) {
                        for (Sorcerer s : sorcerers.values()) {
                            if (s.getName().equals(ownerName)) {
                                s.setTechnique(t);
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (Sorcerer s : sorcerers.values()) {
            mission.addSorcerer(s);
        }

        for (Technique t : techniques.values()) {
            mission.addTechnique(t);
        }

        return mission;
    }
}