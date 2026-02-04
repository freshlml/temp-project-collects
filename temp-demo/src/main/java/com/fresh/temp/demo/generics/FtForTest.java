package com.fresh.temp.demo.generics;


import java.util.Date;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FtForTest {

    public static void main(Date argv[]) {

        Class<ForTestMapper> forTestMapperClazz = ForTestMapper.class;

        Type[] genericInterfaces = forTestMapperClazz.getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];

        Type[] args = parameterizedType.getActualTypeArguments();

        Class<?> ftForTestEntityClazz = (Class<?>) args[0];

        System.out.println(FtForTestEntity.class == ftForTestEntityClazz);

        Field[] fields = ReflectUtils.findAllDeclaredFields(ftForTestEntityClazz);

        System.out.println(fields);


    }

}
