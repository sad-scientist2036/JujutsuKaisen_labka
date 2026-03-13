package mephi.main;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private String note;

    private List<Curse> curse;
    private List<Sorcerer> sorcerer;
    private List<Technique> technique;

    public Mission() {
        this.curse = new ArrayList<>();
        this.sorcerer = new ArrayList<>();
        this.technique = new ArrayList<>();
    }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public long getDamageCost() { return damageCost; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }


    @JsonAlias("comment")
    public String getNote() { return note; }

    @JsonAlias("comment")
    public void setNote(String note) { this.note = note; }

    public List<Curse> getCurse() { return curse; }

    @JsonSetter("curse")
    @JsonAlias("curses")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "curse")
    public void setCurse(List<Curse> curse) { this.curse = curse; }

    public List<Sorcerer> getSorcerer() { return sorcerer; }

    @JsonSetter("sorcerer")
    @JsonAlias("sorcerers")
    @JacksonXmlElementWrapper(localName = "sorcerers")
    @JacksonXmlProperty(localName = "sorcerer")
    public void setSorcerer(List<Sorcerer> sorcerer) { this.sorcerer = sorcerer; }

    public List<Technique> getTechnique() { return technique; }

    @JsonSetter("technique")
    @JsonAlias("techniques")
    @JacksonXmlElementWrapper(localName = "techniques")
    @JacksonXmlProperty(localName = "technique")
    public void setTechnique(List<Technique> technique) { this.technique = technique; }

    public void addCurse(Curse curse) { this.curse.add(curse); }
    public void addSorcerer(Sorcerer sorcerer) { this.sorcerer.add(sorcerer); }
    public void addTechnique(Technique technique) { this.technique.add(technique); }
}