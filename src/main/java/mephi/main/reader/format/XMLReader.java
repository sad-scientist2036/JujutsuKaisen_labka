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
        JsonNode testNode = root.get("sorcerers");
        if (testNode != null) {
            System.out.println("sorcerers node type: " + testNode.getNodeType());
            System.out.println("sorcerers node: " + testNode);
        }

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

            if (sorcerersNode.isArray()) {
                for (JsonNode sNode : sorcerersNode) {
                    addSorcerer(builder, sNode);
                }
            }
            else if (sorcerersNode.has("sorcerer")) {
                JsonNode sorcererField = sorcerersNode.get("sorcerer");
                if (sorcererField.isArray()) {
                    for (JsonNode sNode : sorcererField) {
                        addSorcerer(builder, sNode);
                    }
                } else {
                    addSorcerer(builder, sorcererField);
                }
            }
            else if (sorcerersNode.has("name")) {
                addSorcerer(builder, sorcerersNode);
            }
        }

        JsonNode techniquesNode = root.get("techniques");
        if (techniquesNode != null && !techniquesNode.isNull()) {
            if (techniquesNode.isArray()) {
                for (JsonNode tNode : techniquesNode) {
                    addTechnique(builder, tNode);
                }
            } else if (techniquesNode.has("technique")) {
                JsonNode techniqueField = techniquesNode.get("technique");
                if (techniqueField.isArray()) {
                    for (JsonNode tNode : techniqueField) {
                        addTechnique(builder, tNode);
                    }
                } else {
                    addTechnique(builder, techniqueField);
                }
            } else if (techniquesNode.has("name")) {
                addTechnique(builder, techniquesNode);
            }
        }

        JsonNode enemyNode = root.get("enemyActivity");
        if (enemyNode != null && !enemyNode.isNull()) {
            EnemyActivityComponent.Builder enemyBuilder = new EnemyActivityComponent.Builder()
                    .behaviorType(getText(enemyNode, "behaviorType"))
                    .mobility(getText(enemyNode, "mobility"))
                    .escalationRisk(getText(enemyNode, "escalationRisk"));

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

            builder.addComponent(enemyBuilder.build());
        }

        return builder.build();
    }

    private void addSorcerer(MissionBuilder builder, JsonNode node) {
        String name = getText(node, "name");
        if (name == null || name.isEmpty()) {
            System.err.println("Warning: Skipping sorcerer without name. Node: " + node);
            return;
        }

        String rank = getText(node, "rank");
        SorcererComponent sorcerer = new SorcererComponent.Builder()
                .name(name)
                .rank(rank != null ? rank : "UNKNOWN")
                .build();
        builder.addSorcerer(sorcerer);
        System.out.println("Added sorcerer: " + name);
    }

    private void addTechnique(MissionBuilder builder, JsonNode node) {
        String name = getText(node, "name");
        if (name == null || name.isEmpty()) {
            System.err.println("Warning: Skipping technique without name. Node: " + node);
            return;
        }

        TechniqueComponent technique = new TechniqueComponent.Builder()
                .name(name)
                .type(getText(node, "type"))
                .owner(getText(node, "owner"))
                .damage(getLong(node, "damage"))
                .build();
        builder.addTechnique(technique);
        System.out.println("Added technique: " + name);
    }

    private String getText(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return f != null && !f.isNull() ? f.asText() : null;
    }

    private long getLong(JsonNode node, String field) {
        JsonNode f = node.get(field);
        return f != null && !f.isNull() ? f.asLong() : 0L;
    }
}