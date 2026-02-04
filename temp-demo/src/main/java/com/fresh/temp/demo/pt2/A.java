package com.fresh.temp.demo.pt2;


public class A {

    public static void main(String[] args) {
        int[] a = {1, 2, -11, 123, 1, 3, -122};

        /*insert_sort(a);
        ArrTraversal.of(a).forEach(ArrHandler::print);*/

        /*System.out.println("$##################");
        merge_sort(a);
        ArrTraversal.of(a).forEach(ArrHandler::print);*/

        System.out.println(AA.a);
        System.out.println(BB.a);
        System.out.println(BB.b);
        System.out.println(AA.b);
    }

    static class AA {
        static int b = BB.b;
        static int a = 1;
    }
    static class BB {
        static int a = AA.a;
        static int b = 2;
    }

    static void blMax(int[] a) {

        int result = a[0];
        for(int i=0; i < a.length; i++) {
            int accum = 0;
            for(int j=i; j < a.length; j++) {
                accum += a[j];
                if(accum > result) {
                    result = accum;
                }
            }
        }


    }


    static void merge_sort(int[] a) {
        if(a == null) return;
        merge_sort(a, 0, a.length);
    }

    static void merge_sort(int[] a, int begin, int end) {
        //assert a != null
        int n = end - begin;
        if(n <= 1) return;

        int mi = begin + n/2;
        merge_sort(a, begin, mi);
        merge_sort(a, mi, end);

        merge(a, begin, mi, end);
    }

    static void merge(int[] a, int p, int q, int r) {
        //assert a != null
        //assert 0 <= p <= q <= r <= a.length
        int[] left = new int[q-p];   //q-p may 0
        System.arraycopy(a, p, left, 0, left.length);
        int[] right = new int[r-q];  //r-q may 0
        System.arraycopy(a, q, right, 0, right.length);

        for(int k=p, i=0, j=0; k < r; k++) {

            if(j >= right.length || (i < left.length && left[i] <= right[j])) {
                a[k] = left[i];
                i++;
            } else {
                a[k] = right[j];
                j++;
            }
        }
    }


    static void insert_sort(int[] a) {
        if(a == null) return;

        for(int i=1; i < a.length; i++) {
            int k = a[i];
            int j = i-1;
            while(j >= 0 && a[j] > k) {
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = k;
        }
    }

    static class ArrTraversal {
        private int[] as;
        ArrTraversal(int[] as) {
            this.as = as;
        }

        static ArrTraversal of(int[] as) {
            return new ArrTraversal(as);
        }

        @FunctionalInterface
        interface ElementHandler {
            void handle(int a, int limit, int pos);
        }

        public void forEach(ElementHandler elementHandler) {
            int len = as.length;
            for(int i=0; i<len; i++) {
                elementHandler.handle(as[i], len-1, i);
            }
        }
    }

    static class ArrHandler {
        static void print(int a, int limit, int pos) {
            System.out.print(a);
            if(pos != limit) System.out.print(", ");
            else System.out.println();
        }
    }

    static class ArrPosHandler {
        int p;
        int weight = 0;
        ArrPosHandler(int p) {
            this.p = p;
        }

        static ArrPosHandler of(int p) {
            return new ArrPosHandler(p);
        }

        void print(int a, int limit, int pos) {
            printElement(a, pos);
            if(pos != limit)
                printSep(pos);
            else
                printEnd();
        }

        void printElement(int a, int pos) {
            System.out.print(a);
            if(pos < this.p)
                this.weight += String.valueOf(a).length();
        }
        void printSep(int pos) {
            System.out.print(", ");
            if(pos < this.p)
                this.weight += 2;
        }
        void printEnd() {
            System.out.println();
            for(int i=0; i < weight; i++) {
                System.out.print(" ");
            }
            if(this.p < 0) {
                System.out.println("print p in [" + this.p + "]");
            } else {
                System.out.println("🚩");
            }
        }
    }

}

