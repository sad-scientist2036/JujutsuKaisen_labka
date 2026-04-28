package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import mephi.main.mission.enums.SorcererRank;
import java.util.ArrayList;
import java.util.List;

public class SorcererComponent implements MissionComponent {
    private String name;
    private SorcererRank rank;
    private final List<TechniqueComponent> techniques = new ArrayList<>();

    public SorcererComponent() {}

    @Override
    public String getComponentType() {
        return "sorcerer";
    }

    @Override
    public String getInfo() {
        return "Маг: " + name + " (" + rank + "), техник: " + techniques.size();
    }

    public String getName() { return name; }
    public SorcererRank getRank() { return rank; }
    public List<TechniqueComponent> getTechniques() { return techniques; }

    public void setName(String name) { this.name = name; }
    public void setRank(SorcererRank rank) { this.rank = rank; }
    public static class Builder {
        private final SorcererComponent component = new SorcererComponent();
        public Builder name(String name) {
            component.name = name;
            return this;
        }

        public Builder rank(String rank) {
            component.rank = SorcererRank.fromString(rank);
            return this;
        }
        public SorcererComponent build() {
            if (component.name == null || component.name.isEmpty()) {
                throw new IllegalStateException("Sorcerer name is required");
            }
            return component;
        }
    }
}