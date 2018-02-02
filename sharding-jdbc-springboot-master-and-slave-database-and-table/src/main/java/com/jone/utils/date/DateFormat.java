package com.jone.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iland on 2018/1/25.
 */
public class DateFormat {
    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDate(){
        return getDate().replace("-", "");
    }


    public static int ChangeCurrentDate2Int(){
        return Integer.parseInt(getCurrentDate());
    }

    public static String changeDate2String(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static int changeDate2Int(Date date){
        String dateString = changeDate2String(date).replace("-", "");
        return Integer.parseInt(dateString);
    }
}
