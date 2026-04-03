package mephi.main.mission;

import mephi.main.mission.components.OperationTimelineComponent;
import mephi.main.mission.components.SorcererComponent;
import mephi.main.mission.components.TechniqueComponent;
import mephi.main.mission.enums.Outcome;
import java.util.*;

public class Mission {

    private String missionId;
    private String date;
    private String location;
    private Outcome outcome;
    private long damageCost;
    private String note;

    private final List<SorcererComponent> sorcerers = new ArrayList<>();
    private final List<TechniqueComponent> techniques = new ArrayList<>();
    private final List<OperationTimelineComponent> timeline = new ArrayList<>();
    private final Map<String, MissionComponent> components = new HashMap<>();

    public Mission() {}

    public String getMissionId() { return missionId; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public Outcome getOutcome() { return outcome; }
    public long getDamageCost() { return damageCost; }
    public String getNote() { return note; }

    public List<SorcererComponent> getSorcerers() {
        return Collections.unmodifiableList(sorcerers);
    }

    public List<TechniqueComponent> getTechniques() {
        return Collections.unmodifiableList(techniques);
    }

    public List<OperationTimelineComponent> getTimeline() {
        return Collections.unmodifiableList(timeline);
    }

    public void setMissionId(String missionId) { this.missionId = missionId; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setOutcome(Outcome outcome) { this.outcome = outcome; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }
    public void setNote(String note) { this.note = note; }

    public void addSorcerer(SorcererComponent sorcerer) {
        this.sorcerers.add(sorcerer);
    }

    public void addTechnique(TechniqueComponent technique) {
        this.techniques.add(technique);
    }

    public void addTimelineEvent(OperationTimelineComponent event) {
        this.timeline.add(event);
    }

    public void addComponent(MissionComponent component) {
        this.components.put(component.getComponentType(), component);
    }

    @SuppressWarnings("unchecked")
    public <T extends MissionComponent> Optional<T> getComponent(Class<T> type) {
        return components.values().stream()
                .filter(type::isInstance)
                .map(c -> (T) c)
                .findFirst();
    }
}