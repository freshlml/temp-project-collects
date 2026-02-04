package com.fresh.temp.demo.time;

import java.time.LocalDate;
import java.time.Year;

import static java.time.temporal.ChronoField.YEAR;

public class TestEpochDay {

    public static void main(String[] argv) {

        long start = System.currentTimeMillis();
        System.out.println(ofEpochDay(1099999));
        System.out.println(System.currentTimeMillis()-start);

        start = System.currentTimeMillis();
        System.out.println(ofEpochDay2(1099999));
        System.out.println(System.currentTimeMillis()-start);

        start = System.currentTimeMillis();
        System.out.println(LocalDate.ofEpochDay(1099999));
        System.out.println(System.currentTimeMillis()-start);
        System.out.println("---------------------------------");
        System.out.println(ofEpochDay(-3670));
        System.out.println(ofEpochDay2(-3670));
        System.out.println(LocalDate.ofEpochDay(-3670));

    }


    static LocalDate ofEpochDay2(long epochDay) {
        long yearDlt = (400 * epochDay) / 146097;  // if 400*epochDay overflow, throw exception
        long year = 1970 + yearDlt;
        long rds = epochDay - (yearDlt*365 + countLeapYear2(1970, year));

        int doy;
        if(rds < 0) {
            year--;
            doy = dayLengthOfYear(year) + (int)rds + 1;
        } else {
            int lpy = dayLengthOfYear(year);
            year += ((int)rds) / lpy;
            doy = ((int) rds) % lpy + 1;
        }

        int y = YEAR.checkValidIntValue(year);
        return LocalDate.ofYearDay(y, doy);
    }
/*
    static LocalDate ofEpochDay2(long epochDay) {
        if(epochDay > 0) {
            long yearDlt = (400 * epochDay) / 146097;  // if 400*epochDay overflow, throw exception
            long year = 1970 + yearDlt;
            long rds = epochDay - (yearDlt*365 + countLeapYear(1970, year));

            int doy;
            if(rds < 0) {
                year--;
                doy = dayLengthOfYear(year) + (int)rds + 1;
            } else {
                int lpy = dayLengthOfYear(year);
                year += ((int)rds) / lpy;
                doy = ((int) rds) % lpy + 1;
            }

            int y = YEAR.checkValidIntValue(year);
            return LocalDate.ofYearDay(y, doy);
        } else {
            long yearDlt = Math.floorDiv(400 * epochDay, 146097);  // if 400*epochDay overflow, throw exception
            long year = 1970 + yearDlt;
            long rds = epochDay - (yearDlt*365 + -countLeapYear(year, 1970));

            int doy;
            if(rds < 0) {
                year--;
                doy = dayLengthOfYear(year) + (int)rds + 1;
            } else {
                int lpy = dayLengthOfYear(year);
                year += ((int)rds) / lpy;
                doy = ((int) rds) % lpy + 1;
            }

            int y = YEAR.checkValidIntValue(year);
            return LocalDate.ofYearDay(y, doy);
        }

    }*/

    static LocalDate ofEpochDay(long epochDay) {
        long y = 1970;
        long dayOfYear;
        long remainedDays = epochDay;
        if(epochDay >= 0) {
            while(true) {
                long rawYears = remainedDays / 366;
                long rawDays = remainedDays % 366;
                long addedDays = rawYears - countLeapYear(y, y + rawYears);

                remainedDays = rawDays + addedDays;
                y += rawYears;

                if(remainedDays == 0) {
                    dayOfYear = 1;
                    break;
                } else if(remainedDays < dayLengthOfYear(y)) {
                    dayOfYear = remainedDays + 1;
                    break;
                } else if(remainedDays == dayLengthOfYear(y)) {
                    y += 1;
                    dayOfYear = 1;
                    break;
                }
            }
        } else {
            while(true) {
                long rawYears = remainedDays / 366;
                long rawDays = remainedDays % 366;
                long addedDays = rawYears + countLeapYear(y + rawYears, y);

                remainedDays = rawDays + addedDays;
                y += rawYears;

                if (remainedDays == 0) {
                    dayOfYear = 1;
                    break;
                } else if (remainedDays > -dayLengthOfYear(y-1)) {
                    y -= 1;
                    dayOfYear = dayLengthOfYear(y) + remainedDays + 1;
                    break;
                } else if (remainedDays == -dayLengthOfYear(y-1)) {
                    y -= 1;
                    dayOfYear = 1;
                    break;
                }
            }
        }

        int year = YEAR.checkValidIntValue(y);
        return LocalDate.ofYearDay(year, (int) dayOfYear);
    }


    static int dayLengthOfYear(long year) {
        return Year.isLeap(year) ? 366 : 365;
    }
    static long countLeapYear2(long fromYear, long toYear) {
        if(fromYear <= toYear) {
            return countLeapYear(fromYear, toYear);
        } else {
            return -countLeapYear(toYear, fromYear);
        }
    }
    static long countLeapYear(long fromYear, long toYear) {
        //assert fromYear <= toYear
        if(toYear < 0) {
            return countLeapYear(fromYear) - countLeapYear(toYear);
        } else if(fromYear < 0) {
            return countLeapYear(fromYear) + countLeapYear(toYear);
        } else {
            return countLeapYear(toYear) - countLeapYear(fromYear);
        }
    }
    static long countLeapYear(long year) {
        if(year >= 0) { //[0, year)
            //return (year+3)/4 - (year+99)/100 + (year+399)/400;  //year+3、year+99、year+399 may overflow
            long y4 = year / 4 + (year % 4 + 3 % 4) / 4;
            long y100 = year / 100 + (year % 100 + 3 % 100) / 100;
            long y400 = year / 400 + (year % 400 + 3 % 400) / 400;
            return y4 - y100 + y400;
        } else {        //[year, 0)
            return year/-4 - year/-100 + year/-400;
        }
    }
}
