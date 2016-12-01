package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class ShortSerializer implements JsonSerializer {

    public final static ShortSerializer INSTANCE = new ShortSerializer();

    private ShortSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Number num = (Number) obj;
        Short s = num.shortValue();
        writer.write(s);
    }
}
