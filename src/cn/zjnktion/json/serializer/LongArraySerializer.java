package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * @author zjnktion
 */
public class LongArraySerializer implements JsonSerializer {

    public static final LongArraySerializer INSTANCE = new LongArraySerializer();

    private LongArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }
            writer.write(Array.getLong(obj, i));
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
