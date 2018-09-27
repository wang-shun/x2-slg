package com.xgame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*******************************************************************************
 * <p>
 * 日期工具类
 * </p>
 * 
 * @author YuanMingLiang
 * @version 1.00 2010/8/10
 *          <p>
 *          用于任务模块、副本模块、活动模块的时间判断
 *          </p>
 ******************************************************************************/
public class DateUtils {
	public static final SimpleDateFormat format = new SimpleDateFormat(
			"yyy-MM-dd HH:mm:ss.SSS");

	public static final SimpleDateFormat format2Sec = new SimpleDateFormat(
			"yyy-MM-dd HH:mm:ss");

	/** 一天毫秒数 */
	public static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;

	/**
	 * 获取下一天的开始时间 00:00:00.000
	 */
	public static long getNextDayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 按照指定的时间格式转换成Date对象
	 * 
	 * @param format
	 *            格式字符串
	 * @param dateStr
	 *            时间字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date format(String format, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将日期分解成 年、月、日、时、分、秒的int数组，用于发给前台时间
	 * 
	 * @param date
	 * @return
	 */
	public static int[] splitDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int[] dates = new int[6];
		dates[0] = c.get(Calendar.YEAR);// 年
		dates[1] = c.get(Calendar.MONTH) + 1;// 月
		dates[2] = c.get(Calendar.DAY_OF_MONTH);// 日
		dates[3] = c.get(Calendar.HOUR_OF_DAY);// 小时
		dates[4] = c.get(Calendar.MINUTE);// 分
		dates[5] = c.get(Calendar.SECOND);// 秒
		return dates;
	}

	/**
	 * 判断指定日期是否是指定星期几
	 * 
	 * @param week
	 * @return
	 */
	public static boolean isWeekDay(Date date, int week) {
		boolean flag = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (week == getDateToWeek(c)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 判断两个日期是否是同一周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDiffWeek(Date date1, Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(getFirstDayOfWeek(date1));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(getFirstDayOfWeek(date2));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 得到输入日期是一个星期的第几天
	 * 
	 * @param gc
	 * @return
	 */
	public static int getDateToWeek(Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			return 7;
		case (Calendar.MONDAY):
			return 1;
		case (Calendar.TUESDAY):
			return 2;
		case (Calendar.WEDNESDAY):
			return 3;
		case (Calendar.THURSDAY):
			return 4;
		case (Calendar.FRIDAY):
			return 5;
		case (Calendar.SATURDAY):
			return 6;
		}
		return 0;
	}

	/**
	 * 得到输入日期这个星期的星期一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date result = sdf.parse(sdf.format(date));
			gc.setTime(result);
			switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case (Calendar.SUNDAY):
				gc.add(Calendar.DATE, -6);
				break;
			case (Calendar.MONDAY):
				gc.add(Calendar.DATE, 0);
				break;
			case (Calendar.TUESDAY):
				gc.add(Calendar.DATE, -1);
				break;
			case (Calendar.WEDNESDAY):
				gc.add(Calendar.DATE, -2);
				break;
			case (Calendar.THURSDAY):
				gc.add(Calendar.DATE, -3);
				break;
			case (Calendar.FRIDAY):
				gc.add(Calendar.DATE, -4);
				break;
			case (Calendar.SATURDAY):
				gc.add(Calendar.DATE, -5);
				break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return gc.getTime();
	}

	/**
	 * 得到这个日期的最后一秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTodayEnd(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf1.format(date);
		day = day + " 23:59:59";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = sdf2.parse(day);
		} catch (ParseException e) {
		 
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取间隔一定天数的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getIntervalDay(Date date, int day) {
		Date result = new Date();
		result.setTime(date.getTime() + day * 1000 * 24 * 60 * 60);
		return result;
	}

	/**
	 * 获取间隔一定分钟的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getIntervalMinute(Date date, int minute) {
		Date result = new Date();
		result.setTime(date.getTime() + minute * 1000 * 60);
		return result;
	}

	/**
	 * 获取两个时间的天数差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDateDiff(Date date1, Date date2) {
		int i = Math
				.abs((int) ((date1.getTime() - date2.getTime()) / 1000 / 60 / 60 / 24));
		return i;
	}

	/**
	 * 获取两个时间的分钟差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMinuteDiff(Date date1, Date date2) {
		int i = Math
				.abs((int) ((date1.getTime() - date2.getTime()) / 1000 / 60));
		return i;
	}

	/**
	 * 获取两个时间的秒数差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getSecondDiff(Date date1, Date date2) {
		int i = Math.abs((int) ((date1.getTime() - date2.getTime()) / 1000));
		return i;
	}

	/**
	 * 
	 * 判断是否是零时
	 * 
	 * @return
	 */
	public static boolean isWeeHour() {
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(Calendar.HOUR_OF_DAY) == 0
				&& calendar.get(Calendar.MINUTE) == 0
				&& calendar.get(Calendar.SECOND) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 将时间毫秒格式化为字符串
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            时间毫秒
	 * @return
	 */
	public static String format(String format, long time) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

	/**
	 * 格式化时间
	 * 
	 * @param startTime
	 * @return
	 */
	public static String format(long time) {
		return format.format(new Date(time));
	}

	/**
	 * 格式化到精确到秒的时间字符串
	 * 
	 * @param 时间秒
	 * @return
	 */
	public static String formatBySec(long time) {
		return format2Sec.format(new Date(time));
	}

	/**
	 * 解析格式时间字符串
	 * 
	 * @param format
	 * @param date
	 * @return 时间毫秒
	 * @throws ParseException
	 */
	public static long parse(String format, String date) throws ParseException {
		return new SimpleDateFormat(format).parse(date).getTime();
	}

	/**
	 * 获取下一个偶数整点时间
	 */
	public static long getNextEvenTime() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.HOUR_OF_DAY) % 2 != 0) {
			cal.add(Calendar.HOUR_OF_DAY, 1);
		} else {
			cal.add(Calendar.HOUR_OF_DAY, 2);
		}
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 比较时间差距<br>
	 * 
	 * 计算结果 = 结束时间 - 开始时间
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param field
	 *            Calendar的时间域常量
	 * @return
	 */
	public static int diff(long start, long end, int field) {
		Calendar cstart = Calendar.getInstance();
		cstart.setTimeInMillis(start);
		Calendar cend = Calendar.getInstance();
		cend.setTimeInMillis(end);
		return cend.get(field) - cstart.get(field);
	}

	public static String format(Date date) {
		if (date == null)
			return "null";
		return format(date.getTime());
	}

	/**
	 * 获取时间总分钟数
	 * 
	 * @param date
	 *            12:59
	 * @return
	 */
	public static int getMinuCount(String date) {
		int[] arry = Common.split2Int(date, "\\:");
		return arry[0] * 60 + arry[1];
	}

	/**
	 * 获取总天数
	 * 
	 * @param time
	 *            毫秒数
	 * @return 返回含当天
	 */
	public static int getDayCount(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return getDayCount(c);
	}

	/**
	 * 获取总天数
	 * 
	 * @param time
	 *            毫秒数
	 * @return 返回含当天
	 */
	public static int getDayCount(Calendar c) {
		// 不能用毫秒直接除以一天的总毫秒数计算总天数
		return c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR);
	}

	public static int getHoursCount(long time) {
		return (int) (time / (60 * 60 * 1000L));
	}

	/**
	 * 计算从开始到截止经过了多少个everyDayTime
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @param everyDayTime
	 *            每天的这个时间
	 * @return
	 */
	public static int getDiffCount(long startTime, long endTime,
			long everyDayTime) {
		int agoDays = (int) ((endTime - startTime) / ONE_DAY_TIME);
		int startDayCount = getDayCount(startTime);
		int endDayCount = getDayCount(endTime);
		long starDayTime = getTodayTime(startTime);
		long endDayTime = getTodayTime(endTime);
		// 当天情况下
		if (startDayCount == endDayCount)
			if (starDayTime < everyDayTime && endDayTime >= everyDayTime) {
				// 开始时间未过everyDayTime，当天截止时间过了everyDayTime
				agoDays++;
			}
		// 非当天的情况下
		if (startDayCount < endDayCount) {
			// 补上头或尾一天经过everyDayTime
			if (starDayTime < everyDayTime || endDayTime >= everyDayTime) {
				agoDays++;
			}
		}
		return agoDays;
	}

	private static long getTodayTime(long startTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(startTime);
		return c.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000L
				+ c.get(Calendar.MINUTE) * 60 * 1000L + c.get(Calendar.SECOND)
				* 1000L + c.get(Calendar.MILLISECOND);
	}

	public static void main(String[] args) throws Exception {
		// int[] date = splitDate(new Date());
		// System.out.println(date[0]);
		// System.out.println(date[1]);
		// System.out.println(date[2]);
		// System.out.println(date[3]);
		// System.out.println(date[4]);
		// System.out.println(date[5]);
		// System.out.println(isWeekDay(3));
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date1 = sdf.parse("2011-1-12 15:20:00");
		// Date date2 = new Date();
		// System.out.println(getMinuteDiff(date1, date2));
		// System.out.println(getIntervalMinute(date1, -5));
		// System.out.println(getIntervalMinute(date2, -5));
		// System.out.println(getFirstDayOfWeek(date1));
		// System.out.println(getFirstDayOfWeek(date2));
		// System.out.println(getDateDiff(date1, date2));
		// System.out.println(getTodayEnd(new Date()));
		// System.out.println(isDiffWeek(new Date(), date1));
		// long time = getNextDayStartTime();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println(sdf.format(new Date(time)));
		System.err.println(getDayCount(System.currentTimeMillis() - 8 * 60 * 60
				* 1000L));
		System.err.println(getDayCount(System.currentTimeMillis() + 24 * 60
				* 60 * 1000L));
		System.err.println(getDiffCount(
				System.currentTimeMillis() - 60 * 60 * 1000L,
				System.currentTimeMillis() + 6 * 60 * 60 * 1000L,
				18 * 60 * 60 * 1000L));
	}
}
