package com.fresh.temp.demo.pt3;

import java.util.TreeMap;

public class Temp2 {

    Temp2(int count) {

    }

    public static void main(String[] argv) {

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        treeMap.put(1, 1);

        System.out.println(treeMap);

    }

    volatile Entry entry;

    int exchange(int v) {
        while(true) {
            Entry entry = this.entry;
            if(entry == null) {
                Entry en = new Entry();
                en.from = v;
                if (CAS(null, en)) {
                    while(en.to == 0) {
                        //park
                    }
                    return en.to;
                }
            }
            if(entry != null) {
                if(CAS(entry, null)) {
                    entry.to = v;
                    //unpark
                    return entry.from;
                }
            }
        }
    }
    boolean CAS(Entry oldVar, Entry newVar) { return false; }

    class Entry {
        volatile int from;
        volatile int to;
    }

}
