package com.xgame.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomGenerator {
	
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 兑换码生成
	 * 随机池：大小写字母加数字
	 * @param num 数量
	 * @param length 长度
	 * @return
	 */
	public static Set<String> generateRedeemCode(int num,int length){
		Set<String> redeemCodes = new HashSet<String>();
		Random random = new Random();
		for(int i=0;i<num;i++){
			if(length>0){
				StringBuffer sb = new StringBuffer();
				for(int j =0;j<length ;j++){
					sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
				}
				redeemCodes.add(sb.toString());
			}
		}
		return redeemCodes;
	}

}
