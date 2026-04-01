package mephi.main.mission;

import mephi.main.mission.components.SorcererComponent;
import mephi.main.mission.components.TechniqueComponent;
import java.util.*;

public class Mission {
    // Core
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;

    // Коллекции
    private final List<SorcererComponent> sorcerers = new ArrayList<>();
    private final List<TechniqueComponent> techniques = new ArrayList<>();
    private final Map<String, MissionComponent> components = new HashMap<>();

    // ПУБЛИЧНЫЙ конструктор — для Jackson
    public Mission() {}

    // ПУБЛИЧНЫЕ геттеры
    public String getMissionId() { return missionId; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getOutcome() { return outcome; }
    public long getDamageCost() { return damageCost; }

    public List<SorcererComponent> getSorcerers() {
        return Collections.unmodifiableList(sorcerers);
    }

    public List<TechniqueComponent> getTechniques() {
        return Collections.unmodifiableList(techniques);
    }

    public void setMissionId(String missionId) { this.missionId = missionId; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }

    public void addSorcerer(SorcererComponent sorcerer) {
        this.sorcerers.add(sorcerer);
    }

    public void addTechnique(TechniqueComponent technique) {
        this.techniques.add(technique);
    }

    public void addComponent(MissionComponent component) {
        this.components.put(component.getComponentType(), component);
    }

    public Optional<MissionComponent> getComponent(String type) {
        return Optional.ofNullable(components.get(type));
    }

    @SuppressWarnings("unchecked")
    public <T extends MissionComponent> Optional<T> getComponent(Class<T> type) {
        return components.values().stream()
                .filter(type::isInstance)
                .map(c -> (T) c)
                .findFirst();
    }
    public Collection<MissionComponent> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }
}