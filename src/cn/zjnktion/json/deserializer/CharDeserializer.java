package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class CharDeserializer implements JsonDeserializer {

    public static final CharDeserializer INSTANCE = new CharDeserializer();

    private CharDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Character.valueOf(text.charAt(0));
    }
}
