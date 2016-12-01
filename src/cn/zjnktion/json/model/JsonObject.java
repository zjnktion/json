package cn.zjnktion.json.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjnktion
 */
public class JsonObject implements Json, JsonValue {

    private Map<String, JsonValue> keyValue = new HashMap<String, JsonValue>();

    public JsonObject(Map<String, JsonValue> map) {
        this.keyValue = map;
    }

    public int getInt(String key) {
        return Integer.parseInt((String) keyValue.get(key).value());
    }

    public String getString(String key) {
        return (String) keyValue.get(key).value();
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean((String) keyValue.get(key).value());
    }

    public JsonArray getArray(String key) {
        return (JsonArray) keyValue.get(key).value();
    }

    @Override
    public Object value() {
        return this;
    }
}
