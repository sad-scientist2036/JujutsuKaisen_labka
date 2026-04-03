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
}