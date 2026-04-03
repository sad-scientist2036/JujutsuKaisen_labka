package mephi.main.reader.chain;

import mephi.main.mission.Mission;
import mephi.main.reader.format.LogReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class LogHandler extends ParserHandler {

    private final LogReader reader = new LogReader();

    @Override
    public Mission handle(File file) throws IOException {
        if (canHandle(file)) {
            return reader.read(file);
        }
        if (next != null) {
            return next.handle(file);
        }
        throw new IOException("Не удалось распознать формат файла: " + file.getName());
    }

    @Override
    protected boolean canHandle(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("MISSION_CREATED|")) {
                    return true;
                }
                if (line.startsWith("[") || line.contains(":")) {
                    return false;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}