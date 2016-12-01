package cn.zjnktion.json.deserializer;

import java.util.Date;

/**
 * @author zjnktion
 */
public class DateDeserializer implements JsonDeserializer {

    public static final DateDeserializer INSTANCE = new DateDeserializer();

    private DateDeserializer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Class clazz, String text) {
        if (text == null || text.length() == 0 || "null".equalsIgnoreCase(text)) {
            return null;
        }

        return (T) new Date(Long.parseLong(text));
    }
}
