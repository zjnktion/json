package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class IntegerSerializer implements JsonSerializer {

    public static final IntegerSerializer INSTANCE = new IntegerSerializer();

    private IntegerSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Number num = (Number) obj;
        Integer i = num.intValue();
        writer.write(i);
    }
}
