package mephi.main.reader.chain;

import mephi.main.mission.Mission;
import java.io.File;
import java.io.IOException;

public abstract class ParserHandler {

    protected ParserHandler next;

    public void setNext(ParserHandler handler) {
        this.next = handler;
    }

    public abstract Mission handle(File file) throws IOException;

    protected boolean canHandle(File file) {
        return false;
    }
}