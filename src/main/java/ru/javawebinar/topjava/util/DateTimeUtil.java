package ru.javawebinar.topjava.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class DateTimeUtil {
    private static DateTimeFormatter formatter;

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    //TODO add formatter initialisation
    //TODO add formatter toString()
}
