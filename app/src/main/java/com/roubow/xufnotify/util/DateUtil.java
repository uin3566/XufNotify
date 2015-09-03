package com.roubow.xufnotify.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xuf on 2015/8/30.
 */
public class DateUtil {

    public static String mDateFormat = "yyyy年MM月dd日 HH:mm";

    private static Calendar calendar = Calendar.getInstance();

    public static String getDateString(Date date){
        String ret;
        DateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        ret = dateFormat.format(date);

        return ret;
    }

    public static String getCurrentDateString(){
        String ret;
        Date date = new Date(System.currentTimeMillis());
        ret = getDateString(date);

        return ret;
    }

    public static String getDateStringYMD(Date date){
        String ret;
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        ret = dateFormat.format(date);

        return ret;
    }

    public static boolean isDifferentDay(Date date1, Date date2){
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        if (year1 != year2){
            return true;
        } else {
            if (day1 != day2){
                return true;
            } else {
                return false;
            }
        }
    }

    public static int getDaysOfMonth(int year, int month){
        int ret = -1;
        if (month == 1 || month == 3 || month == 5 || month == 7
                       || month == 8 || month == 10 || month == 12){
            ret = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11){
            ret = 30;
        } else if (month == 2){
            if (isLeapYear(year)){
                ret = 29;
            } else {
                ret = 28;
            }
        }

        return ret;
    }

    private static boolean isLeapYear(int year){
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
            return true;
        }
        return false;
    }
}
