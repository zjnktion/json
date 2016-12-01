package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public class MapDeserializer implements JsonDeserializer {

    public static final MapDeserializer INSTANCE = new MapDeserializer();

    private MapDeserializer() {

    }

    @Override
    public <T> T deserialize(Class clazz, String text) {
        return null;
    }
}
