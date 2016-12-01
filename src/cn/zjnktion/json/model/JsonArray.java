package cn.zjnktion.json.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjnktion
 */
public class JsonArray implements Json, JsonValue {

    private List<Json> list = new ArrayList<Json>();

    public JsonArray(List<Json> list) {
        this.list = list;
    }

    public int length() {
        return list.size();
    }

    public void add(Json element) {
        list.add(element);
    }

    public Json get(int index) {
        return list.get(index);
    }

    @Override
    public Object value() {
        return this;
    }
}
