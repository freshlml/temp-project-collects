package com.fresh.temp.demo.pt3;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Temp3 {

    public static void main(String[] argv) {
        D d_proxy = (D) Proxy.newProxyInstance(D.class.getClassLoader(), new Class[]{D.class}, new Ivh());

        List<Pojo> li = d_proxy.c(new Pojo(1));
        Pojo i = li.get(0);
        System.out.println(i);
    }
}

interface C<T> {
    List<T> c(T t);
}

interface D extends C<Pojo> {

}

class Ivh implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("c".equals(method.getName())) {
            String dcn = method.getDeclaringClass().getName();
            Type type = find(proxy.getClass(), dcn);

            if(type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                Type at = pt.getActualTypeArguments()[0];

                Class<?> atc = (Class<?>) at;

                List<Object> li = new ArrayList<>();
                li.add(atc.newInstance());

                return li;
            }
            List<Pojo> li = new ArrayList<>();
            li.add(new Pojo(2));
            return li;
        }

        return null;
    }

    Type find(Class<?> clazz, String typeName) {

        Type[] genericInterfaces = clazz.getGenericInterfaces();

        for(int i = 0; i < genericInterfaces.length; ) {
            Class<?> c = null;
            if(genericInterfaces[i] instanceof Class) {
                c = (Class<?>) genericInterfaces[i];
            } else if(genericInterfaces[i] instanceof ParameterizedType) {
                ParameterizedType pc = (ParameterizedType) genericInterfaces[i];
                c = (Class<?>) pc.getRawType();
            }

            if(typeName.equals(c.getName())) {
                return genericInterfaces[i];
            }

            Type f = find(c, typeName);
            if(f != null) return f;

            i++;
        }

        return null;
    }
}

class Pojo {
    int i;

    public Pojo() {
    }

    public Pojo(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "i=" + i +
                '}';
    }
}