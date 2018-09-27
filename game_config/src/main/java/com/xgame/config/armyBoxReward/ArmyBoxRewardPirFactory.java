package com.xgame.config.armyBoxReward;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.item.ItemBox;
import com.xgame.config.item.ItemBoxControl;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
@Slf4j
public class ArmyBoxRewardPirFactory extends BasePriFactory<ArmyBoxRewardPir>{
	

	public void init(ArmyBoxRewardPir pir) {
		pir.LV1 = new ItemBoxControl();
		pir.LV2 = new ItemBoxControl();
		pir.LV3 = new ItemBoxControl();
		pir.LV4 = new ItemBoxControl();
		pir.LV5 = new ItemBoxControl();
	}
	
	@Override
	public void load(ArmyBoxRewardPir pir) {
		
	}
	
	/**
	 *自定义解析  LV1
	 */
	@ConfParse("LV1")
	public void LV1Pares(String conf,ArmyBoxRewardPir pir){
		ItemBoxControl randomBoxControl = pir.getLV1();
		String[] config = conf.split(";");
		if(config.length != 2) {
			String error = String.format("联盟宝箱奖励配置错误, value:[%s]", conf);	
			log.error(error);
			throw new RuntimeException(error);
		}
		
		String[] itemConfigs = config[0].split(",");
		for (String itemCfg : itemConfigs) {
			ItemBox box = new ItemBox();
			box.settId(Integer.valueOf(itemCfg.split("_")[0]));
			box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
			
			// 权重
			box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
			// 本次权重边界计算
			randomBoxControl.getItemBoxs().add(box);
		}
		// 设置随机次数
		randomBoxControl.setCount(Integer.valueOf(config[1]));
	}
	
	/**
	 *自定义解析  LV2
	 */
	@ConfParse("LV2")
	public void LV2Pares(String conf,ArmyBoxRewardPir pir){
		ItemBoxControl randomBoxControl = pir.getLV2();
		String[] config = conf.split(";");
		if(config.length != 2) {
			String error = String.format("联盟宝箱奖励配置错误, value:[%s]", conf);	
			log.error(error);
			throw new RuntimeException(error);
		}
		
		String[] itemConfigs = config[0].split(",");
		for (String itemCfg : itemConfigs) {
			ItemBox box = new ItemBox();
			box.settId(Integer.valueOf(itemCfg.split("_")[0]));
			box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
			
			// 权重
			box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
			// 本次权重边界计算
			randomBoxControl.getItemBoxs().add(box);
		}
		// 设置随机次数
		randomBoxControl.setCount(Integer.valueOf(config[1]));
	}
	
	/**
	 *自定义解析  LV3
	 */
	@ConfParse("LV3")
	public void LV3Pares(String conf,ArmyBoxRewardPir pir){
		ItemBoxControl randomBoxControl = pir.getLV3();
		String[] config = conf.split(";");
		if(config.length != 2) {
			String error = String.format("联盟宝箱奖励配置错误, value:[%s]", conf);	
			log.error(error);
			throw new RuntimeException(error);
		}
		
		String[] itemConfigs = config[0].split(",");
		for (String itemCfg : itemConfigs) {
			ItemBox box = new ItemBox();
			box.settId(Integer.valueOf(itemCfg.split("_")[0]));
			box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
			
			// 权重
			box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
			// 本次权重边界计算
			randomBoxControl.getItemBoxs().add(box);
		}
		// 设置随机次数
		randomBoxControl.setCount(Integer.valueOf(config[1]));
	}
	
	/**
	 *自定义解析  LV4
	 */
	@ConfParse("LV4")
	public void LV4Pares(String conf,ArmyBoxRewardPir pir){
		ItemBoxControl randomBoxControl = pir.getLV4();
		String[] config = conf.split(";");
		if(config.length != 2) {
			String error = String.format("联盟宝箱奖励配置错误, value:[%s]", conf);	
			log.error(error);
			throw new RuntimeException(error);
		}
		
		String[] itemConfigs = config[0].split(",");
		for (String itemCfg : itemConfigs) {
			ItemBox box = new ItemBox();
			box.settId(Integer.valueOf(itemCfg.split("_")[0]));
			box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
			
			// 权重
			box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
			// 本次权重边界计算
			randomBoxControl.getItemBoxs().add(box);
		}
		// 设置随机次数
		randomBoxControl.setCount(Integer.valueOf(config[1]));
	}
	
	/**
	 *自定义解析  LV5
	 */
	@ConfParse("LV5")
	public void LV5Pares(String conf,ArmyBoxRewardPir pir){
		ItemBoxControl randomBoxControl = pir.getLV5();
		String[] config = conf.split(";");
		if(config.length != 2) {
			String error = String.format("联盟宝箱奖励配置错误, value:[%s]", conf);	
			log.error(error);
			throw new RuntimeException(error);
		}
		
		String[] itemConfigs = config[0].split(",");
		for (String itemCfg : itemConfigs) {
			ItemBox box = new ItemBox();
			box.settId(Integer.valueOf(itemCfg.split("_")[0]));
			box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
			
			// 权重
			box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
			// 本次权重边界计算
			randomBoxControl.getItemBoxs().add(box);
		}
		// 设置随机次数
		randomBoxControl.setCount(Integer.valueOf(config[1]));
	}
	
	@Override
	public ArmyBoxRewardPir newPri() {
		return new ArmyBoxRewardPir();
	}
	
	public static ArmyBoxRewardPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyBoxRewardPirFactory instance = new ArmyBoxRewardPirFactory(); 
	
	
	public static ArmyBoxRewardPirFactory getInstance() {
		return instance;
	}
}