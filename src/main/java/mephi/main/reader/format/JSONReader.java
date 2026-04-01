package mephi.main.reader.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mephi.main.mission.Mission;
import mephi.main.mission.builder.DefaultMissionBuilder;
import mephi.main.mission.builder.MissionBuilder;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import java.io.File;
import java.io.IOException;

public class JSONReader implements FileReader {

    private final ObjectMapper mapper = new ObjectMapper();

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
                String ownerName = getText(tNode, "owner");
            }
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