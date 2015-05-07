package youngam.bsuir.core.m.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alex on 28.04.2015.
 */
public class MyCalendar {
    public static final long MILLISECONDS_IN_DAY = 24*60*60*1000;
    public static final int DAYS_IN_YEAR = 365;
    public static final int COUNT_OF_YEARS = 2;



    public static String getTime(long date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String currTime = hours + ":" + minutes;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(format.parse(currTime));

    }

    public static int getDifference(long date){
        Calendar calendar = Calendar.getInstance();
        long dateNow = calendar.getTimeInMillis();

        return (int) ((date - dateNow)/MILLISECONDS_IN_DAY);
    }

    public static String getDate(long date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int year = calendar.get(Calendar.YEAR);
        // month begin with 0 finish 11
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currDate = day + "." + month + "." + year;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(format.parse(currDate));

    }

    public static long toMilliseconds(int[] date, int[] time){
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0], date[1], date[2], time[0], time[1]);
        return calendar.getTimeInMillis();
    }
    public static long toMilliseconds(int[] date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0], date[1], date[2]);
        return calendar.getTimeInMillis();
    }

    public static int[] getArrayOfDate(){
        Calendar calendar = Calendar.getInstance();
        int[] date = new int[3];
        date[0] = calendar.get(Calendar.YEAR);
        // month begin with 0 finish 11
        date[1] = calendar.get(Calendar.MONTH);
        date[2] = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("MyCalendar", date[2] + ":" + date[1]+ ":" + date[0]);
        return date;
    }

    public static int[] getArrayOfTime(){
        Calendar calendar = Calendar.getInstance();
        int[] time = new int[3];
        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        // month begin with 0 finish 11
        time[1] = calendar.get(Calendar.MINUTE);
        return time;
    }

    public static String toDate(int[] date) throws ParseException {
        // date[0] - year, date[1] - month, date[2] - day
        //date[1] + 1, because months begin with 0

        String currDate = date[2] + "."+ (date[1] + 1) + "." + date[0];
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(format.parse(currDate));
    }

    public static String toTime(int[] time) throws ParseException {
        String currTime = time[0] + ":"+ time[1];
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(format.parse(currTime));
    }

    public static String getDateNow(){
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(rightNow.getTime());
    }

    //Get the date in 00:00 in milliseconds
    // to get the right dates from db
    public static long getDateNowMilliseconds(){
        Calendar rightNow = Calendar.getInstance();
        Log.d("DEBUG", String.valueOf(rightNow.getTimeInMillis()));

        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0 , 0);
        Log.d("DEBUG", String.valueOf(calendar.getTimeInMillis()));

        return calendar.getTimeInMillis();
    }
    public static String getTimeNow(){
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(rightNow.getTime());
    }


}
