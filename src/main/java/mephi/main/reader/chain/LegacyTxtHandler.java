package mephi.main.reader.chain;

import mephi.main.mission.Mission;
import mephi.main.reader.format.TxtReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class LegacyTxtHandler extends ParserHandler {

    private final TxtReader reader = new TxtReader();

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
                if (line.contains(":") && !line.startsWith("[")) {
                    return true;
                }
                if (line.startsWith("[") && line.endsWith("]")) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}