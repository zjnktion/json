package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.Context;
import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.util.Iterator;
import java.util.Map;

/**
 * @author zjnktion
 */
public class MapSerializer implements JsonSerializer {

    public static final MapSerializer INSTANCE = new MapSerializer();

    private MapSerializer() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.OBJ_PREFIX);

        Map map = (Map) obj;

        if (map != null && !map.isEmpty()) {
            Iterator<Map.Entry> iterator = map.entrySet().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();

                if (i > 0) {
                    writer.write(IConstants.ELEMENT_SEPARATOR);
                }

                Object key = entry.getKey();
                if (key == null) {
                    writer.writeNull();
                }
                else {
                    JsonSerializer serializer = Context.getSerializer(key.getClass());
                    serializer.serialize(writer, key);
                }

                writer.write(IConstants.KV_SEPARATOR);

                Object value = entry.getValue();
                if (value == null) {
                    writer.writeNull();
                }
                else {
                    JsonSerializer serializer = Context.getSerializer(value.getClass());
                    serializer.serialize(writer, value);
                }

                i++;
            }
        }

        writer.write(IConstants.OBJ_SUFFIX);
    }
}
