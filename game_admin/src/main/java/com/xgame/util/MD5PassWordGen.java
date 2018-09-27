package com.xgame.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5PassWordGen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		System.out.println(md5.encodePassword("123456", "xgame"));
	}

}
