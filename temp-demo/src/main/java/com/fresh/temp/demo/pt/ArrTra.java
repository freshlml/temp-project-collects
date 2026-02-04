package com.fresh.temp.demo.pt;

import com.fresh.common.utils.ObjectUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ArrTra<T> {
    T[] t;

    public static void main(String[] argv) {

        ArrTra<?> result = ArrTra.result();

        ArrTra.a(new int[] {1, 2, 3, 4, 5});

        ArrTra.a(new int[][] {{1, 2}, {3, 4, 5}});

        ArrTra.a(new Integer[] {1, 2, 3, 4, 5});

        ArrTra.a(new A[] {new A(), new A()});

        ArrTra.a(new A[][] {new A[1], new A[2]});

        int[] a = new int[0];
        System.out.println(a);
    }

    public static <U> ArrTra<U> result() {
        return new ArrTra<>();
    }

    static <U> void b(U[] ua) {
        a(ua);
    }

    static <ARRAY_TYPE> void a(ARRAY_TYPE array) {
        Class<?> arrayType = array.getClass();
        assert arrayType.isArray();

        Class<?> componentType = arrayType.getComponentType();

        //数组元素如何取？Unsafe api, 直接操作内存，哈哈.
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int base = unsafe.arrayBaseOffset(arrayType);
        int scale = unsafe.arrayIndexScale(arrayType);

        long offset;
        for(int i=0; i < Array.getLength(array); i++) {
            offset = base + i*scale;
            Object e = null;
            if(boolean.class == componentType) {
                e = unsafe.getBoolean(array, offset);
            } else if(byte.class == componentType) {
                e = unsafe.getByte(array, offset);
            } else if(short.class == componentType) {
                e = unsafe.getShort(array, offset);
            } else if(char.class == componentType) {
                e = unsafe.getChar(array, offset);
            } else if(int.class == componentType) {
                e = unsafe.getInt(array, offset);
            } else if(long.class == componentType) {
                e = unsafe.getLong(array, offset);
            } else if(float.class == componentType) {
                e = unsafe.getFloat(array, offset);
            } else if(double.class == componentType) {
                e = unsafe.getDouble(array, offset);
            } else {
                e = unsafe.getObject(array, offset);
            }

            System.out.println(e);
        }

        //java.lang.reflect.Array 提供了此功能
        /*for(int i=0; i < Array.getLength(array); i++) {
            Object e = Array.get(array, i);
            System.out.println(e);
        }*/

    }


}

class A {}


class SharedVariable {
    private static final String str = "daASDfgSDF";
    private static final Map<String, String[]> map;

    static {
        Map<String, String[]> hmp = new HashMap<>();
        for(int i=0; i<str.length(); i+=5) {
            String elm = str.substring(i, Math.min((i + 5), str.length()));
            //if(elm.length() != 5) continue;  //ill-formed

            String key = elm.substring(0, Math.min(2, elm.length()));
            String[] val = elm.length()>2 ? new String[elm.length() - 2] : null;
            for(int j=2; j<elm.length(); j++) {
                val[j-2] = elm.substring(j, j+1);
            }
            hmp.put(key, val);
        }

        map = Collections.unmodifiableMap(hmp);
    }


    public static String[] key(String key) {
        String[] val = map.get(key);
        return Arrays.copyOf(val, val.length);
    }

}

class Temp implements Cloneable {
    private int i;
    private String s;

    public Temp(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temp temp = (Temp) o;
        return i == temp.i &&
                Objects.equals(s, temp.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, s);
    }
}

class UnmodifiableTemp extends Temp {
    public UnmodifiableTemp(Temp temp) {
        super(temp.getI(), temp.getS());
    }

    @Override
    public int getI() {
        return super.getI();
    }

    @Override
    public void setI(int i) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public String getS() {
        return super.getS();
    }

    @Override
    public void setS(String s) {
        throw new UnsupportedOperationException("not supported");
    }
}

class SafeTemp {
    private final int i;
    private final String s;

    public SafeTemp(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public String getS() {
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof SafeTemp)) return false;

        SafeTemp that = (SafeTemp) o;
        return this.i == that.i &&
                ObjectUtils.objEquals(this.s, that.s);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += Integer.hashCode(this.i);
        hash += ObjectUtils.objHashCode(this.s);
        return hash;
    }

    class Builder {
        private int i;
        private String s;

        public Builder setS(String s) {
            this.s = s;
            return this;
        }
        public Builder setI(int i) {
            this.i = i;
            return this;
        }
        public SafeTemp build() {
            return new SafeTemp(this.i, this.s);
        }
    }
}

class SharedVariable2 {
    private static final ConcurrentMap<String, Temp> map1 = new ConcurrentHashMap<>();
    public static final ConcurrentMap<String, SafeTemp> map2 = new ConcurrentHashMap<>();


    public static Temp getReadOnly(String key) {
        Temp temp = map1.get(key);
        if(temp != null) {
            return new UnmodifiableTemp(temp);
        }
        return null;
    }

    public static Temp get(String key) {
        Temp temp = map1.get(key);
        if(temp != null) {
            return (Temp) temp.clone();
        }
        return null;
    }

    public static void put(String key, Temp temp) {
        map1.put(key, temp);
    }

}
