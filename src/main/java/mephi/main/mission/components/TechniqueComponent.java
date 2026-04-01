package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;

/**
 * Leaf — компонент, хранящий информацию о технике.
 */
public class TechniqueComponent implements MissionComponent {
    private String name;
    private String type;
    private String owner;
    private long damage;

    public TechniqueComponent() {}

    @Override
    public String getComponentType() {
        return "technique";
    }

    @Override
    public String getInfo() {
        return "Техника: " + name + " (" + type + "), владелец: " + owner + ", урон: " + damage;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getOwner() { return owner; }
    public long getDamage() { return damage; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setDamage(long damage) { this.damage = damage; }

    public static class Builder {
        private final TechniqueComponent component = new TechniqueComponent();

        public Builder name(String name) {
            component.name = name;
            return this;
        }

        public Builder type(String type) {
            component.type = type;
            return this;
        }

        public Builder owner(String owner) {
            component.owner = owner;
            return this;
        }

        public Builder damage(long damage) {
            component.damage = damage;
            return this;
        }

        public TechniqueComponent build() {
            if (component.name == null || component.name.isEmpty()) {
                throw new IllegalStateException("Technique name is required");
            }
            return component;
        }
    }
}