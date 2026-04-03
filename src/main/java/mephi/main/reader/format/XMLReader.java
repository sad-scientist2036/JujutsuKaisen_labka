package mephi.main.reader.format;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.JsonNode;
import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;

public class XMLReader implements FileReader {

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public Mission read(File file) throws IOException {
        JsonNode root = mapper.readTree(file);

        MissionBuilder builder = new DefaultMissionBuilder();

        builder.setMissionId(getText(root, "missionId"))
                .setDate(getText(root, "date"))
                .setLocation(getText(root, "location"))
                .setOutcome(getText(root, "outcome"))
                .setDamageCost(getLong(root, "damageCost"));

        JsonNode curseNode = root.get("curse");
        if (curseNode != null && !curseNode.isNull()) {
            CurseComponent curse = new CurseComponent.Builder()
                    .name(getText(curseNode, "name"))
                    .threatLevel(getText(curseNode, "threatLevel"))
                    .build();
            builder.addComponent(curse);
        }

        JsonNode sorcerersNode = root.get("sorcerers");
        if (sorcerersNode != null && !sorcerersNode.isNull()) {
            JsonNode sorcererArray = sorcerersNode.get("sorcerer");
            if (sorcererArray != null) {
                if (sorcererArray.isArray()) {
                    for (JsonNode sNode : sorcererArray) {
                        addSorcerer(builder, sNode);
                    }
                } else if (sorcererArray.isObject()) {
                    addSorcerer(builder, sorcererArray);
                }
            } else {
                addSorcerer(builder, sorcerersNode);
            }
        }

        JsonNode techniquesNode = root.get("techniques");
        if (techniquesNode != null && !techniquesNode.isNull()) {
            JsonNode techniqueArray = techniquesNode.get("technique");
            if (techniqueArray != null) {
                if (techniqueArray.isArray()) {
                    for (JsonNode tNode : techniqueArray) {
                        addTechnique(builder, tNode);
                    }
                } else if (techniqueArray.isObject()) {
                    addTechnique(builder, techniqueArray);
                }
            } else {
                addTechnique(builder, techniquesNode);
            }
        }

        JsonNode enemyNode = root.get("enemyActivity");
        if (enemyNode != null && !enemyNode.isNull()) {
            EnemyActivityComponent.Builder enemyBuilder = new EnemyActivityComponent.Builder()
                    .behaviorType(getText(enemyNode, "behaviorType"))
                    .mobility(getText(enemyNode, "mobility"))
                    .escalationRisk(getText(enemyNode, "escalationRisk"));

            JsonNode targetPriorityNode = enemyNode.get("targetPriority");
            if (targetPriorityNode != null) {
                if (targetPriorityNode.isArray()) {
                    for (JsonNode tp : targetPriorityNode) {
                        String value = tp.asText();
                        if (value != null && !value.isEmpty()) {
                            enemyBuilder.addTargetPriority(value);
                        }
                    }
                } else {
                    String value = targetPriorityNode.asText();
                    if (value != null && !value.isEmpty()) {
                        enemyBuilder.addTargetPriority(value);
                    }
                }
            }

            JsonNode patternsNode = enemyNode.get("attackPatterns");
            if (patternsNode != null) {
                JsonNode patternNode = patternsNode.get("pattern");
                if (patternNode != null) {
                    if (patternNode.isArray()) {
                        for (JsonNode p : patternNode) {
                            String pattern = p.asText();
                            if (pattern != null && !pattern.isEmpty()) {
                                enemyBuilder.addAttackPattern(pattern);
                            }
                        }
                    } else {
                        String pattern = patternNode.asText();
                        if (pattern != null && !pattern.isEmpty()) {
                            enemyBuilder.addAttackPattern(pattern);
                        }
                    }
                }
            }

            JsonNode countermeasuresNode = enemyNode.get("countermeasuresUsed");
            if (countermeasuresNode != null) {
                JsonNode measureNode = countermeasuresNode.get("measure");
                if (measureNode != null) {
                    if (measureNode.isArray()) {
                        for (JsonNode m : measureNode) {
                            String measure = m.asText();
                            if (measure != null && !measure.isEmpty()) {
                                enemyBuilder.addCountermeasure(measure);
                            }
                        }
                    } else {
                        String measure = measureNode.asText();
                        if (measure != null && !measure.isEmpty()) {
                            enemyBuilder.addCountermeasure(measure);
                        }
                    }
                }
            }

            builder.addComponent(enemyBuilder.build());
        }

        JsonNode economicNode = root.get("economicAssessment");
        if (economicNode != null && !economicNode.isNull()) {
            EconomicComponent economic = new EconomicComponent.Builder()
                    .totalDamageCost(getLongOrNull(economicNode, "totalDamageCost"))
                    .infrastructureDamage(getLongOrNull(economicNode, "infrastructureDamage"))
                    .commercialDamage(getLongOrNull(economicNode, "commercialDamage"))
                    .transportDamage(getLongOrNull(economicNode, "transportDamage"))
                    .recoveryEstimateDays(getIntOrNull(economicNode, "recoveryEstimateDays"))
                    .insuranceCovered(getBooleanOrNull(economicNode, "insuranceCovered"))
                    .build();
            builder.addComponent(economic);
        }

        JsonNode environmentNode = root.get("environmentConditions");
        if (environmentNode != null && !environmentNode.isNull()) {
            EnvironmentComponent environment = new EnvironmentComponent.Builder()
                    .weather(getText(environmentNode, "weather"))
                    .timeOfDay(getText(environmentNode, "timeOfDay"))
                    .visibility(getText(environmentNode, "visibility"))
                    .cursedEnergyDensity(getIntOrNull(environmentNode, "cursedEnergyDensity"))
                    .build();
            builder.addComponent(environment);
        }
        String note = getText(root, "note");
        if (note == null || note.isEmpty()) {
            note = getText(root, "comment");
        }
        if (note != null && !note.isEmpty()) {
            builder.setNote(note);
        }

        return builder.build();
    }

    private void addSorcerer(MissionBuilder builder, JsonNode node) {
        String name = getText(node, "name");
        if (name == null || name.isEmpty()) {
            System.err.println("Warning: Skipping sorcerer without name");
            return;
        }
        String rank = getText(node, "rank");
        SorcererComponent sorcerer = new SorcererComponent.Builder()
                .name(name)
                .rank(rank != null ? rank : "UNKNOWN")
                .build();
        builder.addSorcerer(sorcerer);
    }

    private void addTechnique(MissionBuilder builder, JsonNode node) {
        String name = getText(node, "name");
        if (name == null || name.isEmpty()) {
            System.err.println("Warning: Skipping technique without name");
            return;
        }
        TechniqueComponent technique = new TechniqueComponent.Builder()
                .name(name)
                .type(getText(node, "type"))
                .owner(getText(node, "owner"))
                .damage(getLong(node, "damage"))
                .build();
        builder.addTechnique(technique);
    }

    private String getText(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return f != null && !f.isNull() ? f.asText() : null;
    }

    private long getLong(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return f != null && !f.isNull() ? f.asLong() : 0L;
    }

    private Long getLongOrNull(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return (f != null && !f.isNull()) ? f.asLong() : null;
    }

    private Integer getIntOrNull(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return (f != null && !f.isNull()) ? f.asInt() : null;
    }

    private Boolean getBooleanOrNull(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return (f != null && !f.isNull()) ? f.asBoolean() : null;
    }
}