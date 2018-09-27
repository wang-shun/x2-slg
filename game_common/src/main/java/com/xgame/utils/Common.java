package com.xgame.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

public class Common {
	/** 2010-2-1 00:00:00 */
	public static final long TIME_START = 1264953600 * 1000l;

	private static Map<String, Map<String, String>> properyInfoCache = new HashMap<String, Map<String, String>>();

	/** 一周英文对应的数字 */
	public static final Map<String, Integer> WEEK_MAP = new HashMap<String, Integer>();

	static {
		WEEK_MAP.put("Monday", 1);
		WEEK_MAP.put("Tuesday", 2);
		WEEK_MAP.put("Wednesday", 3);
		WEEK_MAP.put("Thursday", 4);
		WEEK_MAP.put("Friday", 5);
		WEEK_MAP.put("Saturday", 6);
		WEEK_MAP.put("Sunday", 7);
	}

	public Common() {
	}

	/** 随机生成一个唯一ID */
	public static long getOnlyId() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	/** 随机数 */
	public static String getRandomInt(int size) {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	/** 验证字符串数组是否包含指定字符串 */
	public static boolean containsString(String[] strs, String str) {
		for (int i = 0; i < strs.length; i++) {
			if (str.equals(strs[i])) {
				return true;
			}
		}
		return false;
	}

	/** 判断整数数组是否存在指定值 */
	public static boolean containsInt(int[] arry, int value) {
		for (int i = 0; i < arry.length; i++) {
			if (arry[i] == value) {
				return true;
			}
		}
		return false;
	}

	/** 查找整数数组的指定值索引位置 */
	public static int indexOf(int[] arry, int value) {
		for (int i = 0; i < arry.length; i++) {
			if (arry[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/** 获取当前时间秒数 */
	public static int currentTimeSecond() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/** 获取配置文件路径 */
	public static String getConfigPath() {
		String path = System.getProperty("configPath");
		path = path == null ? "" : path + File.separator;
		return path;
	}

	/**
	 * 格式化String
	 * 
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	/**
	 * 格式化Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Integer formatInteger(String str) {
		if (str == null) {
			return 0;
		} else {
			try {
				Integer i = new Integer(str);
				return i;
			} catch (Exception ex) {
				return 0;
			}
		}
	}

	/**
	 * 格式化long
	 * 
	 * @param number
	 * @return
	 */
	public static String formatNumber(long number) {
		NumberFormat format = NumberFormat.getInstance();
		return format.format(number);
	}

	/**
	 * 格式化double
	 * 
	 * @param number
	 * @param n
	 *            保留小数位数
	 * @return
	 */
	public static String formatNumber(double number, int n) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(n);
		format.setMinimumFractionDigits(n);
		return format.format(number);
	}

	/**
	 * 得到当前月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到规定时间的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
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
	 * 获取两个时间的小时差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getHourDiff(Date date1, Date date2) {
		int i = Math
				.abs((int) ((date1.getTime() - date2.getTime()) / 1000 / 60 / 60));
		return i;
	}

	/**
	 * 得到当前时间字符串 年月日时分秒毫秒
	 * 
	 * @return
	 */
	public static String getStrTime() {
		Calendar c = Calendar.getInstance();
		String str = getStrZero(c.get(Calendar.YEAR), 4);
		str = str + getStrZero(c.get(Calendar.MONTH) + 1, 2);
		str = str + getStrZero(c.get(Calendar.DATE), 2);
		str = str + getStrZero(c.get(Calendar.HOUR_OF_DAY), 2);
		str = str + getStrZero(c.get(Calendar.MINUTE), 2);
		str = str + getStrZero(c.get(Calendar.SECOND), 2);
		str = str + getStrZero(c.get(Calendar.MILLISECOND), 3);
		return str;
	}

	/**
	 * 在数字前补零
	 * 
	 * @param number
	 *            需要补零的数字
	 * @param n
	 *            零的个数
	 * @return
	 */
	public static String getStrZero(int number, int n) {
		String str = number + "";
		if (str.length() < n) {
			int nlen = n - str.length();
			for (int i = 0; i < nlen; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	/**
	 * 得到当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getFullNow() {
		Date now = new Date();
		return FormatFullDate(now);
	}

	/**
	 * 得到当前时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getNow() {
		Date now = new Date();
		return FormatDate(now);
	}

	/**
	 * 格式化时间，将日期型转化为字符型 HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatTime(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 格式化时间，将日期型转化为字符型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatFullDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 格式化时间，将日期型转化为字符型 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 格式化时间，将字符型转化为日期型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseFullDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = df.parse(str);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 格式化时间，将字符型转化为日期型 yyyy-MM-dd
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = df.parse(str);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * String转Date用yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date StringToDate(String dateString) {
		if (dateString == null) {
			return null;
		}
		dateString = dateString.trim();
		SimpleDateFormat df;
		dateString = dateString.replaceAll("/", "-");
		// 检查date格式
		String[] dateTime = dateString.split(" ");
		String date = null;
		String time = null;
		if (dateTime.length > 1) {
			date = dateTime[0];
			time = dateTime[1];
		} else {
			date = dateTime[0];
		}
		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		if (year.length() == 2) {
			year = getNow().substring(0, 2) + year;
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		date = year + "-" + month + "-" + day;
		if (time != null) {
			String hour = time.split(":")[0];
			String minute = time.split(":")[1];
			String second = time.split(":")[2];
			if (hour.length() == 1) {
				hour = "0" + hour;
			}
			if (minute.length() == 1) {
				minute = "0" + minute;
			}
			if (second.length() == 1) {
				second = "0" + second;
			}
			time = hour + ":" + minute + ":" + second;
		}
		dateString = date + (time == null ? "" : " " + time);
		if (dateString.length() == 10) {
			df = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 验证电子邮件
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean isEmail(String str) {
		Pattern p = Pattern.compile("^[\\w\\-\\.]+@[\\w\\-\\.]+(\\.\\w+)+$"); // 正则表达式
		Matcher m = p.matcher(str); // 操作的字符串
		Boolean b = m.matches();
		return b;
	}

	/**
	 * 获取年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday) {
		Calendar now = Calendar.getInstance();
		Date d = parseDate(birthday);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		if (c.after(now))
			return 0;
		int age = now.get(Calendar.YEAR) - c.get(Calendar.YEAR);
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		if (c.after(now))
			age--;
		return age;
	}

	/**
	 * 获取年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAge(Date birthday) {
		return getAge(FormatDate(birthday));
	}

	/**
	 * 获取间隔一定天数的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDayDate(Date date, int day) {
		date.setTime(date.getTime() + day * 1000 * 24 * 60 * 60);
		return date;
	}

	/**
	 * 身份证最后一位校验码
	 * 
	 * @param id
	 * @return
	 */
	public static String doVerify(String id) {
		char pszSrc[] = id.toCharArray();
		int iS = 0;
		int iW[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		String szVerCode[] = new String[] { "1", "0", "X", "9", "8", "7", "6",
				"5", "4", "3", "2" };
		for (int i = 0; i < 17; i++) {
			iS += (pszSrc[i] - '0') * iW[i];
		}
		int iY = iS % 11;
		return szVerCode[iY];
	}

	/**
	 * 判断日期合法性
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		try {
			df.parse(str);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 判断日期是否为身份证合法日期
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isVerifyDate(String str) {
		if (str.startsWith("19") || str.startsWith("20"))
			return isDate(str);
		else
			return false;
	}

	/**
	 * 得到一个bean的属性和类型
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getBeanPropertyNameAndType(Object bean)
			throws Exception {
		String className = bean.getClass().getName();
		if (properyInfoCache.get(className) != null) {
			return properyInfoCache.get(className);
		}
		Map<String, String> map = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor descriptor[] = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < descriptor.length; i++) {
			PropertyDescriptor propertyDescriptor = descriptor[i];
			if (!propertyDescriptor.getName().equals("class")) {
				map.put(propertyDescriptor.getName(), propertyDescriptor
						.getPropertyType().getName());
			}
		}
		properyInfoCache.put(className, map);
		return map;
	}

	/**
	 * 拷贝一个Bean的非空参数到另一个bean.
	 * 
	 * @param bean
	 * @param request
	 */
	public static void copyPropertysOnNotNull(Object fromBean, Object toBean)
			throws Exception {
		Map map = BeanUtils.describe(fromBean);
		// 遍历Bean的参数
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			String name = (String) entry.getKey();
			// 取出真正的值
			Object value = null;
			String methodName = "get" + Character.toUpperCase(name.charAt(0))
					+ name.substring(1);
			Method method = fromBean.getClass().getMethod(methodName,
					new Class[] {});
			value = method.invoke(fromBean, new Object[] {});
			// put到Row里
			if (!name.equals("class") && value != null) {
				BeanUtils.setProperty(toBean, name, value);
			}
		}
	}

	/**
	 * 把异常转为字符描述用于文件
	 * 
	 * @param e
	 * @return
	 */
	public static String exceptionToString(Exception e) {
		StringBuffer s = new StringBuffer();
		s.append(e.toString());
		s.append("\r\n");
		StackTraceElement[] array = e.getStackTrace();
		for (int i = 0; i < array.length; i++) {
			StackTraceElement element = array[i];
			s.append(element.toString());
			s.append("\r\n");
		}
		return s.toString();
	}

	/**
	 * 判断一个String是不是为空 有效的String不能为NULL或空字符串""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		if (str == null) {
			return true;
		}
		if (str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个String是不是不为空 有效的String不能为NULL或空字符串""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmptyString(String str) {
		return !isEmptyString(str);
	}

	/**
	 * 判断一个Model的ID是不是无效或为空的ID 有效ID应该不能为NULL或0
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isEmptyModelId(Integer id) {
		if (id == null) {
			return true;
		}
		if (id.intValue() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 把一个Model的类名转为一个数据库表名
	 * 
	 * @param className
	 * @return
	 */
	public static String classNameToTableName(String className) {
		StringBuffer sb = new StringBuffer();
		if (className == null) {
			return null;
		}
		if (className.startsWith("Base")) {
			className = className.substring(4);
		}
		if (className.startsWith("View")) {
			className = className.substring(4);
			sb.append("view");
		} else {
			sb.append("tbl");
		}
		char[] charArray = className.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (Character.isUpperCase(c)) {
				sb.append("_");
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 把一个Model的类名转为一个数据库表名
	 * 
	 * @param className
	 * @return
	 */
	public static String tableNameToClassName(String tableName) {
		StringBuffer sb = new StringBuffer();
		if (tableName == null) {
			return null;
		}
		if (tableName.startsWith("tbl")) {
			tableName = tableName.substring(3);
		}
		char[] charArray = tableName.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == '_') {
				// "_"后面的变大写
				i++;
				c = charArray[i];
				sb.append(Character.toUpperCase(c));
			} else if (i == 0) {
				// 首字母变大写
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 把一个Model的类名转为一个文档名
	 * 
	 * @param className
	 * @return
	 */
	public static String classNameToTxtName(String className) {
		StringBuffer sb = new StringBuffer();
		if (className == null) {
			return null;
		}
		if (className.startsWith("Base")) {
			className = className.substring(4);
		}
		if (className.endsWith("Model")) {
			className = className.substring(0, className.length() - 5);
		}
		char[] charArray = className.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (Character.isUpperCase(c)) {
				if (i != 0) {
					sb.append("_");
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		sb.append(".txt");
		return sb.toString();
	}

	/**
	 * 把一个Model的类名转为一个文档名
	 * 
	 * @param txtName
	 * @return
	 */
	public static String txtNameToClassName(String txtName) {
		StringBuffer sb = new StringBuffer();
		if (null == txtName) {
			return null;
		}
		if (txtName.endsWith(".txt")) {
			txtName = txtName.substring(0, txtName.length() - 4);
		}
		char[] charArray = txtName.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == '_') {
				// "_"后面的变大写
				i++;
				c = charArray[i];
				sb.append(Character.toUpperCase(c));
			} else if (i == 0) {
				// 首字母变大写
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String stringBigFirst(String name) {
		if (name == null) {
			return "";
		}
		if (name.equals("")) {
			return name;
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	/**
	 * 检查合法性
	 * 
	 * @param regex
	 *            正则表达试
	 * @param command
	 *            内容
	 * @return
	 */
	public static boolean checkString(String regex, String command) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(command);
		if (matcher.find()) {
			String str = matcher.group(0);
			if (command.length() == str.length()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查合法性，根据需求获取数据
	 * 
	 * @param regex
	 *            正则表达试
	 * @param command
	 *            内容
	 * @param group
	 * @return
	 */
	public static String getValue(String regex, String command, Integer group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(command);
		if (matcher.find()) {
			String str = matcher.group(group);
			return str;
		}
		return "";
	}

	public static synchronized java.util.Date getFirstDayOfWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, -7);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc.getTime();
	}

	/** 判断两个日期是否是同一周 */
	public static synchronized boolean getDateDiffWeek(java.util.Date date1,
			java.util.Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(getFirstDayOfWeek(date1));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(getFirstDayOfWeek(date2));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/** 判断两个日期是否是同一天 */
	public static boolean getDateIsSame(java.util.Date date1,
			java.util.Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(parseDate(FormatDate(date1)));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(parseDate(FormatDate(date2)));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * String转List<String>
	 * 
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static List<String> StringToList(String str, String splitStr) {
		List<String> list = Collections
				.synchronizedList(new ArrayList<String>());
		String[] array = str.split(splitStr);
		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			list.add(s);
		}
		return list;
	}

	/**
	 * String转List<Integer>
	 * 
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static List<Integer> StringToIntegerList(String str, String splitStr) {
		List<Integer> list = Collections
				.synchronizedList(new ArrayList<Integer>());
		if (str == null) {
			return list;
		}
		String[] array = str.split(splitStr);
		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			try {
				list.add(new Integer(s));
			} catch (Exception e) {
				System.out.println("不能将“" + s + "”字符转数字");
			}
		}
		return list;
	}

	/**
	 * 获得剩余时间（秒），开始时间是2010-01-01 00：00：00
	 * 
	 * @param time
	 * @return
	 */
	public static int getSurplusTime(long time) {
		return (int) ((time - Common.TIME_START) / 1000);
	}

	/**
	 * 矩阵加
	 * 
	 * @param dest
	 *            目标
	 * @param src
	 *            源
	 */
	public static void add(int[] dest, int[] src) {
		if (dest == null || src == null)
			return;
		for (int i = 0; i < src.length; i++)
			dest[i] += src[i];
	}

	/**
	 * 连接字符串
	 * 
	 * @param split
	 *            分割字符
	 * @param seq
	 *            字符串列表
	 * @return
	 */
	public static String join4split(String split, Object... seq) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < seq.length; i++) {
			if (i > 0)
				sb.append(split);
			sb.append(seq[i]);
		}
		return sb.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param seq
	 *            字符串列表
	 * @return
	 */
	public static String join(Object... seq) {
		StringBuffer sb = new StringBuffer();
		for (Object str : seq)
			sb.append(str);
		return sb.toString();
	}

	/**
	 * 分割字符串到整数数组
	 * 
	 * @param string
	 * @param splitRegex
	 * @return
	 */
	public static int[] split2Int(String string, String splitRegex) {
		String[] strs = string.split(splitRegex);
		int[] vals = new int[strs.length];
		for (int i = 0; i < strs.length; i++)
			vals[i] = Integer.parseInt(strs[i]);
		return vals;
	}

	/**
	 * 分割字符串到整数数组
	 * 
	 * @param string
	 * @param splitRegex
	 * @return
	 */
	public static float[] split2Float(String string, String splitRegex) {
		String[] strs = string.split(splitRegex);
		float[] vals = new float[strs.length];
		for (int i = 0; i < strs.length; i++)
			vals[i] = Float.parseFloat(strs[i]);
		return vals;
	}

	/**
	 * 连接字符串到UTF-8编码URLEncode字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String join2url(Object... value) {
		String str = Common.join(value);
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 周对应的数字，数字为1~7，没有则返回0
	 * 
	 * @param d
	 * @return
	 */
	public static int weekDay(String d) {
		int v = 0;
		try {
			v = WEEK_MAP.get(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 判断当前是否和传入的周相等，传入比较的应为0~6之间的数
	 * 
	 * @param week
	 * @return
	 */
	public static boolean isNowWeek(int week) {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(new Date());
		int nowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		if (nowWeek < 0) {
			nowWeek = 7 + nowWeek;
		}
		return nowWeek == week;
	}

	/**
	 * 将时间转换为当前日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date timeToDate(String time) {
		if (time == null) {
			return new Date();
		}
		String[] times = time.trim().split(":");
		String hour = times[0];
		String minute = times[1];
		String second = "0";
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		if (second.length() == 1) {
			second = "0" + second;
		}
		time = hour + ":" + minute + ":" + second;
		String dateString = Common.getNow() + " " + time;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将长整形数字转换为日期格式，支持完整日期格式与短日期格式
	 * 
	 * @param time
	 * @param isFull
	 * @return
	 */
	public static Date timeToDate(long time, boolean isFull) {
		if (time <= 0l)
			return null;
		Date date = new Date(time);
		if (isFull)
			return date;
		return parseDate(FormatDate(date));
	}

	/**
	 * 字符串数组到双精度数组
	 * 
	 * @param str
	 * @param offset
	 * @return
	 */
	public static double[] arr2double(String[] str, int offset) {
		return arr2double(str, offset, str.length - offset);
	}

	/**
	 * 字符串数组到双精度数组
	 * 
	 * @param str
	 *            字符串数组
	 * @param offset
	 *            偏移索引
	 * @param length
	 *            转换长度
	 * @return
	 */
	public static double[] arr2double(String[] str, int offset, int length) {
		double[] value = new double[length];
		for (int i = 0; i < length; i++) {
			value[i] = Double.parseDouble(str[offset + i]);
		}
		return value;
	}

	/**
	 * 分割字符串到二维数组
	 * 
	 * @param value
	 *            欲分割字符串
	 * @param split1
	 *            一维分隔符，支持正则表达式
	 * @param split2
	 *            二维分隔符，支持正则表达式
	 * @return
	 */
	public static String[][] split2two(String value, String split1,
			String split2) {
		String[] arry = value.split(split1);
		String[][] result = new String[arry.length][];
		for (int i = 0; i < arry.length; i++) {
			result[i] = arry[i].split(split2);
		}
		return result;
	}

	/**
	 * 是否包含本周
	 * 
	 * @param week
	 *            1-7
	 * @return
	 */
	public static boolean isWeekIn(int[] week) {
		Calendar c = Calendar.getInstance();
		int value = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (value == 0)
			value = 7;
		return containsInt(week, value);
	}

	/**
	 * 当前时间是否在指定范围中
	 * 
	 * @param timeQuantum
	 *            [[start,end],...]
	 * @return
	 */
	public static boolean isTimeIn(String[][] timeQuantum) {
		Calendar c = Calendar.getInstance();
		int todayMinu = c.get(Calendar.HOUR_OF_DAY) * 60
				+ c.get(Calendar.MINUTE);
		for (int i = 0; i < timeQuantum.length; i++) {
			String startStr = timeQuantum[i][0];
			String endStr = timeQuantum[i][1];
			int start = DateUtils.getMinuCount(startStr);
			int end = DateUtils.getMinuCount(endStr);
			if (start <= todayMinu && todayMinu <= end) {
				return true;
			}
		}
		return false;
	}

	public static boolean lg(int[] arr1, int[] arr2) {
		for (int i = 0; i < arr1.length && i < arr2.length; i++) {
			if (arr1[i] < arr2[i]) {
				return true;
			} else if (arr1[i] > arr2[i]) {
				return false;
			}
		}
		return arr1.length < arr2.length;
	}

	public static void main(String[] args) {
		// System.err.println(isTimeIn(split2two("19:45~19:55;20:00~21:00", ";",
		// "~")));
		System.err.println(lg(new int[] { 2, 2, 1 }, new int[] { 2, 2, 2 }));
	}

	public static int getRemaingSecondsByWeek(int[] weeks) {
		Calendar c = Calendar.getInstance();
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		int seconds = c.get(Calendar.HOUR_OF_DAY) * 60 * 60
				+ c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
		if (week == 0)
			week = 7;
		for (int i = 0; i < weeks.length; i++)
			if (week < weeks[i]) {
				int days = weeks[i] - week;
				return days * 24 * 60 * 60 - seconds;
			}
		return (weeks[0] + (7 - week)) * 24 * 60 * 60 - seconds;
	}

	/**
	 * 将金币数目转换为 X金X银X铜的格式 10000 -> 1金 11000 -> 1金10银 10010 -> 1金10铜 11010 ->
	 * 1金10银10铜
	 * 
	 * @param amount
	 * @return
	 */
	public static String moneyLiteral(int amount) {
		int grade;
		StringBuilder buffer = new StringBuilder();
		if (amount > 10000) {
			grade = amount / 10000;
			buffer.append(grade).append("金");
			amount -= grade * 10000;
		}
		if (amount > 100) {
			grade = amount / 10000;
			buffer.append(grade).append("银");
			amount -= grade * 100;
		}
		if (amount > 0) {
			buffer.append(amount).append("铜");
		}
		return buffer.toString();
	}
}
