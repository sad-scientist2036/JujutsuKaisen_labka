package mephi.main.reader.chain;

import mephi.main.mission.Mission;
import mephi.main.reader.format.XMLReader;
import java.io.File;
import java.io.IOException;

public class XmlHandler extends ParserHandler {

    private final XMLReader reader = new XMLReader();

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
        return name.endsWith(".xml");
    }
}