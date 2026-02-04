package com.fresh.temp.demo.component;


import com.fresh.temp.demo.component.clazz.ClazzComponent;
import com.fresh.temp.demo.component.clazz.DefaultClazzComponentResolver;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class ComponentTest {

    public static void main(String[] argv) throws Exception {

        ClazzComponent<Object> objectClazzComponent = new ClazzComponent<>(Object.class, true);
        ClazzComponent<Serializable> serClazzComponent = new ClazzComponent<>(Serializable.class, true);

        ClazzComponent<Number> numberClazzComponent = new ClazzComponent<>(Number.class);
        numberClazzComponent.addChild(objectClazzComponent);
        numberClazzComponent.addChild(serClazzComponent);

        ClazzComponent<Comparable> comparableClazzComponent = new ClazzComponent<>(Comparable.class, true);

        ClazzComponent<Integer> clazzComponent = new ClazzComponent<>(Integer.class);
        clazzComponent.addChild(numberClazzComponent);
        clazzComponent.addChild(comparableClazzComponent);

        DefaultClazzComponentResolver<Integer> defaultClazzComponentResolver = new DefaultClazzComponentResolver<>(clazzComponent);
        List<ClazzComponent<?>> results = defaultClazzComponentResolver.getAllSuperClass();

        Class<?> clazz1 = results.get(0).getEntity();
        int[] a1 = new int[1];
        int[] a2 = new int[2];

        m(a1, a2);
        int[][] a3 = new int[][] {{1, 2, 3}, {1, 3}};

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        long offset = unsafe.objectFieldOffset(ComponentTest.class.getDeclaredField("i"));
        System.out.println(offset);

        offset = unsafe.objectFieldOffset(ComponentTest.class.getDeclaredField("j"));
        System.out.println(offset);

        offset = unsafe.objectFieldOffset(ComponentTest.class.getDeclaredField("b"));
        System.out.println(offset);

        System.out.println(unsafe.arrayBaseOffset(short[][].class));
        System.out.println(unsafe.arrayIndexScale(short[][].class));
        System.out.println("-----------------------------------------------");

        ComponentTest componentTest = new ComponentTest();
        long xOffset = unsafe.objectFieldOffset(ComponentTest.class.getDeclaredField("x"));
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("update begin...");
            componentTest.x = 1;
            //unsafe.putInt(temp, xOffset, 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        int i = 0;
        while(unsafe.getInt(componentTest, xOffset) == 0) {
            if(i == 0) {
                System.out.println("running begin...");
                i = 1;
            }
        }
        System.out.println("running end..." + componentTest.x);
    }
    private int x;



    private int i;
    private long j;
    private byte b;
    byte[] bs;
    static void m(int[]... widths) {

    }

    static void m2(int[][] widths) {

    }

}


