package com.xgame.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
	private static final List<String> formats = new ArrayList<String>(4);
	static{
		formats.add("yyyy-MM");
		formats.add("yyyy-MM-dd");
		formats.add("yyyy-MM-dd hh:mm");
		formats.add("yyyy-MM-dd hh:mm:ss");
		formats.add("yyyy-MM-dd hh:mm:ss.SSS");
	}

	@Override
	public Date convert(String source) {
		String value =source.trim();
		if("".equals(value)){
			return null;
		}
		if(source.matches("^\\d{4}-\\d{1,2}$")){
			return parseDate(source, formats.get(0));
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
			return parseDate(source, formats.get(1));
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
			return parseDate(source, formats.get(2));
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
			return parseDate(source, formats.get(3));
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")){
			return parseDate(source, formats.get(4));
		}else{
			throw new IllegalArgumentException("Invalid boolean value '"+source+"'");
		}
	}
	
	public Date parseDate(String dateStr,String format){
		Date date = null;
		try{
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = (Date) dateFormat.parse(dateStr);
		}catch (Exception e) {
			
		}
		return date;
	}
}
