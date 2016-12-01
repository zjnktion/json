package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.Context;
import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * @author zjnktion
 */
public class ReferenceArraySerializer implements JsonSerializer {

    public static final ReferenceArraySerializer INSTANCE = new ReferenceArraySerializer();

    private ReferenceArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }

            Object o = Array.get(obj, i);
            if (o == null) {
                writer.writeNull();
            }
            else {
                JsonSerializer serializer = Context.getSerializer(o.getClass());
                serializer.serialize(writer, o);
            }
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
