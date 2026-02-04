package com.fresh.temp.demo.pt4;

public class VolatileTest {

    //Full volatile Visibility Guarantee:
    //1.If Thread A writes to a volatile variable and Thread B subsequently reads the same volatile variable,
    //  then all variables visible to Thread A before writing the volatile variable,
    //  will also be visible to Thread B after it has read the volatile variable.
    //2.If Thread A reads a volatile variable, then all all variables visible to Thread A
    //  when reading the volatile variable will also be re-read from main memory.

    static class MyClass {
        private int years;
        private int months;
        private volatile int days;

        public void update(int years, int months, int days){
            this.years  = years;
            this.months = months;
            this.days   = days;
            // when a value is written to days, then all variables visible to the thread are also written to main memory.
            // That means, that when a value is written to days, the values of years and months are also written to main memory.
        }

        public int totalDays() {
            // When reading the value of days, the values of months and years are also read into main memory.
            // Therefore you are guaranteed to see the latest values of days, months and years with the above read sequence.
            int total = this.days;
            total += months * 30;
            total += years * 365;
            return total;
        }

    }

}
