package com.rty.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getBeforeDay(String date, int day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            Date d = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DATE, day);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        return sdf.format(new Date());
    }
}
