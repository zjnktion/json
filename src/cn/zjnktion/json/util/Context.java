package cn.zjnktion.json.util;

import cn.zjnktion.json.deserializer.*;
import cn.zjnktion.json.serializer.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjnktion
 */
public abstract class Context {

    private static final Map<Class, JsonSerializer> SERIALIZER_MAP = new HashMap<Class, JsonSerializer>();
    private static final Map<Class, JsonDeserializer> DESERIALIZER_MAP = new HashMap<Class, JsonDeserializer>();

    static {
        SERIALIZER_MAP.put(boolean.class, BooleanSerializer.INSTANCE);
        SERIALIZER_MAP.put(Boolean.class, BooleanSerializer.INSTANCE);
        SERIALIZER_MAP.put(boolean[].class, BooleanArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Boolean[].class, BooleanArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(byte.class, ByteSerializer.INSTANCE);
        SERIALIZER_MAP.put(Byte.class, ByteSerializer.INSTANCE);
        SERIALIZER_MAP.put(byte[].class, ByteArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Byte[].class, ByteArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(char.class, CharSerializer.INSTANCE);
        SERIALIZER_MAP.put(Character.class, CharSerializer.INSTANCE);
        SERIALIZER_MAP.put(char[].class, CharArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Character[].class, CharArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(short.class, ShortSerializer.INSTANCE);
        SERIALIZER_MAP.put(Short.class, ShortSerializer.INSTANCE);
        SERIALIZER_MAP.put(short[].class, ShortArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Short[].class, ShortArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(int.class, IntegerSerializer.INSTANCE);
        SERIALIZER_MAP.put(Integer.class, IntegerSerializer.INSTANCE);
        SERIALIZER_MAP.put(int[].class, IntegerArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Integer[].class, IntegerArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(float.class, FloatSerializer.INSTANCE);
        SERIALIZER_MAP.put(Float.class, FloatSerializer.INSTANCE);
        SERIALIZER_MAP.put(float[].class, FloatArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Float[].class, FloatArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(long.class, LongSerializer.INSTANCE);
        SERIALIZER_MAP.put(Long.class, LongSerializer.INSTANCE);
        SERIALIZER_MAP.put(long[].class, LongArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Long[].class, LongArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(double.class, DoubleSerializer.INSTANCE);
        SERIALIZER_MAP.put(Double.class, DoubleSerializer.INSTANCE);
        SERIALIZER_MAP.put(double[].class, DoubleArraySerializer.INSTANCE);
        SERIALIZER_MAP.put(Double[].class, DoubleArraySerializer.INSTANCE);

        SERIALIZER_MAP.put(String.class, StringSerializer.INSTANCE);
        SERIALIZER_MAP.put(String[].class, StringArraySerializer.INSTANCE);

        SERIALIZER_MAP.put(Map.class, MapSerializer.INSTANCE);
        SERIALIZER_MAP.put(List.class, ListSerializer.INSTANCE);

        SERIALIZER_MAP.put(Date.class, DateSerializer.INSTANCE);
    }

    static {
        DESERIALIZER_MAP.put(boolean.class, BooleanDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Boolean.class, BooleanDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(byte.class, ByteDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Byte.class, ByteDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(char.class, CharDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Character.class, CharDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(short.class, ShortDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Short.class, ShortDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(int.class, IntegerDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Integer.class, IntegerDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(float.class, FloatDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Float.class, FloatDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(long.class, LongDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Long.class, LongDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(double.class, DoubleDeserializer.INSTANCE);
        DESERIALIZER_MAP.put(Double.class, DoubleDeserializer.INSTANCE);

        DESERIALIZER_MAP.put(Date.class, DateDeserializer.INSTANCE);
    }

    public static JsonSerializer getSerializer(Class clazz) {
        JsonSerializer serializer = null;

        serializer = SERIALIZER_MAP.get(clazz);

        if (serializer != null) {
            return serializer;
        }

        if (Map.class.isAssignableFrom(clazz)) {
            return SERIALIZER_MAP.get(Map.class);
        }

        if (List.class.isAssignableFrom(clazz)) {
            return SERIALIZER_MAP.get(List.class);
        }

        if (Date.class.isAssignableFrom(clazz)) {
            return SERIALIZER_MAP.get(Date.class);
        }

        if (clazz.isArray()) {
            return ReferenceArraySerializer.INSTANCE;
        }

        return ReferenceSerializer.INSTANCE;
    }

    public static JsonDeserializer getDeserializer(Class clazz) {
        JsonDeserializer deserializer = null;

        deserializer = DESERIALIZER_MAP.get(clazz);

        if (deserializer != null) {
            return deserializer;
        }

        return null;
    }
}
