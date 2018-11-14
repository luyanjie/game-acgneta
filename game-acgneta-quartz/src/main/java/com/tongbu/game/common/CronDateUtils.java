package com.tongbu.game.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jokin
 * @date 2018/11/8 17:09
 *
 * 该类提供Quartz的cron表达式与Date之间的转换
 */
public class CronDateUtils {
    //private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    private static final String CRON_DATE_FORMAT = "ss 0/1 * * * ? *";
    /***
     *
     * @param date 时间
     * @return  cron类型的日期
     */
    public static String getCron(final Date  date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     *
     * @param cron Quartz cron的类型的日期
     * @return  Date日期
     */
    public static Date getDate(final String cron) {


        if(cron == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            // 此处缺少异常处理,自己根据需要添加
            return null;
        }
        return date;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
        System.out.println(getCron(DateUtils.addSeconds(date,10)));
    }
}
