package com.xgame.common;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	
	private static Properties props;
	
	static{
		loadProps();
	}
	
	synchronized static private void loadProps(){
		props = new Properties();
		InputStream in = null;
		try{
			in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties");
			props.load(in);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(null != in){
					in.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getProperty(String key){
		if(null == props){
			loadProps();
		}
		return props.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue){
		if(null == props){
			loadProps();
		}
		return props.getProperty(key,defaultValue);
	}
}
