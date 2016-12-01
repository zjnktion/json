package cn.zjnktion.json.reader;

import cn.zjnktion.json.exception.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author zjnktion
 */
public class FirstReader implements JsonReader {

    private Reader reader;

    public FirstReader(String text) {
        reader = new StringReader(text);
    }

    @Override
    public int read() throws JsonParseException {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new JsonParseException("read string buffer error");
        }
    }
}
