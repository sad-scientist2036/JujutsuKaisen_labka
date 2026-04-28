package mephi.archive.controller;

import mephi.archive.dto.MissionResponse;
import mephi.archive.entity.MissionEntity;
import mephi.archive.report.ReportType;
import mephi.archive.service.MissionService;
import mephi.archive.service.ReportService;
import mephi.archive.mapper.MissionMapper;
import mephi.main.mission.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MissionMapper missionMapper;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MissionResponse> uploadMission(
            @RequestParam("file") MultipartFile file) {
        try {
            MissionEntity saved = missionService.uploadMission(file);
            return ResponseEntity.ok(new MissionResponse(saved.getId(), saved.getMissionId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MissionResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<String> getReport(
            @PathVariable Long id,
            @RequestParam(defaultValue = "simple") String type) {

        MissionEntity entity = missionService.getMission(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }

        ReportType reportType = ReportType.fromString(type);
        Mission mission = missionMapper.toModel(entity);
        String report = reportService.generateReport(mission, reportType);

        return ResponseEntity.ok(report);
    }

    @GetMapping
    public ResponseEntity<List<MissionEntity>> getAllMissions() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionEntity> getMission(@PathVariable Long id) {
        MissionEntity entity = missionService.getMission(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}