package cn.zjnktion.json.serializer;

import cn.zjnktion.json.writer.JsonWriter;

/**
 * @author zjnktion
 */
public interface JsonSerializer {

    void serialize(JsonWriter writer, Object obj);
}
