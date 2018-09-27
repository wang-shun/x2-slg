package com.xgame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	
	public static final long ONE_SECONDS_MS = 1000;
	
	public static final long WEEK_MILLIS = 3600 * 1000 * 24 * 7;
	
	
//    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimeUtils.class);

    public static int getCurrentDayInYear() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(getDayBeginTimeMillis(System.currentTimeMillis()));
        return currentDate.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Epoch time in seconds
     * 
     * @return
     */
    public static int getCurrentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 校验的时间和当前事件是否在同一天
     * @param seconds
     * @return
     */
    public static boolean isToday(long milliSeconds) {
    	Calendar nowCalendar = Calendar.getInstance();
    	Calendar checkCalendar = Calendar.getInstance();
    	checkCalendar.setTimeInMillis(milliSeconds);
    	
    	if(nowCalendar.get(Calendar.YEAR) == checkCalendar.get(Calendar.YEAR) 
    			&& nowCalendar.get(Calendar.MONTH) == checkCalendar.get(Calendar.MONTH)
    			&& nowCalendar.get(Calendar.DAY_OF_MONTH) == checkCalendar.get(Calendar.DAY_OF_MONTH)) {
    		return true;
    	}
    	return false;
    }
    
    public static void main(String[] args) {
    	System.out.println(isToday(System.currentTimeMillis() - 24 * 3600 * 1000));
    }

    public static int getTodayBeginTime() {
        return (int) (getDayBeginTimeMillis(getCurrentTimeMillis()) / 1000L);
    }

    public static long getDayBeginTimeMillis(long time) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(time);
        currentDate.add(Calendar.HOUR_OF_DAY, -5); // daily begin time is 05:00
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        currentDate.add(Calendar.HOUR_OF_DAY, 5);
        return currentDate.getTimeInMillis();
    }
    
    public static int getDaySpan(Date begin, Date end) {
        return getDaySpan(begin.getTime(), end.getTime());
    }

    public static int getDaySpan(long beginTimeMilis, long endTimeMilis) {
        long beginDayBeginTimeMilis = getDayBeginTimeMillis(beginTimeMilis);
        long endDayBeginTimeMilis = getDayBeginTimeMillis(endTimeMilis);

        return (int) ((endDayBeginTimeMilis - beginDayBeginTimeMilis) / (24 * 3600 * 1000L));
    }

    public static int getTodayLeftTime() {
        return 24 * 3600 - getTodayPastTime();
    }

    public static int getTodayPastTime() {
        return getCurrentTime() - getTodayBeginTime();
    }

    public static boolean isBeforeToday(int utcUnixTs) {
        return utcUnixTs < getTodayBeginTime();
    }
    
    public static long getCalanderDayBeginTimeMillis(long time) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(time);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        return currentDate.getTimeInMillis();
    }

    public static int getCalanderDaySpan(long beginTimeMilis, long endTimeMilis) {
        long beginDayBeginTimeMilis = getCalanderDayBeginTimeMillis(beginTimeMilis);
        long endDayBeginTimeMilis = getCalanderDayBeginTimeMillis(endTimeMilis);

        return (int) ((endDayBeginTimeMilis - beginDayBeginTimeMilis) / (24 * 3600 * 1000L));
    }
    
    public static String getCron(int s){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
		return dateFormat.format(new Date(System.currentTimeMillis()+(long)(s*1000l)));
    }
    
    public static String dateStr(long t,String format)
	{
		return new SimpleDateFormat(format).format(t);
	}
	
	public static long dateValue(String str,String format)
	{		
		
		try {
			return new SimpleDateFormat(format).parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取当前时间周一零点的timeMillis
	 * @return
	 */
	public static long getMondayTimeMillis(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 获取当周周日23:59:59.999的timeMillis
	 * @return
	 */
	public static long getSundayLastTimeMillis(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}
}
