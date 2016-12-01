package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class FloatDeserializer implements JsonDeserializer {

    public static final FloatDeserializer INSTANCE = new FloatDeserializer();

    private FloatDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Float.valueOf(text);
    }
}
