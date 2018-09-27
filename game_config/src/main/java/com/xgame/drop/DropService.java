package com.xgame.drop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;


/**
 * 掉落
 * @author jacky.jiang
 *
 */
public class DropService {
	
	private static final Random random = new Random();
	
	/**
	 * @param dropList
	 * @return
	 */
	public static <T extends IDrop> T getDrop(List<T> dropList) {
		/**
		 * 新建对象 防止引用传递修改对象
		 */
		List<T> newList = Lists.newArrayList(dropList);
		List<T> list = getDrop(newList,1);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 处理掉落
	 * @param dropList
	 * @return
	 */
	public static <T extends IDrop> List<T> getDrop(List<T> dropList,int num) {
		/**
		 * 新建对象 防止引用传递修改对象
		 */
		List<T> newList = Lists.newArrayList(dropList);
		if(newList == null) {
			return null;
		}
		int totalValue = 0;
		for(T drop : newList) {
			totalValue += drop.getValue();
		}
		List<T> list = new ArrayList<T>();
		if(num == newList.size()){
			return newList;
		}
		for(int i = 0;i < num;i++){
			int randomValue = random.nextInt(totalValue);
			int currentValue = 0;
			for(int j = newList.size() - 1;j >= 0;j--){
				T drop = newList.get(j);
				if(randomValue < drop.getValue() + currentValue) {
					list.add(newList.remove(j));
					totalValue -= drop.getValue();
					break;
				}
				currentValue += drop.getValue();
			}
		}
		return list;
	}
}
