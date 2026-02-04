package com.fresh.temp.yui.chapter1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Exercise1 {

    public static void main(String argv[]) {

        double d1 = 9.5 * 4.5 - 2.5 * 3;  //the operand, result has no precision lost
        double d2 = 45.5 - 3.5;  //the operand, result has no precision lost

        double d = d1 / d2;
        System.out.println(d);  //0.8392857142857143
        System.out.printf("%.16f\n", d);  //0.8392857142857143

        BigDecimal bd = new BigDecimal("35.25");
        System.out.println(bd.divide(new BigDecimal("42.0"), MathContext.DECIMAL32));  //0.8392857
        System.out.println(bd.divide(new BigDecimal("42.0"), MathContext.DECIMAL64));  //0.8392857142857143
        System.out.println(bd.divide(new BigDecimal("42.0"), MathContext.DECIMAL128)); //0.8392857142857142857142857142857143

        System.out.println("------------1--------");

        speed();
        System.out.println("------------2--------");

        sum(1000_000);
        System.out.println("------------3--------");

        //expand_sample();

        //sum2(Integer.MAX_VALUE/3);
        System.out.println("------------4--------");

        sum3(3.9999);
        System.out.println("------------5--------");

        rk_cal(5000_000);

    }

    static void speed() {
        double lc = 14.0000001;
        double sj = 145.5;

        System.out.println((lc/1.7)/(sj/60));  // 3.395997598544573
        System.out.printf("%.16f\n", (lc/1.7)/(sj/60));  //3.3959975985445730

        System.out.println((lc/1.7)*(60/sj));  // 3.3959975985445725
        System.out.printf("%.16f\n", (lc/1.7)*(60/sj));  //3.3959975985445725

        System.out.println(lc/1.7/sj*60);  // 3.395997598544573
        System.out.printf("%.16f\n", lc/1.7/sj*60);  //3.3959975985445730

        BigDecimal bd = new BigDecimal("9998");
        BigDecimal d1 = new BigDecimal("1").divide(new BigDecimal("3"), 16, RoundingMode.DOWN);
        BigDecimal d2 = new BigDecimal("1").subtract(d1);
        System.out.println(bd.multiply(d2));  // 6665.3333333333336666

        BigDecimal d3 = bd.multiply(d1);
        System.out.println(bd.subtract(d3));  // 6665.3333333333336666


    }

    static void sum(int max) {
        if(max <= 0) throw new IllegalArgumentException("the argument max is less and equal than 0");

        //等差数列: a a+d a+2d a+3d a+4d...
        //求和公式: n*(A1 + An)/2. 要么n为偶数，要么(A1 + An)能够整除2。所以任何等差数列均可以直接用求和公式。
        //long result = (Integer.toUnsignedLong(Integer.MAX_VALUE) * (1 + Integer.toUnsignedLong(Integer.MAX_VALUE)))/2;
        long result = (Integer.toUnsignedLong(max) * (1 + Integer.toUnsignedLong(max)))/2;  // no overflow
        System.out.println(result);

        long result2 = 0;
        for(int i=1; i<=max; i++) {
            result2 += i;  //no overflow
        }
        System.out.println(result2);

        int result3 = 0;
        BigInteger bi = null;
        overflow: for(int i=1; i<=max; i++) {
            result3 += i;
            if(result3 < 0) {  //overflow
                bi = BigInteger.valueOf(result3 - i);
                for(int j=i; j<=max; j++) {
                    bi = bi.add(BigInteger.valueOf(j));
                }
                break overflow;
            }
        }
        System.out.println(result3);
        System.out.println(bi);

    }


    static void sum2(int sum) {
        double result = 0;
        for(int i=1; i<=sum; i++) {
            if(i % 2 == 0) {
                result -= 1.0/(2.0*i-1);  //1.0/(2.0*Integer.MAX_VALUE-1) > 0.0
            } else {
                result += 1.0/(2.0*i-1);
            }
        }
        System.out.println(result * 4);

        result = 0;
        for(int i=1; i<=sum; i++) {
            if(i % 2 == 0) {
                result -= 4.0/(2.0*i-1);
            } else {
                result += 4.0/(2.0*i-1);
            }
        }

        System.out.println(result);

        BigDecimal one = new BigDecimal("1.0");
        BigDecimal supply = new BigDecimal("0");
        for(int i=1; i<=sum; i++) {
            BigDecimal per = one.divide(new BigDecimal(2.0*i-1), 30, RoundingMode.HALF_UP);
            if(i % 2 == 0) {
                supply = supply.subtract(per);
            } else {
                supply = supply.add(per);
            }
        }
        System.out.println(supply.multiply(new BigDecimal("4")));

    }

    static void sum3(double sum) {
        if(Double.isNaN(sum))
            throw new IllegalArgumentException("the argument sum is NaN");
        if(Double.isInfinite(2.0*sum-1))
            throw new IllegalArgumentException("the argument sum is too large, so that `2.0*sum-1` is finite");

        long dl = Double.doubleToLongBits(sum);
        long jm = dl & 0x7f_f0_00_00_00_00_00_00L;  //获取阶码
        if(jm == 0x7f_f0_00_00_00_00_00_00L) {  //无穷或者 NaN
            //System.out.println("无穷或者NaN");
        } else if(jm == 0) {  //非规格化小数
            //计算指数值
            //short zs = 0x00_01 - 0x03_ff;
            //System.out.println(zs);  //-1022
        } else {
            //计算指数值
            long zs = (jm - 0x3f_f0_00_00_00_00_00_00L) & 0x7f_f0_00_00_00_00_00_00L;
            //System.out.println(zs << 1 >> 53);  //打印指数值
            if((zs << 1 >> 53) > 52) {
                //`sum`的整数部分已丢失精度
                throw new IllegalArgumentException("the argument sum is too large, so that the integer-pair of the `sum` has precision lost");
            } else {
                //sum = (double) ((long) sum);
            }
        }

        double result = 0;
        double d=1;
        /* note: 使用 double 作循环计数，诸如 1.0 2.0 3.0 ...
        1. 边界`sum`的整数部分未丢失精度
           对于`d<=sum`可能会多计数一次(因为舍入)
        2. 边界`sum`的整数部分已丢失精度
           对于`d<=sum`，存在d在数学中大于sum，但作为double存储时等于sum的情况
         */
        for(; d<=sum; d++) {
            double supplement = 4.0/(2.0*d-1);
            if(supplement == 0.0) break;
            if(d % 2 == 0) {
                result -= supplement;
            } else {
                result += supplement;
            }
        }

        System.out.println(result);
    }


    static void expand_sample() {
        //1111011.011101001011110001101010011111101111100111011011001001
        //1.1110 1101_1101 0010_1111 0001_1010 1001_1111 1011_1110 0111_0110|11001001   +6(000 0000 0110)
        //1.1110 1101_1101 0010_1111 0001_1010 1001_1111 1011_1110 0111_0111            +6(000 0000 0110)
        //0 100 0000 0101  1110 1101_1101 0010_1111 0001_1010 1001_1111 1011_1110 0111_0111
        double df = 123.456;

        //11111111111111111111111111111111111111111111111111111.000
        //1.1111111111111111111111111111111111111111111111111111|000    +52(000 0011 0100)
        //1.1111111111111111111111111111111111111111111111111111        +52(000 0011 0100)
        //0 100 0011 0011  1111 1111_1111 1111_1111 1111_1111 1111_1111 1111_1111 1111_1111
        df = 9_007_199_254_740_991.0;  //2^53-1

        //10 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000.0
        //1.0 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0  +53(000 0011 0101)
        //0 100 0011 0100  0 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 000

        //100000000000000000000000000000000000000000000000000000.0
        //1.0000000000000000000000000000000000000000000000000000|00      +53(000 0011 0101)
        //1.0000000000000000000000000000000000000000000000000000         +53(000 0011 0101)
        //0 100 0011 0100  0000 0000_0000 0000_0000 0000_0000 0000_0000 0000_0000 0000_0000
        df = 9_007_199_254_740_992.0; //2^53


        //11.11111111111111111111111111111111111111111111111111111
        //1.1111111111111111111111111111111111111111111111111111|11  +1
        //10.0000000000000000000000000000000000000000000000000000    +1
        //1.0000000000000000000000000000000000000000000000000000     +2
        //0 100 0000 0001 0000000000000000000000000000000000000000000000000000
        df = 3.9999999999999999;

        //df = Double.NaN;
        //df = 0x0.1p-1022;

        long dl = Double.doubleToLongBits(df);
        System.out.println(Long.toHexString(dl));
        System.out.println(Long.toBinaryString(dl));
        long jm = dl & 0x7f_f0_00_00_00_00_00_00L;  //获取阶码
        if(jm == 0x7f_f0_00_00_00_00_00_00L) {  //无穷或者 NaN
            System.out.println("无穷或者NaN");
        } else if(jm == 0) {  //非规格化小数
            //计算指数值
            short zs = 0x00_01 - 0x03_ff;
            System.out.println(zs);  //-1022
        } else {
            //计算指数值
            long zs = (jm - 0x3f_f0_00_00_00_00_00_00L) & 0x7f_f0_00_00_00_00_00_00L;
            System.out.println(zs << 1 >> 53);  //打印指数值
        }


        //整数部分已丢失精度
        double g = 36028797018963968.0;
        System.out.println(36028797018963969.0 == 36028797018963968.0);  //true
        System.out.println(36028797018963970.0 == 36028797018963968.0);  //true

    }


    static void rk_cal(int n) {
        final int seconds = 365 * 24 * 60 * 60;  //31536000
        int div1 = seconds / 7;  //4505142
        int div2 = seconds / 13;  //2425846
        int div3 = seconds / 45;  //700800
        int supplement = div1 - div2 + div3;  //2780096, no overflow

        int mod1 = seconds % 7;  //6
        int mod1_prev = 0;
        int mod2 = seconds % 13;  //2
        int mod2_prev = 0;
        int mod3 = seconds % 45;  //0
        int mod3_prev = 0;

        long result = 0;
        for(int i=1; i<=n; i++) {
            result += supplement;  //supplement>0, may overflow
            if(result < 0) {  //overflow. result > Long.MAX_VALUE - supplement
                System.out.println("计算第 " + i + " 年时, Long类型overflow");
                break;
            }

            int m1 = (mod1_prev + mod1) / 7;
            mod1_prev = (mod1_prev + mod1) - m1*7;

            int m2 = (mod2_prev + mod2) / 13;
            mod2_prev = (mod2_prev + mod2) - m2*13;

            int m3 = (mod3_prev + mod3) / 45;  //m3 always 0
            mod3_prev = (mod3_prev + mod3) - m3*45;

            result += m1 - m2 + m3;  //If m1-m2>0: may overflow
            if(result < 0) {  //overflow. result > Long.MAX_VALUE - (m1-m2)
                System.out.println("计算第 " + i + " 年时, Long类型overflow");
                break;
            }
            System.out.println("第 " + i + " 年: " + result);
        }

    }


}
