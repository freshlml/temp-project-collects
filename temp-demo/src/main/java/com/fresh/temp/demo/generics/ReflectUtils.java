package com.fresh.temp.demo.generics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class ReflectUtils {

    /**
     * 查找所有field
     *  查找范围: 1.Class Object的declaredFields
     *      2.在Class Object的interfaces中深度递归
     *      3.在Class Object的superclass中深度递归
     * @param clazz 不能为空
     * @return
     */
    public static Field[] findAllDeclaredFields(Class<?> clazz) {
        if(clazz == null) throw new RuntimeException("参数clazz不能为空");

        List<Field> result = new ArrayList<>();
        findDeclaredFieldConsumer(clazz, result::add);
        return result.toArray(new Field[0]);
    }
    /**@see ReflectUtils#findAllDeclaredFields*/
    public static void findDeclaredFieldConsumer(Class<?> clazz, Consumer<Field> consumer) {
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(consumer);

        Class<?>[] interfaces = clazz.getInterfaces();
        for(Class<?> inter : interfaces) {
            findDeclaredFieldConsumer(inter, consumer);
        }
        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null && superClass != Object.class) findDeclaredFieldConsumer(superClass, consumer);

    }

}
