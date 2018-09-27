package com.xgame.config.flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-09 19:22:33 
 */
public class FlagPirFactory extends BasePriFactory<FlagPir>{
	
	Map<Integer, List<Integer>> partys = new HashMap<>();
	
	Map<Integer, List<Integer>> teams = new HashMap<>();

	public void init(FlagPir pir) {
		
	}
	
	@Override
	public void load(FlagPir pir) {
		if(pir.type_1 == 1){
			List<Integer> list = partys.get(pir.type_2);
			if(list==null){
				partys.put(pir.type_2, list = new ArrayList<>());
			}
			list.add(pir.ID);
		}
		else if(pir.type_1 == 2){
			List<Integer> list = teams.get(pir.type_2);
			if(list==null){
				teams.put(pir.type_2, list = new ArrayList<>());
			}
			list.add(pir.ID);
		}
	}
	
	public Integer[] getRandomPartyFlag(){
		return randomList(partys);
	}
	
	public Integer[] getRandomTeamFlag(){
		return randomList(partys);
	}
	
	private Integer[] randomList(Map<Integer, List<Integer>> map){
		List<Integer> arr = new ArrayList<Integer>(3);
		Iterator<List<Integer>> iterator = map.values().iterator();
		while (iterator.hasNext()) {
			List<Integer> next = iterator.next();
			int i = RandomUtils.nextInt(0, next.size());
			arr.add(next.get(i));
		}
		return arr.toArray(new Integer[0]);
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,FlagPir pir){
	}
	
	@Override
	public FlagPir newPri() {
		return new FlagPir();
	}
	
	public static FlagPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final FlagPirFactory instance = new FlagPirFactory(); 
	
	
	public static FlagPirFactory getInstance() {
		return instance;
	}

	public Map<Integer, List<Integer>> getPartys() {
		return partys;
	}

	public void setPartys(Map<Integer, List<Integer>> partys) {
		this.partys = partys;
	}

	public Map<Integer, List<Integer>> getTeams() {
		return teams;
	}

	public void setTeams(Map<Integer, List<Integer>> teams) {
		this.teams = teams;
	}
}