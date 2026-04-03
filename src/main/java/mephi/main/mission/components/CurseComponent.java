package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import mephi.main.mission.enums.ThreatLevel;

public class CurseComponent implements MissionComponent {
    private String name;
    private ThreatLevel threatLevel;

    public CurseComponent() {}

    @Override
    public String getComponentType() {
        return "curse";
    }

    @Override
    public String getInfo() {
        return "Проклятие: " + name + " (" + threatLevel + ")";
    }

    public String getName() { return name; }
    public ThreatLevel getThreatLevel() { return threatLevel; }

    public void setName(String name) { this.name = name; }
    public void setThreatLevel(ThreatLevel threatLevel) { this.threatLevel = threatLevel; }

    public static class Builder {
        private final CurseComponent component = new CurseComponent();

        public Builder name(String name) {
            component.name = name;
            return this;
        }

        public Builder threatLevel(String threatLevel) {
            component.threatLevel = ThreatLevel.fromString(threatLevel);
            return this;
        }

        public CurseComponent build() {
            if (component.name == null || component.name.isEmpty()) {
                throw new IllegalStateException("Curse name is required");
            }
            return component;
        }
    }
}