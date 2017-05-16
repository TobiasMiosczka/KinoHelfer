package com.github.tobiasmiosczka.kinohelfer;

public class WageHelper {

    public static Time getEffectiveDuration(Time duration) {
        Time effectiveDuration = new Time(duration);
        if (duration.getInHours() > 9.0f)
            effectiveDuration.addMinutes(-45);
        else if (duration.getInHours() > 6.0f)
            effectiveDuration.addMinutes(-30);
        return effectiveDuration;
    }

    public static float businessRounding(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }

    public static float getWage(Time duration, float hourlyWage, float bonusInPercents) {
        Time effektiveDuration = getEffectiveDuration(duration);
        float wage = effektiveDuration.getInHours() * hourlyWage;
        wage *= 1 + (bonusInPercents / 100.0f);
        return businessRounding(wage);
    }
}
