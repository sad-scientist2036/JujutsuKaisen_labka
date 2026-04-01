package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import java.util.ArrayList;
import java.util.List;

public class EnemyActivityComponent implements MissionComponent {
    private final String behaviorType;
    private final List<String> attackPatterns;
    private final String mobility;
    private final String escalationRisk;

    private EnemyActivityComponent(Builder builder) {
        this.behaviorType = builder.behaviorType;
        this.attackPatterns = new ArrayList<>(builder.attackPatterns);
        this.mobility = builder.mobility;
        this.escalationRisk = builder.escalationRisk;
    }

    @Override
    public String getComponentType() {
        return "enemy";
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder("Активность врага: ");
        if (behaviorType != null) sb.append("тип=").append(behaviorType);
        if (mobility != null) sb.append(", мобильность=").append(mobility);
        if (escalationRisk != null) sb.append(", риск=").append(escalationRisk);
        if (!attackPatterns.isEmpty()) sb.append(", паттерны=").append(attackPatterns);
        return sb.toString();
    }

    public String getBehaviorType() { return behaviorType; }
    public List<String> getAttackPatterns() { return attackPatterns; }
    public String getMobility() { return mobility; }
    public String getEscalationRisk() { return escalationRisk; }

    public static class Builder {
        private String behaviorType;
        private List<String> attackPatterns = new ArrayList<>();
        private String mobility;
        private String escalationRisk;

        public Builder behaviorType(String value) { this.behaviorType = value; return this; }
        public Builder addAttackPattern(String value) { this.attackPatterns.add(value); return this; }
        public Builder mobility(String value) { this.mobility = value; return this; }
        public Builder escalationRisk(String value) { this.escalationRisk = value; return this; }

        public EnemyActivityComponent build() {
            return new EnemyActivityComponent(this);
        }
    }
}