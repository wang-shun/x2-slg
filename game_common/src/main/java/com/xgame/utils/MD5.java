package com.xgame.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	private static char md5String[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	/**
	 * md5值
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			return md5(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 32位md5值
	 * @param bytes
	 * @return
	 */
	public static String md5(byte[] bytes) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(bytes);
		byte[] md = messageDigest.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for(int i=0;i<j;i++) {
			byte byte0 = md[i];
			str[k++] = md5String[byte0 >>> 4 & 0xf];
			str[k++] = md5String[byte0 & 0xf];
		}
		
		return new String(str);
	}
	
	
	public static void main(String[] args) {
		System.out.println(md5("123123"));
	}
}
