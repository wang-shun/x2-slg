package com.xgame.config.talent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class TalentPirFactory extends BasePriFactory<TalentPir>{
	

	public void init(TalentPir pir) {
		pir.pre_id = new HashSet<Integer>();
		pir.attr = new AttributeConfMap(pir.id);
		pir.gs_bonus = new HashMap<Integer,Integer>();
		pir.target = new int[0]; 
	}
	
	@Override
	public void load(TalentPir pir) {
	}
	
	
	/**
	 *自定义解析  pre_id
	 */
	@ConfParse("pre_id")
	public void pre_idPares(String conf,TalentPir pir){
		Set<Integer> pre_id=pir.getPre_id();
		String[] split = conf.split(";");
		if(split.length>0){
			for(int i=0;i<split.length;i++){
				pre_id.add(Integer.parseInt(split[i]));
			}
		}else{
			pre_id.add(Integer.parseInt(conf));
		}
	}
	
	/**
	 *自定义解析  target
	 */
	@ConfParse("target")
	public void targetPares(String conf,TalentPir pir){
		String[] split = conf.split(";");
		int [] targets = new int[split.length];
		for(int i=0;i<split.length;i++){
			targets[i] = Integer.parseInt(split[i]);
		}
		pir.target =  targets;
	}
	
	
	/**
	 *自定义解析  attr
	 */
	@ConfParse("attr")
	public void attrPares(String conf,TalentPir pir){
		AttributeParser.parse(conf, pir.getAttr());
	}
	
	/**
	 *自定义解析  gs_bonus
	 */
	@ConfParse("gs_bonus")
	public void gs_bonusPares(String conf,TalentPir pir){
		Map<Integer, Integer> gs_bonusMap = pir.getGs_bonus();
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			int parseInt = Integer.parseInt(split[i]);
			gs_bonusMap.put(i+1, parseInt);
		}
		
	}
	
	@Override
	public TalentPir newPri() {
		return new TalentPir();
	}
	
	public static TalentPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TalentPirFactory instance = new TalentPirFactory(); 
	
	
	public static TalentPirFactory getInstance() {
		return instance;
	}
}