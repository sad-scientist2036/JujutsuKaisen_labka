package mephi.main.reader.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;

public class YamlReader implements FileReader {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

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
        if (sorcerersNode != null && sorcerersNode.isArray()) {
            for (JsonNode sNode : sorcerersNode) {
                SorcererComponent sorcerer = new SorcererComponent.Builder()
                        .name(getText(sNode, "name"))
                        .rank(getText(sNode, "rank"))
                        .build();
                builder.addSorcerer(sorcerer);
            }
        } else if (sorcerersNode != null && sorcerersNode.isObject()) {
            SorcererComponent sorcerer = new SorcererComponent.Builder()
                    .name(getText(sorcerersNode, "name"))
                    .rank(getText(sorcerersNode, "rank"))
                    .build();
            builder.addSorcerer(sorcerer);
        }

        JsonNode techniquesNode = root.get("techniques");
        if (techniquesNode != null && techniquesNode.isArray()) {
            for (JsonNode tNode : techniquesNode) {
                TechniqueComponent technique = new TechniqueComponent.Builder()
                        .name(getText(tNode, "name"))
                        .type(getText(tNode, "type"))
                        .owner(getText(tNode, "owner"))
                        .damage(getLong(tNode, "damage"))
                        .build();
                builder.addTechnique(technique);
            }
        } else if (techniquesNode != null && techniquesNode.isObject()) {
            TechniqueComponent technique = new TechniqueComponent.Builder()
                    .name(getText(techniquesNode, "name"))
                    .type(getText(techniquesNode, "type"))
                    .owner(getText(techniquesNode, "owner"))
                    .damage(getLong(techniquesNode, "damage"))
                    .build();
            builder.addTechnique(technique);
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

        JsonNode enemyNode = root.get("enemyActivity");
        if (enemyNode != null && !enemyNode.isNull()) {
            EnemyActivityComponent.Builder enemyBuilder = new EnemyActivityComponent.Builder()
                    .behaviorType(getText(enemyNode, "behaviorType"))
                    .mobility(getText(enemyNode, "mobility"))
                    .escalationRisk(getText(enemyNode, "escalationRisk"));

            JsonNode patternsNode = enemyNode.get("attackPatterns");
            if (patternsNode != null) {
                if (patternsNode.isArray()) {
                    for (JsonNode p : patternsNode) {
                        String pattern = p.asText();
                        if (pattern != null && !pattern.isEmpty()) {
                            enemyBuilder.addAttackPattern(pattern);
                        }
                    }
                } else if (patternsNode.isObject()) {
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
            }

            builder.addComponent(enemyBuilder.build());
        }

        return builder.build();
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