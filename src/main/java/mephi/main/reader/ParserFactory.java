package mephi.main.reader;

import mephi.main.reader.format.*;
import java.io.File;
import java.io.IOException;

public class ParserFactory {

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