package cn.zjnktion.json.util;

import cn.zjnktion.json.exception.JsonParseException;
import cn.zjnktion.json.model.JsonArray;
import cn.zjnktion.json.model.JsonObject;
import cn.zjnktion.json.reader.FirstReader;
import cn.zjnktion.json.reader.JsonReader;
import cn.zjnktion.json.reader.parser.DefaultParser;
import cn.zjnktion.json.reader.parser.JsonParser;
import cn.zjnktion.json.reader.tokenizer.Tokenizer;
import cn.zjnktion.json.serializer.JsonSerializer;
import cn.zjnktion.json.writer.JsonWriter;
import cn.zjnktion.json.writer.StringBuilderWriter;

/**
 * @author zjnktion
 */
public final class JSON {

    public static String toJsonString(Object obj) {
        JsonWriter writer = new StringBuilderWriter();
        JsonSerializer serializer = Context.getSerializer(obj.getClass());
        serializer.serialize(writer, obj);
        return writer.toString();
    }

    public static JsonObject parseJSONObject(String text) throws JsonParseException {
        JsonReader reader = new FirstReader(text);
        Tokenizer tokenizer = new Tokenizer(reader);
        JsonParser parser = new DefaultParser(tokenizer);
        return parser.parseObject();
    }

    public static JsonArray parseJSONArray(String text) throws JsonParseException {
        JsonReader reader = new FirstReader(text);
        Tokenizer tokenizer = new Tokenizer(reader);
        JsonParser parser = new DefaultParser(tokenizer);
        return parser.parseArray();
    }
}
