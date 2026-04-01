package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;

public class CurseComponent implements MissionComponent {
    private String name;
    private String threatLevel;

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
    public String getThreatLevel() { return threatLevel; }

    public void setName(String name) { this.name = name; }
    public void setThreatLevel(String threatLevel) { this.threatLevel = threatLevel; }

    public static class Builder {
        private final CurseComponent component = new CurseComponent();

        public Builder name(String name) {
            component.name = name;
            return this;
        }

        public Builder threatLevel(String threatLevel) {
            component.threatLevel = threatLevel;
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