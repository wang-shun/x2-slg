package com.xgame.config.skill;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:40 
 */
public class SkillPirFactory extends BasePriFactory<SkillPir>{
	

	public void init(SkillPir pir) {
		
	}
	
	@Override
	public void load(SkillPir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  buffEnemy
	 */
	@ConfParse("buffEnemy")
	public void buffEnemyPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  buffFriend
	 */
	@ConfParse("buffFriend")
	public void buffFriendPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  buffSelf
	 */
	@ConfParse("buffSelf")
	public void buffSelfPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  buffMaster
	 */
	@ConfParse("buffMaster")
	public void buffMasterPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  nextSkill
	 */
	@ConfParse("nextSkill")
	public void nextSkillPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  fxShoot
	 */
	@ConfParse("fxShoot")
	public void fxShootPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  fxHit
	 */
	@ConfParse("fxHit")
	public void fxHitPares(String conf,SkillPir pir){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  shootSound
	 */
	@ConfParse("shootSound")
	public void shootSoundPares(String conf,SkillPir pir){
		
	}
	
	/**
	 *自定义解析  hitSound
	 */
	@ConfParse("hitSound")
	public void hitSoundPares(String conf,SkillPir pir){
		
	}
	
	@Override
	public SkillPir newPri() {
		return new SkillPir();
	}
	
	public static SkillPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final SkillPirFactory instance = new SkillPirFactory(); 
	
	
	public static SkillPirFactory getInstance() {
		return instance;
	}
}