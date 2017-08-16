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
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * @param date 时间戳
     * @return HH:mm格式的时间
     */
    public static String getTime(int date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }
}
