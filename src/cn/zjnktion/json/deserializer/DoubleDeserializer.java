package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class DoubleDeserializer implements JsonDeserializer {

    public static final DoubleDeserializer INSTANCE = new DoubleDeserializer();

    private DoubleDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Double.valueOf(text);
    }
}
