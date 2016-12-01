package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public class StringArraySerializer implements JsonSerializer {

    public static final StringArraySerializer INSTANCE = new StringArraySerializer();

    private StringArraySerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        String[] strs = (String[]) obj;
        for (int i = 0; i < strs.length; i++) {
            if (i > 0) {
                writer.write(IConstants.ELEMENT_SEPARATOR);
            }
            writer.write(IConstants.STR_FIX);
            writer.write(strs[i]);
            writer.write(IConstants.STR_FIX);
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
