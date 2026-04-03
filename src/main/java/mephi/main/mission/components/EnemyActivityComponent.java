package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import mephi.main.mission.enums.BehaviorType;
import mephi.main.mission.enums.Mobility;
import mephi.main.mission.enums.EscalationRisk;
import java.util.ArrayList;
import java.util.List;

public class EnemyActivityComponent implements MissionComponent {
    private final BehaviorType behaviorType;
    private final List<String> targetPriority;
    private final List<String> attackPatterns;
    private final Mobility mobility;
    private final EscalationRisk escalationRisk;
    private final List<String> countermeasuresUsed;

    private EnemyActivityComponent(Builder builder) {
        this.behaviorType = builder.behaviorType;
        this.targetPriority = new ArrayList<>(builder.targetPriority);
        this.attackPatterns = new ArrayList<>(builder.attackPatterns);
        this.mobility = builder.mobility;
        this.escalationRisk = builder.escalationRisk;
        this.countermeasuresUsed = new ArrayList<>(builder.countermeasuresUsed);
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
        if (!targetPriority.isEmpty()) sb.append(", приоритеты=").append(targetPriority);
        if (!attackPatterns.isEmpty()) sb.append(", паттерны=").append(attackPatterns);
        if (!countermeasuresUsed.isEmpty()) sb.append(", контрмеры=").append(countermeasuresUsed);
        return sb.toString();
    }

    public BehaviorType getBehaviorType() { return behaviorType; }
    public List<String> getTargetPriority() { return targetPriority; }
    public List<String> getAttackPatterns() { return attackPatterns; }
    public Mobility getMobility() { return mobility; }
    public EscalationRisk getEscalationRisk() { return escalationRisk; }
    public List<String> getCountermeasuresUsed() { return countermeasuresUsed; }

    public static class Builder {
        private BehaviorType behaviorType;
        private List<String> targetPriority = new ArrayList<>();
        private List<String> attackPatterns = new ArrayList<>();
        private Mobility mobility;
        private EscalationRisk escalationRisk;
        private List<String> countermeasuresUsed = new ArrayList<>();

        public Builder behaviorType(String value) {
            this.behaviorType = BehaviorType.fromString(value);
            return this;
        }
        public Builder addTargetPriority(String value) { this.targetPriority.add(value); return this; }
        public Builder addAttackPattern(String value) { this.attackPatterns.add(value); return this; }
        public Builder mobility(String value) {
            this.mobility = Mobility.fromString(value);
            return this;
        }
        public Builder escalationRisk(String value) {
            this.escalationRisk = EscalationRisk.fromString(value);
            return this;
        }
        public Builder addCountermeasure(String value) { this.countermeasuresUsed.add(value); return this; }

        public EnemyActivityComponent build() {
            return new EnemyActivityComponent(this);
        }
    }
}