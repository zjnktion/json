package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class LongSerializer implements JsonSerializer {

    public final static LongSerializer INSTANCE = new LongSerializer();

    private LongSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Number num = (Number) obj;
        Long l = num.longValue();
        writer.write(l);
    }
}
