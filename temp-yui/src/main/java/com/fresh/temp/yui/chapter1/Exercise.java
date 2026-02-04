package com.fresh.temp.yui.chapter1;

public class Exercise {


    public static void main(String[] argv) {


        System.out.println(0x40_00_00_00 * 2 - 1);  //Integer.MAX_VALUE
        System.out.println((0x40_00_00_00 << 1) - 1);  //Integer.MAX_VALUE

        print('A', 50);

        print2(65536);

        //System.out.println(Double.MAX_VALUE * 2 - 1);

    }

    /**
     * print
     *
     * @param c a Unicode character
     * @param sum the lines of print
     * @throws IllegalArgumentException    If the specified sum is less and equals than 0
     *                                  or If the specified sum is too large, so that {@code 2 * (sum - 1) + 1} overflow
     * @throws OutOfMemoryError            If the allocated array is too large, so that greater than Jvm heap size or the maximum array size defined by Jvm
     */
    static void print(char c, int sum) {
        if(sum <= 0) throw new IllegalArgumentException("the argument sum is less and equal than 0");
        StringBuilder sb = null;
        try {
            sb = new StringBuilder((sum << 1) - 1); //really: 2 * (sum - 1) + 1
        } catch (NegativeArraySizeException e) {  // "2 * (sum - 1) + 1" overflow
            throw new IllegalArgumentException("the argument sum is greater than 1_073_741_820, so that a overflow occur");
        } catch (OutOfMemoryError e) { // the allocated array is too large, so that greater than Jvm heap size or the maximum array size defined by Jvm
            throw e;
        }
        for(int i=0; i<sum; i++) {
            if(i % 2 == 0) {
                if(i == 0) {
                    sb.append(c);
                } else {
                    for (int j = 0; j < 4; j++) {
                        sb.append(c);
                    }
                }
                System.out.printf("%" + (sum + i) + "s\n", sb);
            } else {
                System.out.printf("%" + (sum - i) + "c" + "%" + (2*i) + "c\n", c, c);
            }
        }
    }


    static void print2(int sum) {
        if(sum <= 0) throw new IllegalArgumentException("the argument sum is less and equal than 0");

        int size_sum = intSize(sum);
        int size_sum2;
        if(sum > Integer.MAX_VALUE/sum) { //sum*sum overflow
            size_sum2 = 11;
        } else {
            size_sum2 = intSize(sum*sum);
        }

        int p_width = (size_sum-1) + 3 + 3;
        int q_width = (size_sum2-3) + 3 + 3;
        System.out.printf("%s" + "%" + p_width + "s" + "%" + q_width + "s\n", "a", "a^2", "a^3");

        for(int i=sum; i>=1; i=i/10) {
            int size_i = intSize(i);
            int p = i*i;  //may overflow
            int size_p = intSize(p);
            int q = p*i;  //may overflow
            int size_q = intSize(q);
            System.out.printf("%d" + "%" + (size_p + (size_sum + 3) - size_i) + "d" + "%" + (size_q + (size_sum2 + 3) - size_p) + "d\n", i, p, q);
        }

    }

    final static int [] sizeTable = { 9, 99, 999, 9_999, 99_999, 999_999, 9_999_999, 99_999_999, 999_999_999, Integer.MAX_VALUE };

    // Requires positive x
    static int intSize(int x) {
        if(x == Integer.MIN_VALUE) return 11;
        int supplement = 0;
        if(x < 0) {
            x = (-x);
            supplement = 1;
        }
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i + 1 + supplement;
    }

}
