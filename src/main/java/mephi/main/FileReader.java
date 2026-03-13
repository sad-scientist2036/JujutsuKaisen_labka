package mephi.main;

import java.io.File;
import java.io.IOException;

public interface FileReader {
    public Mission read(File file) throws IOException;
}
