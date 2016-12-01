package cn.zjnktion.json.model;

/**
 * @author zjnktion
 */
public class JsonPrimary implements Json, JsonValue {

    private String value;

    public JsonPrimary(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }
}
