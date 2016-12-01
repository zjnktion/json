package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class StringSerializer implements JsonSerializer {

    public static final StringSerializer INSTANCE = new StringSerializer();

    private StringSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        CharSequence cs = (CharSequence) obj;
        String str = cs.toString();
        writer.write(IConstants.STR_FIX);
        writer.write(str);
        writer.write(IConstants.STR_FIX);
    }
}
