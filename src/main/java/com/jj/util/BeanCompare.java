package com.jj.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.collection.internal.PersistentBag;

@SuppressWarnings(value = "all")
public class BeanCompare {

    private static Logger logger = Logger.getLogger(BeanCompare.class.getName());

    private BeanCompare() {
        // Garante que a classe não seja instanciada.
    }

    @SuppressWarnings("unchecked")
    public static Boolean eq(Object obj1, Object obj2) {

        int hash1 = getHashFrom(obj1);

        int hash2 = getHashFrom(obj2);

        return hash1 == hash2;
    }

    public static int getHashFrom(Object obj) {

        int hash1 = 0;

        if (obj == null) {
            return hash1;
        }

        Class<?> type1 = obj.getClass();

        if (type1.isAssignableFrom(List.class) || type1.isAssignableFrom(ArrayList.class)) {

            List<?> itens = (List<?>) obj;
            for (Object item : itens) {
                hash1 += getHashItem(item);
            }

        } else {

            hash1 = getHashItem(obj);

        }
        return hash1;
    }

    private static int getHashItem(Object obj) {

        int hash = 0;

        Class<?> type = obj.getClass();

        if (!isBeanType(type.getName())) {
            return obj.hashCode();
        }

        List<Field> fields = getAllFields(type);

        try {
            for (Field field : fields) {

                if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {

                    field.setAccessible(true);

                    Class<?> ob = field.getType();

                    if (ob.isAssignableFrom(List.class) || ob.isAssignableFrom(ArrayList.class)) {

                        hash += hashFromList(field, obj);

                    } else {

                        hash += hashFromObject(field, obj);

                    }
                }

            }

        } catch (Exception e) {
            logger.severe("Ocorreu uma falha ao comparar os objetos." + e.toString());
        }
        return hash;
    }

    private static int hashFromObject(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {

        Object value = field.get(obj);

        Class<?> type = field.getType();

        String name = type.getName();

        if (value == null) {
            return 0;

        }

        if (isBeanType(name)) {

            return getHashFrom(value);

        }

        return value.hashCode();
    }

    private static boolean isBeanType(String name) {
        return !name.split("\\.")[0].equals("java");
    }

    private static int hashFromList(Field field, Object orig)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ParameterizedType fieldGenericType = (ParameterizedType) field.getGenericType();
        Class<?> fieldType = (Class<?>) fieldGenericType.getActualTypeArguments()[0];

        Method method;

        method = orig.getClass().getMethod("get" + StringUtil.primeiraMaiscula(field.getName()), new Class[] {});
        Object value = method.invoke(orig, new Object[] {});

        if (value instanceof PersistentBag) {
            value = Arrays.asList(((PersistentBag) value).toArray());
        }

        int hash = 0;

        if (value != null) {

            List<?> values = (List<?>) value;

            for (Object item : values) {

                if (item != null) {

                    Class<?> tipo = item.getClass();

                    String name = tipo.getName();

                    if (isBeanType(name)) {

                        hash += getHashFrom(item);

                    } else {

                        hash += item.hashCode();
                    }
                }
            }
        }

        return hash;

    }

    private static List<Field> getAllFields(Class<?> clazz) {

        List<Field> fieldList = new ArrayList<Field>();

        Class<?> superClazz = clazz.getSuperclass();

        if (!superClazz.equals(Object.class)) {
            fieldList.addAll(getAllFields(superClazz));
        }

        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));

        return fieldList;

    }

}
