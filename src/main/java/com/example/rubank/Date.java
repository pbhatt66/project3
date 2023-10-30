package com.example.rubank;

import java.util.Calendar;

/**
 * Date class represents a calendar date as an object with a year, month, and day.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;
    private static final int MARCH = 3;
    private static final int APRIL = 4;
    private static final int MAY = 5;
    private static final int JUNE = 6;
    private static final int JULY = 7;
    private static final int AUGUST = 8;
    private static final int SEPTEMBER = 9;
    private static final int OCTOBER = 10;
    private static final int NOVEMBER = 11;
    private static final int DECEMBER = 12;

    private static final int DAYS_IN_FEB_LEAP = 29;
    private static final int DAYS_IN_FEB_NONLEAP = 28;
    private static final int DAYS_IN_SHORT_MONTH = 30;
    private static final int DAYS_IN_LONG_MONTH = 31;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUARTERCENTENNIAL = 400;

    /**
     * Constructs a Date object.
     * @param year the year
     * @param month the month
     * @param day the day
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Checks to see if the date is valid.
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        if (year < 0 || month < JANUARY || month > DECEMBER || day < 1) {
            return false;
        }
        if (month == FEBRUARY) {
            if (isLeapYear()) {
                return day <= DAYS_IN_FEB_LEAP;
            } else {
                return day <= DAYS_IN_FEB_NONLEAP;
            }
        }
        if (month == APRIL || month == JUNE || month == SEPTEMBER || month == NOVEMBER) {
            return day <= DAYS_IN_SHORT_MONTH;
        }
        return day <= DAYS_IN_LONG_MONTH;
    }

    /**
     * Checks to see if the year is a leap year.
     * @return true if the year is a leap year, false otherwise
     */
    public boolean isLeapYear() {
        return year % QUARTERCENTENNIAL == 0 || (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0);
    }

    public boolean isFutureDate() {
        Calendar currentCalendar = Calendar.getInstance();
        final Date today = new Date(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH) + 1, currentCalendar.get(Calendar.DAY_OF_MONTH));
        return compareTo(today) > 0;
    }

    public boolean isOver16() {
        Calendar currentCalendar = Calendar.getInstance();
        final Date today = new Date(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH) + 1, currentCalendar.get(Calendar.DAY_OF_MONTH));
        int age = today.year - year;
        if (today.month < month || (today.month == month && today.day < day)) {
            age--;
        }
        return age >= 16;
    }

    public boolean isUnder24() {
        Calendar currentCalendar = Calendar.getInstance();
        final Date today = new Date(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH) + 1, currentCalendar.get(Calendar.DAY_OF_MONTH));
        int age = today.year - year;
        if (today.month < month || (today.month == month && today.day < day)) {
            age--;
        }
        return age < 24;
    }

    /**
     * Compares this date to another date.
     * @param other the other date
     * @return a negative integer if this date is before the other date, a positive integer if this date is after the other date, and 0 if the dates are equal
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof Date date))
            return false;

        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

}

