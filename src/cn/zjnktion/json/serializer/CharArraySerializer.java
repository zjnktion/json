package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * @author zjnktion
 */
public class CharArraySerializer implements JsonSerializer {

    public static final CharArraySerializer INSTANCE = new CharArraySerializer();

    private CharArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }
            writer.write(IConstants.CHAR_FIX);
            writer.write(Array.getChar(obj, i));
            writer.write(IConstants.CHAR_FIX);
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
