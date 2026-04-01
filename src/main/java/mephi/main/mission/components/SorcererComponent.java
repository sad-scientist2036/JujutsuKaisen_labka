package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Leaf — компонент, хранящий информацию о маге-участнике.
 * Поддерживает список техник мага.
 */
public class SorcererComponent implements MissionComponent {
    private String name;
    private String rank;
    private List<TechniqueComponent> techniques = new ArrayList<>();

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
    public String getRank() { return rank; }
    public List<TechniqueComponent> getTechniques() { return techniques; }

    public void setName(String name) { this.name = name; }
    public void setRank(String rank) { this.rank = rank; }
    public void addTechnique(TechniqueComponent technique) { this.techniques.add(technique); }

    public static class Builder {
        private final SorcererComponent component = new SorcererComponent();

        public Builder name(String name) {
            component.name = name;
            return this;
        }

        public Builder rank(String rank) {
            component.rank = rank;
            return this;
        }

        public Builder addTechnique(TechniqueComponent technique) {
            component.techniques.add(technique);
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