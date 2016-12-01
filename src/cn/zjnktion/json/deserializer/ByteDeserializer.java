package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class ByteDeserializer implements JsonDeserializer {

    public static final ByteDeserializer INSTANCE = new ByteDeserializer();

    private ByteDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) Byte.valueOf(Byte.parseByte(text, 16));
    }
}
