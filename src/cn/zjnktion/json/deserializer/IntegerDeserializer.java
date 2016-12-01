package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class IntegerDeserializer implements JsonDeserializer {

    public static final IntegerDeserializer INSTANCE = new IntegerDeserializer();

    private IntegerDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Integer.valueOf(text);
    }
}
