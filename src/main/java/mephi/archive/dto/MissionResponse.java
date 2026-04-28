package mephi.archive.dto;

public class MissionResponse {
    private Long id;
    private String missionId;
    private String error;

    public MissionResponse(Long id, String missionId) {
        this.id = id;
        this.missionId = missionId;
    }

    public MissionResponse(String error) {
        this.error = error;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}