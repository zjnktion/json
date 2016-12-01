package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class BooleanDeserializer implements JsonDeserializer {

    public static final BooleanDeserializer INSTANCE = new BooleanDeserializer();

    private BooleanDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        if ("true".equalsIgnoreCase(text) || "1".equalsIgnoreCase(text)) {
            return (T) Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(text) || "0".equalsIgnoreCase(text)) {
            return (T) Boolean.FALSE;
        }

        return null;
    }
}
