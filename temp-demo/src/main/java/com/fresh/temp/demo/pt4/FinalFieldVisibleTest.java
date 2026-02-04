package com.fresh.temp.demo.pt4;


import lombok.SneakyThrows;

public class FinalFieldVisibleTest {

    static class MyClass {
        int x;
        final int y;
        final Model model;

        public MyClass() {
            x = 1;
            y = 2;
            model = new Model(3);
        }

        //https://javamex.com/tutorials/synchronization_final.shtml
        //todo: 查看编译后的字节码，看看添加了什么特别的指令
        //todo: final parameter, final local variable
        public void read() {
            //one thread
            MyClass myClass = new MyClass();

            //another thread
            int x = myClass.x;         //could see 0
            int y = myClass.y;         //guaranteed to 2
            int z = myClass.model.z;   //guaranteed to 3, even the field z of Model is not final

            //thread 3
            myClass.model.z = 4;
            //thread 4
            int zz = myClass.model.z; //also guaranteed to 4, no

        }
    }
    static class Model {
        int z;
        Model(int z) {
            this.z = z;
        }
    }


    private static int x;
    public static void main(String[] argv) {

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("update begin...");
                x = 1;                                //线程本地缓存刷到主存，不会失效化其他线程中对相同目标的本地缓存
                System.out.println("update end...");
            }
        }.start();

        int i = 0;
        while(x == 0) {
            if(i == 0) {
                System.out.println("running begin...");
                i = 1;
            }
            System.out.println("");  //清空本地缓存, 从而去主存获取 x 的值
        }
        System.out.println("running end...");  //不执行

    }

}
