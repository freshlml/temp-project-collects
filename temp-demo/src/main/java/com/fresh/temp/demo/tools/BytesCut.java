package com.fresh.temp.demo.tools;


import java.util.Date;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.OptionalInt;
import java.util.stream.IntStream;



public class BytesCut {

    public static <E> void main(Date[] argv) {
        int l = 0x9e3779b9;
        for(;;) {
            System.out.println(l = l + l);
            if(l < 0) break;
        }


        //cutBytes("中".getBytes(), -1);
        //cutBytes("".getBytes(), 100);
        //cutBytes("中".getBytes(), 0);
        //cutBytes("中".getBytes(), 1);
        //cutBytes("中".getBytes(), 3);
        //cutBytes("中".getBytes(), 4);
        //cutBytes("中?".getBytes(), 4);
        byte[] bytes = "中𝕆fkdsfskdf中".getBytes();  //file.encoding 配置值(一般为 UTF-8) or iso-8859-1
        System.out.println(cutBytes(bytes, 100));

        byte[] bytes2 = new byte[17];
        System.arraycopy(bytes, 0, bytes2, 0, 2);
        System.arraycopy(bytes, 4, bytes2, 2, bytes2.length - 2);
        System.out.println(cutBytes(bytes2, 7));
        System.out.println(cutBytes2(bytes2, 4));




        char c = 'J';
        for(int i=0; i<4; i++) {
            switch (i) {
                case 3:
                    System.out.printf("%2c%2c\n", c, c);
                    break;
                case 2:
                    System.out.printf("%c%4c\n", c, c);
                    break;
                case 1:
                case 0:
                    System.out.printf("%5c\n", c);
                    break;
            }
        }

        double d = 2 / 10.0;
        //0.0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011
        //0.11 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 00|11 0011  -2
        //0.11 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 01          -2
        //0 xxx xxxx xxxx  11 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 0011 01
        System.out.println(d);
        System.out.printf("%.16f\n", d);
        System.out.printf("%a\n", d);

        BigDecimal bd = new BigDecimal("2");
        System.out.println(bd.divide(new BigDecimal("10.0"), new MathContext(16, RoundingMode.HALF_UP)));


        bd = new BigDecimal("99.99999999");
        System.out.println(bd.multiply(bd));

        System.out.println(bd.multiply(bd,
                new MathContext((bd.precision()-bd.scale()) + (bd.precision()-bd.scale()) + 1, RoundingMode.DOWN)));

        System.out.println(c2i('a'));
        System.out.println(c2i('d'));
        System.out.println(c2i('s'));
        System.out.println(c2i('y'));
        System.out.println(c2i('z'));


        IntStream intStream = IntStream.range(1, 100);  //[1, 100)

        OptionalInt result = intStream.parallel().map(i -> {
            System.out.println(i + ", " + Thread.currentThread().getName());
            return i;
        }).reduce(Integer::sum);

        System.out.println(result.orElseGet(() -> 0));


        int j = 1;

        class Local {
            private int i = j;
        }

        m(new Temp() {
            private int ii = j;
            int am() {
                System.out.println("am");
                return ii;
            }
        });

    }

    public class Mem {

    }
    
    public static class Temp {
        int am() { return 0; }
    }
    static void m(Temp temp) {
        System.out.println(temp.am());
    }



    static int c2i(char c) {
        if(c < 'a' || c > 'z') throw new IllegalArgumentException();

        int d = c - 'a';

        if(d == 25) {
            return 2 + (d-2)/3;
        } else if(d >= 18) {
            return 2 + (d-1)/3;
        } else {
            return 2+ d/3;
        }

    }


    static char[] i2c(int codePoint) {
        try {
            return Character.toChars(codePoint);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    static class D<T extends Integer> {

    }



    /**
     * 根据 {@code reservedLength} 指示的下标来切割 {@code utf-8} 编码的字节序列 {@code bytes}。
     * 切割下来的字节序列保存在一个新创建的字节数组中返回。
     *
     * 如果 {@code reservedLength} 大于或等于 {@code bytes.length}，在 {@code bytes.length} 处切割(不包括边界)。
     * 如果 {@code reservedLength} 小于 {@code bytes.length}，在 {@code reservedLength} 处切割(不包括边界)。
     * 特别地，当切口切断了字符时，切口将沿下标变小的方向涌动到被切断字符的首字节位置。
     *
     * 如果 {@code bytes} 不是一个完整的或者不是 {@code utf-8} 编码的字节序列，无法保证切割是逻辑正确的。
     *
     * @param bytes the specified bytes
     * @param reservedLength the max reserved length
     * @return a new created non-null byte-array
     * @throws NullPointerException if the specified bytes is null
     * @throws IllegalArgumentException if the specified reservedLength is less {@code 0}
     */
    private static byte[] cutBytes(byte[] bytes, int reservedLength) {
        if(reservedLength < 0) throw new IllegalArgumentException("reservedLength(" + reservedLength + ") < 0");

        int pos = bytes.length;
        if(reservedLength < bytes.length) {
            pos = reservedLength;
            while(isBelongTo(bytes[pos]) && pos > 0) {
                pos--;
            }
        }

        byte[] resultBytes = new byte[pos];
        System.arraycopy(bytes, 0, resultBytes, 0, pos);

        return resultBytes;
    }

    private static boolean isBelongTo(byte b) {
        return (b & 0xf0) != 0xf0 &&
               (b & 0xe0) != 0xe0 &&
               (b & 0xc0) != 0xc0 &&
               (b & 0x80) == 0x80;
    }
    
    private static byte[] cutBytes2(byte[] bytes, int reservedLength) {
        if(reservedLength < 0) throw new IllegalArgumentException("reservedLength(" + reservedLength + ") < 0");

        int pos = 0, nextPos;
        while(pos < bytes.length) {

            int charLen = count(bytes[pos]);
            if((nextPos = pos + charLen) > reservedLength || nextPos > bytes.length) break;

            pos = nextPos;
        }

        byte[] resultBytes = new byte[pos];
        System.arraycopy(bytes, 0, resultBytes, 0, pos);

        return resultBytes;
    }

    private static int count(byte b) {
        int c = 1;
        if((b & 0xf0) == 0xf0) c = 4;
        else if((b & 0xe0) == 0xe0) c = 3;
        else if((b & 0xc0) == 0xc0) c = 2;
        else if((b & 0x80) == 0x80) c = 1;
        return c;
    }

}


