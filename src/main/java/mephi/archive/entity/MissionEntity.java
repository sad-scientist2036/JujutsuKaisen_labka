package mephi.archive.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
public class MissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String missionId;

    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private String note;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Односторонние связи (только здесь храним ссылки)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curse_id")
    private CurseEntity curse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "economic_id")
    private EconomicEntity economic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enemy_activity_id")
    private EnemyActivityEntity enemyActivity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "environment_id")
    private EnvironmentEntity environment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "civilian_id")
    private CivilianEntity civilian;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private List<SorcererEntity> sorcerers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private List<TechniqueEntity> techniques = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private List<TimelineEventEntity> timelineEvents = new ArrayList<>();

    public MissionEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public CurseEntity getCurse() { return curse; }
    public void setCurse(CurseEntity curse) { this.curse = curse; }

    public EconomicEntity getEconomic() { return economic; }
    public void setEconomic(EconomicEntity economic) { this.economic = economic; }

    public EnemyActivityEntity getEnemyActivity() { return enemyActivity; }
    public void setEnemyActivity(EnemyActivityEntity enemyActivity) { this.enemyActivity = enemyActivity; }

    public EnvironmentEntity getEnvironment() { return environment; }
    public void setEnvironment(EnvironmentEntity environment) { this.environment = environment; }

    public CivilianEntity getCivilian() { return civilian; }
    public void setCivilian(CivilianEntity civilian) { this.civilian = civilian; }

    public List<SorcererEntity> getSorcerers() { return sorcerers; }
    public void setSorcerers(List<SorcererEntity> sorcerers) { this.sorcerers = sorcerers; }

    public List<TechniqueEntity> getTechniques() { return techniques; }
    public void setTechniques(List<TechniqueEntity> techniques) { this.techniques = techniques; }

    public List<TimelineEventEntity> getTimelineEvents() { return timelineEvents; }
    public void setTimelineEvents(List<TimelineEventEntity> timelineEvents) { this.timelineEvents = timelineEvents; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}