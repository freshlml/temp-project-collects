package com.fresh.temp.yui.chapter1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Exercise2 {

    public static void main(String argv[]) {

        celsius(45.6);

        celsius(new BigDecimal("999.1"));

        BigDecimal result = new BigDecimal("1.1").multiply(new BigDecimal("1.1"), new MathContext(2 + 2, RoundingMode.HALF_UP));
        System.out.println(result);


        fahrenheit();

        int m = 10_0000_0000;
        int days = m / (60*24);
        int remainder_m_of_day = m % (60*24);

        System.out.println(remainder_m_of_day);
        System.out.println(m % 60 + ((m/60) % 24)*60);


        BigDecimal div = new BigDecimal("9998.6").divide(new BigDecimal("3.1"), 4, RoundingMode.DOWN);
        System.out.println(div);
        BigDecimal mod = new BigDecimal("9998.6").remainder(new BigDecimal("3.1"));
        System.out.println(mod);

        f();

    }


    static void celsius(double d) {
        if(Double.isNaN(d)) return;

        double result = (5.0/9)*(d - 32);
        System.out.printf("%.16f\n", result);

    }

    static void celsius(BigDecimal bd) {

        BigDecimal one = bd.subtract(new BigDecimal(32));
        BigDecimal two = one.multiply(new BigDecimal(5));
        BigDecimal result = two.divide(new BigDecimal(9), 2, RoundingMode.HALF_UP);

        System.out.println(result);

    }

    static void fahrenheit() {

        //floating-point
        double c = 35.1118;
        double d = (9.0/5) * c + 32;
        System.out.printf("%.16f\n", d);

        //decimal
        BigDecimal one = new BigDecimal(9).divide(new BigDecimal(5));
        BigDecimal result = one.multiply(new BigDecimal("35.1118")).add(new BigDecimal(32));
        System.out.println(result);

    }


    static void f() {
        BigDecimal one = new BigDecimal(3);
        BigDecimal two = new BigDecimal(2);
        BigDecimal a = new BigDecimal(6);

        for(int i=1; i<1000; i++) {
            BigDecimal bg = new BigDecimal(12345.555 + i);

            BigDecimal result1 = bg.divide(one, 4, RoundingMode.DOWN).divide(two, 4, RoundingMode.DOWN);
            System.out.print(result1);
            System.out.print(" , ");
            System.out.println(bg.divide(a, 4, RoundingMode.DOWN));
        }


    }


}
