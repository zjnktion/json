package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class BooleanSerializer implements JsonSerializer {

    public static final BooleanSerializer INSTANCE = new BooleanSerializer();

    private BooleanSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Boolean b = (Boolean) obj;
        writer.write(b);
    }
}
