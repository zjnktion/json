package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.Context;
import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.util.List;

/**
 * @author zjnktion
 */
public class ListSerializer implements JsonSerializer {

    public static final ListSerializer INSTANCE = new ListSerializer();

    private ListSerializer() {

    }

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.ARRAY_PREFIX);

        List list = (List) obj;

        if (list != null && !list.isEmpty()) {
            int i = 0;
            for (Object o : list) {
                if (i > 0) {
                    writer.write(IConstants.ELEMENT_SEPARATOR);
                }
                if (o == null) {
                    writer.writeNull();
                }
                else {
                    JsonSerializer serializer = Context.getSerializer(o.getClass());
                    serializer.serialize(writer, o);
                }
                i++;
            }
        }

        writer.write(IConstants.ARRAY_SUFFIX);
    }
}
