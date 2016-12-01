package cn.zjnktion.json.reader.parser;

import cn.zjnktion.json.exception.JsonParseException;
import cn.zjnktion.json.model.*;
import cn.zjnktion.json.reader.tokenizer.Token;
import cn.zjnktion.json.reader.tokenizer.TokenType;
import cn.zjnktion.json.reader.tokenizer.Tokenizer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjnktion
 */
public class DefaultParser implements JsonParser {

    private Tokenizer tokenizer;

    public DefaultParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public JsonObject parseObject() throws JsonParseException {
        tokenizer.tokenize(); // 构建语法树

        if (!isTopTouchAs(TokenType.OBJ_PREFIX)) {
            throw new JsonParseException("invalid input");
        }
        return readJsonObject();
    }

    @Override
    public JsonArray parseArray() throws JsonParseException {
        tokenizer.tokenize();

        if (!isTopTouchAs(TokenType.ARRAY_PREFIX)) {
            throw new JsonParseException("invalid input");
        }
        return readJsonArray();
    }

    @SuppressWarnings("unchecked  ")
    @Override
    public <T> T parseObject(Class<T> clazz) throws JsonParseException {
        JsonObject result = parseObject();

        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor();
        }
        catch (NoSuchMethodException e) {
            throw new JsonParseException("clazz parse error");
        }
        Object latestNews = null;
        try {
            latestNews = constructor.newInstance();
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new JsonParseException("clazz parse error");
        }
        Field[] fields = clazz.getDeclaredFields();
        int numField = fields.length;
        String[] fieldNames = new String[numField];
        String[] fieldTypes = new String[numField];
        for (int i = 0; i < numField; i++) {
            String type = fields[i].getType().getName();
            String name = fields[i].getName();
            fieldTypes[i] = type;
            fieldNames[i] = name;
        }
        for (int i = 0; i < numField; i++) {
            if (fieldTypes[i].equals("java.lang.String")) {
                fields[i].setAccessible(true);
                try {
                    fields[i].set(latestNews, result.getString(fieldNames[i]));
                }
                catch (IllegalAccessException e) {
                    throw new JsonParseException("clazz parse error");
                }
            } else if (fieldTypes[i].equals("java.util.List")) {
                fields[i].setAccessible(true);
                JsonArray array = result.getArray(fieldNames[i]);
                ParameterizedType pt = (ParameterizedType) fields[i].getGenericType();
                Type elementType = pt.getActualTypeArguments()[0];
                String elementTypeName = elementType.toString();
                Class<?> elementClass = null;
                try {
                    elementClass = Class.forName(elementTypeName);
                }
                catch (ClassNotFoundException e) {
                    throw new JsonParseException("clazz parse error");
                }

                //fields[i].set(latestNews, inflateList(array, elementClass));//Type Capture

            } else if (fieldTypes[i].equals("int")) {
                fields[i].setAccessible(true);
                try {
                    fields[i].set(latestNews, result.getString(fieldNames[i]));
                }
                catch (IllegalAccessException e) {
                    throw new JsonParseException("clazz parse error");
                }
            }
        }
        return (T) latestNews;
    }

    @Override
    public <T> List<T> parseArray(Class<T> clazz) throws JsonParseException {
        return null;
    }

    // --- 私有方法 -----------------------------------------------------------------------------------------------------
    private JsonObject readJsonObject() throws JsonParseException {
        Token token = tokenizer.next(); // 消费一个"{"

        Map<String, JsonValue> map = new HashMap<String, JsonValue>();
        if (isTopTouchAs(TokenType.OBJ_SUFFIX)) { // 如果下一个就是对象的结尾
            tokenizer.next();
            return new JsonObject(map);
        }
        else if (isTopTouchAs(TokenType.STRING)) { // 如果下一个是字符串，说明是key
            map = readMap(map);
        }
        else {
            throw new JsonParseException("invalid input");
        }
        return new JsonObject(map);
    }

    private boolean isTopTouchAs(TokenType type) {
        return tokenizer.peek().getType() == type;
    }

    private Map<String, JsonValue> readMap(Map<String, JsonValue> map) throws JsonParseException {
        Token keyToken = tokenizer.next();
        String key = keyToken.getValue();
        if (!isTopTouchAs(TokenType.COLON)) {
            throw new JsonParseException("invalid input");
        }
        else {
            tokenizer.next(); // 消费一个":"
            if (isTopTouchAsPrimaryType()) {
                JsonValue value = new JsonPrimary(tokenizer.next().getValue());
                map.put(key, value);
            }
            else if (isTopTouchAs(TokenType.ARRAY_PREFIX)) {
                JsonValue value = readJsonArray();
                map.put(key, value);
            }
            else if (isTopTouchAs(TokenType.OBJ_PREFIX)) {
                JsonValue value = readJsonObject();
                map.put(key, value);
            }
            if (isTopTouchAs(TokenType.COMMA)) {
                tokenizer.next(); // 消费一个","
                if (isTopTouchAs(TokenType.STRING)) {
                    map = readMap(map);
                }
            }
            else if (isTopTouchAs(TokenType.OBJ_SUFFIX)) {
                tokenizer.next(); // 消费一个"}"
                return map;
            }
            else if (isTopTouchAs(TokenType.FINISH)) {
                tokenizer.next(); // 消费一个"EOF"
                return map;
            }
            else {
                throw new JsonParseException("invalid input");
            }
        }
        return map;
    }

    private boolean isTopTouchAsPrimaryType() {
        TokenType topType = tokenizer.peek().getType();
        return topType == TokenType.BOOLEAN || topType == TokenType.NULL || topType == TokenType.NUMBER || topType == TokenType.STRING;
    }

    private JsonArray readJsonArray() throws JsonParseException {
        tokenizer.next(); // 消费一个"["
        List<Json> list = new ArrayList<Json>();
        JsonArray array = null;
        if (isTopTouchAs(TokenType.ARRAY_PREFIX)) {
            array = readJsonArray();
            list.add(array);
            if (isTopTouchAs(TokenType.COMMA)) {
                tokenizer.next(); // 消费一个","
                list = listElement(list);
            }
        }
        else if (isTopTouchAsPrimaryType()) {
            list = listElement(list);
        }
        else if (isTopTouchAs(TokenType.OBJ_PREFIX)) {
            list.add(readJsonObject());
            while (isTopTouchAs(TokenType.COMMA)) {
                tokenizer.next();
                list.add(readJsonObject());
            }
        }
        else if (isTopTouchAs(TokenType.ARRAY_SUFFIX)) {
            tokenizer.next(); // 消费一个"]"
            array = new JsonArray(list);
            return array;
        }
        tokenizer.next(); // 消费一个"]"
        array = new JsonArray(list);
        return array;
    }

    private List<Json> listElement(List<Json> list) throws JsonParseException {
        list.add(new JsonPrimary(tokenizer.next().getValue()));
        if (isTopTouchAs(TokenType.COMMA)) {
            tokenizer.next();
            if (isTopTouchAsPrimaryType()) {
                list = listElement(list);
            }
            else if (isTopTouchAs(TokenType.OBJ_PREFIX)) {
                list.add(readJsonObject());
            }
            else if (isTopTouchAs(TokenType.ARRAY_PREFIX)) {
                list.add(readJsonArray());
            }
            else {
                throw new JsonParseException("invalid input");
            }
        }
        else if (isTopTouchAs(TokenType.ARRAY_SUFFIX)) {
            return list;
        }
        else {
            throw new JsonParseException("invalid input");
        }
        return list;
    }
}
