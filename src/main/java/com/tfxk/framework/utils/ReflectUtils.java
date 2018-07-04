package com.tfxk.framework.utils;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Chenchunpeng on 2015/9/16.
 */
public class ReflectUtils {

    public static Field generatorField(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            if (field == null)
                clazz = clazz.getSuperclass();
            field = clazz.getDeclaredField(fieldName);
        }
        return field;
    }

    public static String generateSetMethod(Field field) {
        String fieldName = field.getName();
        field.setAccessible(true);
        return "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
    }

    public static String generateGetMethod(Field field) {
        String name = field.getName();
        String methodName = name.toUpperCase().charAt(0) + name.substring(1);
        methodName = "get" + methodName;
        return methodName;
    }

    public static void handleSetMethod(Class clazz, Field field, Object value, Object out) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = generateSetMethod(field);
        Class fieldType = field.getType();
        if (fieldType.equals(String.class)) {
            value = String.valueOf(value);
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            value = Integer.valueOf(String.valueOf(value));
        } else if (fieldType.equals(float.class) || Float.class.equals(fieldType)) {
            value = Float.valueOf(String.valueOf(value));
        } else if (Double.class.equals(fieldType) || double.class.equals(fieldType)) {
            value = Double.valueOf(String.valueOf(value));
        }
        try {
            clazz.getDeclaredMethod(methodName, field.getType()).invoke(out, value);
        } catch (Exception e) {
            clazz = clazz.getSuperclass();
            clazz.getDeclaredMethod(methodName, field.getType()).invoke(out, value);
        }
    }

    /**
     * @param clazz the class of the field which want to reflect
     * @param field the field which the object want to reflect
     * @param out   the object which need call the getXXX() method
     * @return the object which after call getXXX()
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object handleGetMethod(Class clazz, Field field, Object out) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = generateGetMethod(field);
        Object object;
        try {
            object = clazz.getDeclaredMethod(methodName, new Class[]{}).invoke(out, new Object[]{});
        } catch (Exception e) {
            clazz = clazz.getSuperclass();
            object = clazz.getDeclaredMethod(methodName, new Class[]{}).invoke(out, new Object[]{});
        }
        return object;
    }

}
