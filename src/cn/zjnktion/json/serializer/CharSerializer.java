package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class CharSerializer implements JsonSerializer {

    public final static CharSerializer INSTANCE = new CharSerializer();

    private CharSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Character c = (Character) obj;
        writer.write(IConstants.CHAR_FIX);
        writer.write(c);
        writer.write(IConstants.CHAR_FIX);
    }
}
