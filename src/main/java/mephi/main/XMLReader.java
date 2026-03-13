package mephi.main;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.File;
import java.io.IOException;

public class XMLReader implements FileReader {

    private XmlMapper mapper;

    public XMLReader() {
        this.mapper = new XmlMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    @Override
    public Mission read(File file) throws IOException {
        return mapper.readValue(file, Mission.class);
    }
}