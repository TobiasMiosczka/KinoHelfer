package com.github.tobiasmiosczka.kinohelfer;

import org.junit.Test;

import static org.junit.Assert.*;

public class WageHelperTest {

    @Test
    public void TestCalculateWage() {
        float delta = 0.0001f;

        assertEquals(0.00f, WageHelper.getWage(new Time(0, 0), 8.84f, 00), delta);
        assertEquals(6.00f, WageHelper.getWage(new Time(6, 0), 1.00f, 00), delta);
        assertEquals(5.52f, WageHelper.getWage(new Time(6, 1), 1.00f, 00), delta);
        assertEquals(1.50f, WageHelper.getWage(new Time(1, 0), 1.00f, 50), delta);
    }

    private void assertSameTime(Time t1, Time t2) {
        assertEquals(t1.getInMinutes(), t2.getInMinutes());
    }

    @Test
    public void TestGetEffectiveTime() {
        assertSameTime(new Time(0, 0), WageHelper.getEffectiveDuration(new Time(0, 0)));
        assertSameTime(new Time(1, 2), WageHelper.getEffectiveDuration(new Time(1, 2)));
        assertSameTime(new Time(6, 0), WageHelper.getEffectiveDuration(new Time(6, 0)));
        assertSameTime(new Time(5, 31), WageHelper.getEffectiveDuration(new Time(6, 1)));
        assertSameTime(new Time(7, 0), WageHelper.getEffectiveDuration(new Time(7, 30)));
        assertSameTime(new Time(8, 30), WageHelper.getEffectiveDuration(new Time(9, 0)));
        assertSameTime(new Time(8, 16), WageHelper.getEffectiveDuration(new Time(9, 1)));


        Time time = new Time(12, 34);
        Time time2 = WageHelper.getEffectiveDuration(time);

        assertSameTime(new Time(12, 34), time);
    }

}