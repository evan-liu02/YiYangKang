package com.anju.yyk.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author LeoWang
 *
 * @Package com.leo.common.utils
 *
 * @Description 时间相关方法工具类
 *
 * @Date 2019/5/6 11:16
 *
 * @modify:
 */
public class TimeUtil {
    /** 几点几分*/
    public final static String FORMAT_HH_MM = "HH:mm";
    /** xxxx-xx-xx*/
    public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /** xxxx-xx-xx hh:mm:ss*/
    public final static String FORMAT_YYYY_MM_DD_HH__MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** xx月xx日 xx:xx*/
    public final static String FORMAT_MM_DD_HH_MM = "MM月dd日 HH:mm";

    /**
     * 获得时间戳
     * @return 时间戳字符串
     */
    public static String getStringTimestamp(){
        return String.valueOf((int) (System.currentTimeMillis()/1000));
    }

    /**
     * 获得整型时间戳
     * @return 时间戳
     */
    public static int getTimestamp(){
        return (int) (System.currentTimeMillis()/1000);
    }


    /**
     * 获得年月日
     * @return	xxxx-xx-xx
     */
    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD,
                Locale.getDefault());
        return format.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 根据时间戳获得相应格式日期
     * @param timestamp Long
     * @return 日期
     */
    public static String getFormatTime(String formatter, long timestamp){
        SimpleDateFormat format = new SimpleDateFormat(formatter,
                Locale.getDefault());
        return format.format(new Date(timestamp));
    }

    /**
     * 获取格式化的时间
     *
     * @param formatter 格式
     * @param time	php time
     * @return 日期
     */
    public static String getFormatTime(String formatter, String time) {
        SimpleDateFormat format = new SimpleDateFormat(formatter,
                Locale.getDefault());
        long timestamp = Long.valueOf(time) * 1000L;
        return format.format(new Date(timestamp));
    }

    /**
     * 获得某种时间格式字符串的时间戳
     * @param time 时间文本
     * @param format 时间格式
     * @return	根据相应格式转换成的Java时间戳 int型
     */
    public static int getTimestampByTimeWithFormat(String time, String format){
        SimpleDateFormat simpleDF = new SimpleDateFormat(format, Locale.getDefault());
        int timeStamp = 0;
        try {
            if (!TextUtils.isEmpty(time)){
                Date date = simpleDF.parse(time);
                timeStamp = (int) (date.getTime() / 1000L);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 获得某种时间格式字符串的时间戳
     * @param time 时间文本
     * @param format 格式
     * @return 时间戳String
     */
    public static String getTimestampByTime(String time, String format){
        SimpleDateFormat simpleDF = new SimpleDateFormat(format, Locale.getDefault());
        String timeStamp = "";
        try {
            if (!TextUtils.isEmpty(time)){
                Date date = simpleDF.parse(time);
                timeStamp = String.valueOf((date.getTime()) / 1000L);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }
}
