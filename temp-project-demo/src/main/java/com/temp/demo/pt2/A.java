package com.temp.demo.pt2;

public class A {

    public static void main(String[] args) {
        int[] a = {1, 3, 543, 1, 2, 4};
        ArrTraversal.of(a).forEach(ArrHandler::print);
        insert_sort(a);
        ArrTraversal.of(a).forEach(ArrHandler::print);

        int[] b = {-1, 123, -123, 231123213, 11, -2};
        ArrTraversal.of(b).forEach(ArrHandler::print);
        bubble_sort(b);
        ArrTraversal.of(b).forEach(ArrPosHandler.of(2)::print);
    }

    static void insert_sort(int[] a) {
        if(a == null || a.length == 0 || a.length == 1) return ;

        for(int i=1; i < a.length; i++) {
            int k = a[i];
            int j = i - 1;

            while (j >= 0 && a[j] > k) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = k;
        }
    }

    static void bubble_sort(int[] a) {
        if(a == null) return ;

        for(int i=a.length-1; i > 0; i--) {
            for(int j=0; j < i; j++) {
                if(a[j] > a[j + 1]) {
                    int k = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = k;
                }
            }
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
