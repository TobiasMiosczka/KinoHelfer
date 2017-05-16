package com.github.tobiasmiosczka.kinohelfer;

import android.support.annotation.NonNull;

public class Time implements Comparable<Time>{

    public static final int MINUTES_PER_HOUR = 60;
    public static final int HOURS_PER_DAY = 24;

    private int hours,
                minutes;

    public static Time difference(Time from, Time to) {
        int minutesFrom = from.getInMinutes();
        int minutesTo = to.getInMinutes();

        if (minutesFrom > minutesTo)
            minutesTo += HOURS_PER_DAY * MINUTES_PER_HOUR;

        int minutesDiff = minutesTo - minutesFrom;

        return new Time(0, minutesDiff);
    }

    private static String get2DigitsString(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    private void normalize() {
        hours += minutes / MINUTES_PER_HOUR;
        minutes %= MINUTES_PER_HOUR;
        if (minutes < 0) {
            minutes += MINUTES_PER_HOUR;
            --hours;
        }
        hours %= HOURS_PER_DAY;
        if (hours < 0)
            hours += HOURS_PER_DAY;
    }

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        normalize();
    }

    public Time(Time time) {
        this.hours = time.getHours();
        this.minutes = time.getMinutes();
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
        normalize();
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        normalize();
    }

    public void addMinutes(int deltaMinutes) {
        this.minutes += deltaMinutes;
        normalize();
    }

    public int getInMinutes() {
        return minutes + hours * MINUTES_PER_HOUR;
    }

    public float getInHours() {
        return (float)hours + (float)minutes / (float)MINUTES_PER_HOUR;
    }

    @Override
    public String toString() {
        return get2DigitsString(hours) + ":" + get2DigitsString(minutes);
    }

    @Override
    public int compareTo(@NonNull Time that) {
        return (this.getInMinutes() - that.getInMinutes());
    }
}
