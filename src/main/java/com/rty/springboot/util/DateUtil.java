package com.rty.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * JAVA8中好用的时间api :LocalDate
 */
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

    /**
     * 获取当前的地区的当天是该星期的第几天
     */
    public static int getDayNumOnWeek(){
        return LocalDate.now().getDayOfWeek().getValue();
    }

}
