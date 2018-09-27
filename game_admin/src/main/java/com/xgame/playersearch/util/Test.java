package com.xgame.playersearch.util;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String roleId = "10010000000001";
		if(StringUtils.isNotBlank(roleId)){
			System.out.println(roleId.substring(0, 4));
		}
		if(StringUtils.isNotEmpty(roleId)){
			System.out.println(roleId);
		}
	}

}
