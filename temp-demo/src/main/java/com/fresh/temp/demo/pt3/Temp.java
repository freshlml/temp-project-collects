package com.fresh.temp.demo.pt3;

import java.util.*;

public class Temp {
    private static final ThreadGroup qopGroup = new ThreadGroup("qop");
    static {
        qopGroup.setDaemon(true);
        qopGroup.setMaxPriority(Thread.MAX_PRIORITY - 1);
    }

    public static void main(String[] argv) {


        Thread thread = new Thread(qopGroup, new Runnable() {
           @Override
           public void run() {
               int[] a = {2, 3, 6, 234, 1, 9};
               insertion_sort(a);
               Arrays.stream(a).forEach(ArrayPrinter.of(a.length)::print);
           }
        }, "thread1");
        String name = thread.getName()    ;       //thread1
        int priority = thread.getPriority();      //5
        boolean daemon = thread.isDaemon();       //false
        ClassLoader contextClassLoader = thread.getContextClassLoader();  //AppClassLoader
        ThreadGroup group = thread.getThreadGroup();
        thread.start();

        while(thread.isAlive()) {
            try {
                thread.join();                  // could replace with LockSupport.park
            } catch (InterruptedException e) {
                //if(!thread.isAlive()) break;
            }
        }
        System.out.println("##################################");
        int[] a = new int[] {2, 3, 6, 234, 1, 9};
        merge_sort(a);
        Arrays.stream(a).forEach(ArrayPrinter.of(a.length)::print);

    }

    static int partition(int[] a, int p, int r) {
        int pos = determinePosition(p, r);
        int e = a[pos];
        int j = p - 1, k = p;

        while(k < pos) {
            if(a[k] <= e) {
                j++;

                int ex = a[j];
                a[j] = a[k];
                a[k] = ex;
            }
            k++;
        }

        if(++j != pos) {
            int ex = a[j];
            a[j] = e;
            a[pos] = ex;
        }

        return j;
    }
    static int determinePosition(int p, int r) {
        return r - 1;
    }

    static void insertion_sort(int[] a) {
        //check parameter ...
        for(int i=1; i < a.length; i++) {
            int ka = a[i];
            int j = i - 1;
            while(j >= 0 && a[j] > ka) {
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = ka;
        }

    }

    static void merge_sort(int[] a) {
        merge_sort(a, 0, a.length);
    }
    static void merge_sort(int[] a, int begin, int end) {
        int n = end - begin;
        if(n == 1) return;

        int mi = begin + n/2;
        merge_sort(a, begin, mi);
        merge_sort(a, mi, end);

        merge(a, begin, mi, end);
    }
    static void merge(int[] a, int p, int q, int r) {
        int[] left = new int[q-p];
        System.arraycopy(a, p, left, 0, left.length);
        int[] right = new int[r-q];
        System.arraycopy(a, q, right, 0, right.length);

        for(int i = 0, j = 0, k = p; k < r; k++) {
            if(j == right.length || (i != left.length && left[i] < right[j])) {
                a[k] = left[i];
                i++;
            } else if(i == left.length || left[i] > right[j]) {
                a[k] = right[j];
                j++;
            } else {  //j != right.length && i != left.length && left[i] == right[j]
                a[k] = left[i];
                i++;
                a[++k] = right[j];
                j++;
            }
        }

    }

    static void blMaxSubArray(int[] a) {
        int i = 0;
        int sum = a[i];
        int p = i;
        int q = i;
        for(; i < a.length; i++) {
            int accum = 0;
            for(int j=i; j < a.length; j++) {
                accum += a[j];
                if(sum < accum) {
                    sum = accum;
                    p = i;
                    q = j;
                }
            }
        }
    }

    private static class ArrayPrinter {
        private final int length;
        private int count = 1;

        ArrayPrinter(int length) {
            //assert length > 0
            this.length = length;
        }

        <T> void print(T t) {
            System.out.print(t);
            if(count < length) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
            count++;
        }

        static ArrayPrinter of(int length) {
            return new ArrayPrinter(length);
        }
    }

    public static class Tempiii {
        private volatile int a;
        private volatile int c;
        public int getA() {
            return this.a;
        }
    }

}



