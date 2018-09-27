/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 基于map的比较器
 * @author jiangxt
 *
 */
public class MapRankableComparator implements Comparator<MapRankable> {
	
	private List<String[]> compareEntryList = new ArrayList<String[]>();
	
	private String compareExpresion;
	
	/**
	 * 构建比较器
	 * @param compareExpresion  level DESC, id ASC
	 */
	public MapRankableComparator(String compareExpresion){
		this.compareExpresion = compareExpresion;
		for(String compareToken : compareExpresion.trim().split(",")){
			String[] array = compareToken.trim().split(" +");
			String field = array[0].trim();
			String orderBy = array[1].trim().toUpperCase();
			compareEntryList.add(new String[]{field, orderBy});
		}
	}
	

	@Override
	public int compare(MapRankable o1, MapRankable o2) {
		CompareBuilder compareBuilder = new CompareBuilder();
		for(String[] array : this.compareEntryList){
			String field = array[0];
			String orderBy = array[1];
			
			Comparable<?> leftValue = (Comparable<?>)o1.getValue(field);
			Comparable<?> rightValue = (Comparable<?>)o2.getValue(field);
			
			if(leftValue instanceof Number){
				leftValue = ((Number)leftValue).longValue();
			}
			
			if(rightValue instanceof Number){
				rightValue = ((Number)rightValue).longValue();
			}
			
			if("ASC".equalsIgnoreCase(orderBy)){
				compareBuilder.asc(leftValue, rightValue);
			} else {
				compareBuilder.desc(leftValue, rightValue);
			}
		}
		
		return compareBuilder.compare();
	}


	@Override
	public String toString() {
		return this.compareExpresion;
	}
	
}
