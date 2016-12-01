package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class LongDeserializer implements JsonDeserializer {

    public static final LongDeserializer INSTANCE = new LongDeserializer();

    private LongDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 | "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Long.valueOf(text);
    }
}
