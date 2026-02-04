package com.fresh.temp.demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlatMapTest {

    public static void main(String[] argv) {

        List<List<Pojo>> flatPojos = new ArrayList<>();
        List<Pojo> l1 = new ArrayList<>();
        l1.add(new Pojo("1"));
        l1.add(new Pojo("2"));

        List<Pojo> l2 = new ArrayList<>();
        l2.add(new Pojo("a a.md"));
        l2.add(new Pojo("b"));

        List<Pojo> l3 = new ArrayList<>();
        l3.add(new Pojo("1"));
        l3.add(new Pojo("d"));

        flatPojos.add(l1);
        flatPojos.add(l2);
        flatPojos.add(l3);

        List<Pojo> nestedLstOne = flatPojos.stream().map(lst -> lst.get(0)).collect(Collectors.toList());
        System.out.println(nestedLstOne.get(1));  //Pojo{ a a.md }

        List<Pojo> result = flatPojos.stream()
                .filter(lst -> lst.get(0).s.equals("1"))
                .flatMap(lst -> lst.stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result);  //[Pojo{ 1 }, Pojo{ 2 }, Pojo{ d }]
    }

    static class Pojo {
        public String s;

        public Pojo(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return "Pojo{ " + s + " }";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pojo pojo = (Pojo) o;
            return Objects.equals(s, pojo.s);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s);
        }
    }

}
