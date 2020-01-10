package com.dlc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static boolean sameDate(Date d1, Date d2) {
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static String format(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            return format(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某天的零时零分零秒
     * @return
     */
    public static Date getCurrentDate(Integer currentDaytInt){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, currentDaytInt);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取时间差(天数差，小时差，分钟差)
     */
    public static int getHours(Date fromDate,Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        int hours = (int) ((to - from)/(1000 * 60 * 60));
        return hours;
    }

    /**
     * 获取时间差(天数差，小时差，分钟差)
     */
    public static int getHours(Long from,Long to){
        /*long from = fromDate.getTime();
        long to = toDate.getTime();*/
        int hours = (int) ((to - from)/(1000 * 60 * 60));
        return hours;
    }


    /**
     * 获取时间差(天数差，小时差，分钟差)
     */
    public static int getMin(Date fromDate,Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        int min = (int) ((to - from)/(1000 * 60));
        return min;
    }


    /**
     * 获取时间差(天数差，小时差，分钟差)
     */
    public static int getMin(Long from,Long to){
        /*long from = fromDate.getTime();
        long to = toDate.getTime();*/
        int min = (int) ((to - from)/(1000 * 60));
        return min;
    }

    /**
     * 增加日期天数()
     */
    public static Date addDate(Date startTime,int count){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(calendar.DATE,count); //把日期往后增加一天,整数  往后推,负数往前移动
        Date toTime =calendar.getTime(); //这个时间就是日期往后推一天的结果
        return toTime;
    }

    //String类型的时间转为Date
    static public  Date toDate(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
