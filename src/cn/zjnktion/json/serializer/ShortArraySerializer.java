package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * @author zjnktion
 */
public class ShortArraySerializer implements JsonSerializer {

    public static final ShortArraySerializer INSTANCE = new ShortArraySerializer();

    private ShortArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }
            writer.write(Array.getShort(obj, i));
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
