package jigneshkt.com.supportwheeloffate.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    private static String dateFormat = "yyyy-MM-dd";

    public static String dayOfDate(String strDate){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);
        try {
            Date date = dtFormat.parse(strDate);
            calendar.setTime(date);
            String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
            return days[calendar.get(Calendar.DAY_OF_WEEK)-1];
        }catch (Exception e){
            Log.e("NextEmployeeRepository",e.getLocalizedMessage());
        }
        return null;
    }

    public static String incrementDate(String lastDate){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(lastDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            return sdf.format(c.getTime());  // dt is now the new date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  null;

    }


    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(c.getTime());
    }
}
