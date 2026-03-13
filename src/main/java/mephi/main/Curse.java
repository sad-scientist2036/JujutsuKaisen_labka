package mephi.main;

public class Curse {
    private String name;
    private String threatLevel;
    private Technique technique;
    public Curse() {}
    public Curse(String name, String threatLevel) {
        this.name = name;
        this.threatLevel = threatLevel;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public Technique getTechnique() {
        return technique;
    }

    public void setTechnique(Technique technique) {
        this.technique = technique;
    }
}