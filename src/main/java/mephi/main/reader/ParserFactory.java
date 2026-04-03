package mephi.main.reader;

import mephi.main.mission.Mission;
import mephi.main.reader.chain.*;
import mephi.main.reader.format.JSONReader;
import mephi.main.reader.format.TxtReader;
import mephi.main.reader.format.XMLReader;
import mephi.main.reader.format.YamlReader;

import java.io.File;
import java.io.IOException;

public class ParserFactory {

    private static final ParserHandler HANDLER_CHAIN;

    static {
        JsonHandler jsonHandler = new JsonHandler();
        XmlHandler xmlHandler = new XmlHandler();
        YamlHandler yamlHandler = new YamlHandler();
        LogHandler logHandler = new LogHandler();
        LegacyTxtHandler legacyTxtHandler = new LegacyTxtHandler();
        SectionTxtHandler sectionTxtHandler = new SectionTxtHandler();

        jsonHandler.setNext(xmlHandler);
        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(logHandler);
        logHandler.setNext(legacyTxtHandler);
        legacyTxtHandler.setNext(sectionTxtHandler);

        HANDLER_CHAIN = jsonHandler;
    }

    public static Mission parse(File file) throws IOException {
        return HANDLER_CHAIN.handle(file);
    }

    // Для обратной совместимости (если старый код использует createReader)
    public static FileReader createReader(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".json")) {
            return new JSONReader();
        } else if (fileName.endsWith(".xml")) {
            return new XMLReader();
        } else if (fileName.endsWith(".txt")) {
            return new TxtReader();
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return new YamlReader();
        } else {
            throw new IOException("Неподдерживаемый формат файла: " + fileName);
        }
    }
}