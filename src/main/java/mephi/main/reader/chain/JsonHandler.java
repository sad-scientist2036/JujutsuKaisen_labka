package mephi.main.reader.chain;

import mephi.main.mission.Mission;
import mephi.main.reader.format.JSONReader;
import java.io.File;
import java.io.IOException;

public class JsonHandler extends ParserHandler {

    private final JSONReader reader = new JSONReader();

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
        String name = file.getName().toLowerCase();
        return name.endsWith(".json");
    }
}