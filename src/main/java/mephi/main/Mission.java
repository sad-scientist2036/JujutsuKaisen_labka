package mephi.main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Mission {
    private String missionId;
    private LocalDate date;
    private String location;
    private String outcome;
    private long damageCost;
    private String note;

    private ArrayList<Curse> curses;
    private ArrayList<Sorcerer> sorcerers;

    public Mission() {
        this.curses = new ArrayList<>();
        this.sorcerers = new ArrayList<>();
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public long getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(long damageCost) {
        this.damageCost = damageCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<Curse> getCurses() {
        return curses;
    }

    public void setCurses(ArrayList<Curse> curses) {
        this.curses = curses;
    }

    public ArrayList<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(ArrayList<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public void addCurse(Curse curse) {
        this.curses.add(curse);
    }

    public void addSorcerer(Sorcerer sorcerer) {
        this.sorcerers.add(sorcerer);
    }
}