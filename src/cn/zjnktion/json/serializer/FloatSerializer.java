package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class FloatSerializer implements JsonSerializer {

    public final static FloatSerializer INSTANCE = new FloatSerializer();

    private FloatSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Number num = (Number) obj;
        Float f = num.floatValue();
        writer.write(f);
    }
}
