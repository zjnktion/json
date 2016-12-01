package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class ShortDeserializer implements JsonDeserializer {

    public static final ShortDeserializer INSTANCE = new ShortDeserializer();

    private ShortDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Short.valueOf(text);
    }
}
