package com.salesbox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*作用：格式化时间戳
 *
 * 返回值 是一个格式化后的字符串*/
public class DateUtils {
    private  static SimpleDateFormat sf = null;
    public static String getDateToString (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd"); //hh表示12小时制的时间   HH 表示24小时制度
        return sf.format(d);
    }

    public static String getDateToStringHMD (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }
    public static String getDateToStringHMD4 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("HH:mm:ss");
        return sf.format(d);
    }
    public static String getDateToStringHMD2 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
    public static String getDateToStringHMD3 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("HH:mm");
        return sf.format(d);
    }
    public static String getDateToStringHMD5 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM月dd日 HH:mm:ss");
        return sf.format(d);
    }
    public static String getDateToStringHMD6 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    public static String getDateToStringHMD7 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(d);
    }
    public static String getDateToStringHMD8 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sf.format(d).substring(2,sf.format(d).length());
        return str;
    }
    public static String getDateToStringHMD9 (long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(d);
    }
    public static Long getMiSeconsFromDate (String date){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    public static String reportTime() {
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        String format = df.format(new Date());
        String[] split = format.split("-");
        StringBuilder sb = new StringBuilder();
        for (String string : split) {
            String hexString = Integer.toHexString(Integer.parseInt(string));
            if (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

}
