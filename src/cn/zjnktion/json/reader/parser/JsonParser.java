package cn.zjnktion.json.reader.parser;

import cn.zjnktion.json.exception.JsonParseException;
import cn.zjnktion.json.model.JsonArray;
import cn.zjnktion.json.model.JsonObject;

import java.util.List;

/**
 * @author zjnktion
 */
public interface JsonParser {

    JsonObject parseObject() throws JsonParseException;

    JsonArray parseArray() throws JsonParseException;

    <T> T parseObject(Class<T> clazz) throws JsonParseException;

    <T>List<T> parseArray(Class<T> clazz) throws JsonParseException;
}
