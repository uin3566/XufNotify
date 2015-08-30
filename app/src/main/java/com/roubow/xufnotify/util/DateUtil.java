package com.roubow.xufnotify.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xuf on 2015/8/30.
 */
public class DateUtil {

    public static String mDateFormat = "yyyy年MM月dd日 HH:mm";

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
