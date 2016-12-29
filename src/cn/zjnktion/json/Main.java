package cn.zjnktion.json;

import cn.zjnktion.json.exception.JsonParseException;
import cn.zjnktion.json.model.JsonArray;
import cn.zjnktion.json.model.JsonObject;
import cn.zjnktion.json.util.Context;
import cn.zjnktion.json.util.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static List<String> listaaa = new ArrayList<String>();

    public static void main(String[] args) throws JsonParseException {
        Context.getSerializer(int.class); // 去掉静态块初始化的耗时
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        long start = System.currentTimeMillis();
        String s = show(a);
        //JsonArray ja = JSON.parseJSONArray(s);
        System.out.println(System.currentTimeMillis() - start);

        Map<String, Object> map = new HashMap<>();
        map.put("a", 1.01d);
        map.put("b", new ArrayList() {
            {
                add(123.7);
                add(234);
                add(new HashMap() {
                    {
                        put("lia", 'a');
                        put("lib", true);
                        put("lic", null);
                    }
                });
            }
        });
        map.put("c", new HashMap() {
            {
                put("ia", "sa");
                put("ib", new ArrayList() {
                    {
                        add("g");
                    }
                });
            }
        });
        start = System.currentTimeMillis();
        String s1 = show(map);
        JsonObject jo = JSON.parseJSONObject(s1);
        System.out.println(System.currentTimeMillis() - start);

        List[] lists = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            lists[i] = new ArrayList();
            lists[i].add(i);
            lists[i].add("a");
            lists[i].add("b");
        }
        start = System.currentTimeMillis();
        show(lists);
        System.out.println(System.currentTimeMillis() - start);

        Integer o = new Integer(123);
        //fields(o);

        InnerClass ic = new InnerClass();
        ic.setName("zjn");
        ic.setValue(100);
        List ll = new ArrayList();
        ll.add("s");
        ll.add((Map) null);
        ic.setList(ll);
        start = System.currentTimeMillis();
        show(ic);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(Byte.parseByte("1e", 16));

        Field[] fields = Main.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            System.out.println(fields[i].getType().getName());
            System.out.println(fields[i].getName());

            ParameterizedType pt = (ParameterizedType) fields[i].getGenericType();
            Type elementType = pt.getActualTypeArguments()[0];
            String elementTypeName = elementType.getTypeName();
            System.out.println(elementTypeName.equals("java.lang.String"));
        }
    }

    public static String show(Object obj) {
        String jsonStr = JSON.toJsonString(obj);
        System.out.println(jsonStr);
        return jsonStr;
    }

    public static void fields(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + ":::" + (method.getReturnType() == Void.TYPE) + ":::" + (method.getParameterTypes().length == 0));
        }
    }

    static class InnerClass {
        private String name;
        private int value;
        private List list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }
    }
}
