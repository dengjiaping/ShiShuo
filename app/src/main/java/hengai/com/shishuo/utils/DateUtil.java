package hengai.com.shishuo.utils;

import java.text.SimpleDateFormat;

/**
 * Created by yu on 2017/8/15.
 */

public class DateUtil {

    /**
     * @param date 输入时间戳
     * @return yyyy-MM-dd格式的日期
     */
    public static String getDate(int date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long x=Long.parseLong(date+"000");
        String dateString = formatter.format(x);
        return dateString;
    }

    /**
     * @param date 时间戳
     * @return HH:mm格式的时间
     */
    public static String getTime(int date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Long x=Long.parseLong(date+"000");
        String dateString = formatter.format(x);
        return dateString;
    }
}
