package cn.zjnktion.json.serializer;

import cn.zjnktion.json.util.Context;
import cn.zjnktion.json.util.IConstants;
import cn.zjnktion.json.writer.JsonWriter;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author zjnktion
 */
public class ReferenceSerializer implements JsonSerializer {

    public static final ReferenceSerializer INSTANCE = new ReferenceSerializer();

    private ReferenceSerializer() {

    }

    private static final String GET_REGEX = "get(\\w+)";
    private static final Pattern GET_PATTERN = Pattern.compile(GET_REGEX);
    private static final String REPLACE_POS = "$1";

    @Override
    public void serialize(JsonWriter writer, Object obj) {
        writer.write(IConstants.OBJ_PREFIX);

        Method[] methods = obj.getClass().getMethods();
        for (int i = 0, j = 0; i < methods.length; i++) {
            if (checkMethod(methods[i])) {
                if (j > 0) {
                    writer.write(IConstants.ELEMENT_SEPARATOR);
                }

                String methodName = methods[i].getName();
                String midFieldName = GET_PATTERN.matcher(methodName).replaceAll(REPLACE_POS);
                StringBuilder sb = new StringBuilder(midFieldName);
                sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
                String fieldName = sb.toString();

                Object fieldValue = null;
                try {
                    methods[i].setAccessible(true);
                    fieldValue = methods[i].invoke(obj);
                }
                catch (Exception e) {
                    fieldValue = null;
                }

                writer.write(IConstants.STR_FIX);
                writer.write(fieldName);
                writer.write(IConstants.STR_FIX);

                writer.write(IConstants.KV_SEPARATOR);

                if (fieldValue == null) {
                    writer.writeNull();
                }
                else {
                    JsonSerializer serializer = Context.getSerializer(fieldValue.getClass());
                    serializer.serialize(writer, fieldValue);
                }

                j++;
            }
        }

        writer.write(IConstants.OBJ_SUFFIX);
    }

    private boolean checkMethod(Method method) {
        return Pattern.matches(GET_REGEX, method.getName()) && method.getReturnType() != void.class && method.getReturnType() != Void.class && method.getReturnType() != Class.class && method.getParameterTypes().length == 0;
    }
}
