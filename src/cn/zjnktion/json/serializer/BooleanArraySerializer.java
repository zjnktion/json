package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * @author zjnktion
 */
public class BooleanArraySerializer implements JsonSerializer {

    public static final BooleanArraySerializer INSTANCE = new BooleanArraySerializer();

    private BooleanArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write("[");

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }
            writer.write(Array.getBoolean(obj, i));
        }

        writer.write("]");
    }
}
