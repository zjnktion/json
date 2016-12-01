package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class ByteSerializer implements JsonSerializer {

    public static final ByteSerializer INSTANCE = new ByteSerializer();

    private ByteSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        final Byte b = (Byte) obj;
        writer.write(b);
    }
}
