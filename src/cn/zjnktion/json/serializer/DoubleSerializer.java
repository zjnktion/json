package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class DoubleSerializer implements JsonSerializer {

    public static final DoubleSerializer INSTANCE = new DoubleSerializer();

    private DoubleSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Number num = (Number) obj;
        Double d = num.doubleValue();
        writer.write(d);
    }
}
