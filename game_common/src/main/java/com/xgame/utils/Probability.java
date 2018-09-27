package com.xgame.utils;

import java.security.SecureRandom;

public class Probability {
	
	private static SecureRandom rand = new SecureRandom();

	/**
	 * 是否在概率里面
	 * 
	 * @param per
	 *            相对于100的概率值 80表示 80%
	 * @return
	 */
	public static boolean isHit(int per) {
		return isHit(per, 100);
	}

	/**
	 * 是否在概率里面
	 * 
	 * @param percentNum
	 * @param maxNum
	 * @return
	 */
	public static boolean isHit(int percentNum, int maxNum) {
		if (percentNum == 0) {
			return false;
		}
		// 如果机率大于 maxNum
		if (percentNum >= maxNum) {
			return true;
		}
		// 如果不是
		int value = rand.nextInt(maxNum + 1);
		if (percentNum >= value) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否在概率里面
	 * 
	 * @param percentNum
	 * @param maxNum
	 * @return
	 */
	public static boolean isHit(long percentNum, long maxNum) {
		if (percentNum == 0) {
			return false;
		}
		// 如果机率大于 maxNum
		if (percentNum >= maxNum) {
			return true;
		}
		// 如果不是
		long value = (long) (rand.nextDouble() * (maxNum + 1));
		if (percentNum >= value) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回这个概率里面的随机值
	 * 
	 * @param min
	 *            包含最小
	 * @param max
	 *            包含最大
	 * @return
	 */
	public static int randomNum(int min, int max) {
		int num = max + 1 - min;
		if (num <= 0) {
			return min;
		}
		return min + rand.nextInt(num);
	}

	public static void main(String[] args) {
		int count = 0;
		int countY = 0;
		int countN = 0;
		for (int i = 0; i < 10000; i++) {
			count++;
			if (Probability.isHit(1200, 10000))
				countY++;
			else
				countN++;
		}
		System.out.print((countY / (count * 1.0)) + "/"
				+ (countN / (count * 1.0)));
	}

	/**
	 * 产生随机区间索引
	 * 
	 * @param randstr
	 *            80_10_6_3_1
	 * @param split
	 *            _
	 * @return
	 */
	public static int randomIndex(String randstr, String split) {
		int[] random = Common.split2Int(randstr, split);
		return randomIndex(random);
	}

	/**
	 * 按区间概率产生索引
	 * 
	 * @param random
	 *            e.g [80, 10, 6, 3, 1]
	 * @return
	 */
	public static int randomIndex(Integer[] random) {
		int count = sum(random);
		int pos = randomNum(1, count);
		count = 0;
		for (int i = 0; i < random.length; i++) {
			if (count < pos && pos <= count + random[i])
				return i;
			count += random[i];
		}
		return -1;
	}

	/**
	 * 按区间概率产生索引
	 * 
	 * @param random
	 *            e.g [80, 10, 6, 3, 1]
	 * @return
	 */
	public static int randomIndex(int[] random) {
		int count = sum(random);
		int pos = randomNum(1, count);
		count = 0;
		for (int i = 0; i < random.length; i++) {
			if (count < pos && pos <= count + random[i])
				return i;
			count += random[i];
		}
		return 0;
	}

	/**
	 * 求和
	 * 
	 * @param ints
	 * @return
	 */
	private static int sum(int[] ints) {
		int count = 0;
		for (int i = 0; i < ints.length; i++)
			count += ints[i];
		return count;
	}

	/**
	 * 求和
	 * 
	 * @param ints
	 * @return
	 */
	private static int sum(Integer[] ints) {
		int count = 0;
		for (int i = 0; i < ints.length; i++)
			count += ints[i];
		return count;
	}

	/**
	 * 通过指定区间机率随机产生区间字符串
	 * 
	 * @param src
	 *            源字符串“200;201;202;203;204;205;206,10_10_10_10_10_10_10”
	 * @param split
	 *            数据分隔符“,”
	 * @param rateSplit
	 *            区间概率分隔符“_”
	 * @param rsSplit
	 *            区间分割符 “;”
	 * @return
	 */
	public static String randomStr(String src, String split, String rateSplit,
			String rsSplit) {
		String[] arr = src.split(split);
		if (arr.length == 2) {
			int index = randomIndex(arr[1], rateSplit);
			arr = arr[0].split(rsSplit);
			src = arr[index];
		}
		return src;
	}

	public static int randomNum(int value) {
		return randomNum(0, value - 1);
	}

	public static int rangeRandom(int min, int max) {
		return (int) (min + Math.random() * (max - min));
	}
}
