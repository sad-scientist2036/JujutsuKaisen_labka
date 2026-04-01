package mephi.main.reader;

import mephi.main.mission.Mission;

import java.io.File;
import java.io.IOException;

public interface FileReader {
    public Mission read(File file) throws IOException;
}
