package cn.zjnktion.json.deserializer;

/**
 * @author zjnktion
 */
public interface JsonDeserializer {

    <T> T deserialize(Class clazz, String text);
}
