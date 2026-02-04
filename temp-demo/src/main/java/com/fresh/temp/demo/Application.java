package com.fresh.temp.demo;

import com.fresh.common.utils.ScanUtils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
/**
 * Hello world!
 *
 */
public class Application {

    public static void main(String[] args) throws Exception {
        System.out.println("sun.boot.class.path: " + System.getProperty("sun.boot.class.path"));
        System.out.println("java.ext.dirs: " + System.getProperty("java.ext.dirs"));
        System.out.println("java.class.path: " + System.getProperty("java.class.path"));

        System.out.println(ScanUtils.scan("nothing"));
        Enumeration<URL> jarInsRes = Application.class.getClassLoader().getResources("com/fresh");
        while(jarInsRes.hasMoreElements()) {
            System.out.println(jarInsRes.nextElement());
        }

        URL re = Application.class.getClassLoader().getResource("sunec.jar");
        System.out.println(re);

        System.out.println(ScanUtils.class.getClassLoader());

        test_cls();

        URL nn = Application.class.getClassLoader().getResource(""); //user.dir
        System.out.println(nn);

    }

    public static void test_cls() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        while(classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                    System.out.println(url);
                }
            }
            System.out.println(classLoader + "############");
            classLoader = classLoader.getParent();
        }

    }

}



