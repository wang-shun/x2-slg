package com.xgame.config.science;

import java.util.HashMap;
import java.util.Map;

import com.xgame.common.AwardConfList;
import com.xgame.common.BuildCondtitionBean;
import com.xgame.common.ItemConf;
import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.util.ConfigUtil;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class SciencePirFactory extends BasePriFactory<SciencePir>{
	

	public void init(SciencePir pir) {
		pir.require_id = new HashMap<Integer,BuildCondtitionBean>();
		pir.building_id = new HashMap<Integer,BuildCondtitionBean>();
		pir.cd = new HashMap<Integer,Integer>();
		pir.v1 = new AttributeConfMap(pir.ID);
		pir.cost_cash=new HashMap<Integer,Integer>();
		pir.cost_earth=new HashMap<Integer,Integer>();
		pir.cost_steel=new HashMap<Integer,Integer>();
		pir.cost_oil=new HashMap<Integer,Integer>();
		pir.strength=new HashMap<Integer,Integer>();
		pir.exp=new HashMap<Integer,AwardConfList>();
		pir.type2=new int[0];
	}
	
	@Override
	public void load(SciencePir pir) {
	}
	
	public int getFightPower(int sid,int level){
		SciencePir sciencePir = factory.get(sid);
		if(sciencePir!=null){
			Map<Integer, Integer> map = sciencePir.getStrength();
			if(map!=null){
				if(map.get(level) != null) {
					return map.get(level);
				}
			}
		}
		return 0;
	}
	
	/**
	 *自定义解析  building_id
	 */
	@ConfParse("building_id")
	public void building_idPares(String conf,SciencePir pir){
		Map<Integer, BuildCondtitionBean> buildCondtition = pir.getBuilding_id();
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			String[] split2 = split[i].split(",");
			for(int j=0;j<split2.length;j++){
				buildCondtition.put(i, new BuildCondtitionBean(split2[j]));
			}
		}
	}
	
	/**
	 *自定义解析  require_id
	 */
	@ConfParse("require_id")
	public void require_idPares(String conf,SciencePir pir){
		Map<Integer, BuildCondtitionBean> techCondtition = pir.getRequire_id();
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			String[] split2 = split[i].split(",");
			for(int j=0;j<split2.length;j++){
				techCondtition.put(i, new BuildCondtitionBean(split2[j]));
			}
		}
	}
	
	/**
	 *自定义解析  type2
	 */
	@ConfParse("type2")
	public void type2Pares(String conf,SciencePir pir){
		String[] split = conf.split(";");
		int [] targets = new int[split.length];
		for(int i=0;i<split.length;i++){
			targets[i] = Integer.parseInt(split[i]);
		}
		pir.type2 = targets;
	}
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,SciencePir pir){
		AttributeParser.parse(conf, pir.getV1());
	}
	
	/**
	 *自定义解析  cd
	 */
	@ConfParse("cd")
	public void cdPares(String conf,SciencePir pir){
		ConfigUtil.parseArr(conf, pir.getCd());
	}
	
	/**
	 *自定义解析  cost_cash
	 */
	@ConfParse("cost_cash")
	public void cost_cashPares(String conf,SciencePir pir){
		ConfigUtil.parseArr(conf, pir.getCost_cash());
	}
	
	/**
	 *自定义解析  cost_earth
	 */
	@ConfParse("cost_earth")
	public void cost_earthPares(String conf,SciencePir pir){
		ConfigUtil.parseArr(conf, pir.getCost_earth());
	}
	
	/**
	 *自定义解析  cost_steel
	 */
	@ConfParse("cost_steel")
	public void cost_steelPares(String conf,SciencePir pir){
		ConfigUtil.parseArr(conf, pir.getCost_steel());
	}
	
	/**
	 *自定义解析  cost_oil
	 */
	@ConfParse("cost_oil")
	public void cost_oilPares(String conf,SciencePir pir){
		ConfigUtil.parseArr(conf, pir.getCost_oil());
	}
	
	/**
	 *自定义解析  strength
	 */
	@ConfParse("strength")
	public void strengthPares(String conf,SciencePir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getStrength());
	}
	
	/**
	 *自定义解析  exp
	 */
	@ConfParse("exp")
	public void expPares(String conf,SciencePir pir){
		Map<Integer, AwardConfList> levelUpGiveLeaderExp= pir.getExp();
		int level;
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			AwardConfList itemConfs = new AwardConfList();
			String[] split2 = split[i].split(",");
			level = Integer.parseInt(split2[0]);
			for(int j=1;j<split2.length;j++){
				String[] split3 = split2[j].split("_");
				itemConfs.add(new ItemConf(Integer.parseInt(split3[0]), Integer.parseInt(split3[1])));
			}
			levelUpGiveLeaderExp.put(level, itemConfs);
		} 
	}
	
	
	@Override
	public SciencePir newPri() {
		return new SciencePir();
	}
	
	public static SciencePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final SciencePirFactory instance = new SciencePirFactory(); 
	
	
	public static SciencePirFactory getInstance() {
		return instance;
	}
}