package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

import java.util.Date;

/**
 * @author zjnktion
 */
public class DateSerializer implements JsonSerializer {

    public static final DateSerializer INSTANCE = new DateSerializer();

    private DateSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        Date date = (Date) obj;
        writer.write(date.getTime());
    }
}
