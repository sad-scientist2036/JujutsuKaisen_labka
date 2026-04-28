package mephi.archive.service;

import mephi.archive.entity.MissionEntity;
import mephi.archive.mapper.MissionMapper;
import mephi.archive.repository.MissionRepository;
import mephi.main.mission.Mission;
import mephi.main.reader.ParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MissionMapper missionMapper;

    public MissionEntity uploadMission(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("mission_", "_" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }

        Mission mission = ParserFactory.parse(tempFile);
        MissionEntity entity = missionMapper.toEntity(mission);
        MissionEntity saved = missionRepository.save(entity);

        tempFile.delete();
        return saved;
    }

    public MissionEntity getMission(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    public List<MissionEntity> getAllMissions() {
        return missionRepository.findAll();
    }

    public void delete(Long id) {
        missionRepository.deleteById(id);
    }
}