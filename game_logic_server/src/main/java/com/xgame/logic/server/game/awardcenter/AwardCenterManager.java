package com.xgame.logic.server.game.awardcenter;

import java.util.Collection;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.awardcenter.converter.AwardConverter;
import com.xgame.logic.server.game.awardcenter.entity.AwardDB;
import com.xgame.logic.server.game.awardcenter.message.ReqGetAwardMessage;
import com.xgame.logic.server.game.awardcenter.message.ResAwardCenterMessage;
import com.xgame.logic.server.game.player.entity.Player;

/**
 *
 *2016-12-17  16:00:42
 *@author ye.yuan
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AwardCenterManager extends AbstractComponent<Player>{
	
	/**
	 * 奖励中心固定数量上限
	 */
	public static final int SIZE = 200;
	
	
	/**
	 * 发送全部奖励数据
	 */
	public void send(){
		sendAwardsToClient(e.roleInfo().getAwards().getAwardDBs().values());
	}
	
	/**
	 * 生成奖励id
	 * @return
	 */
	public int newId(){
		if(e.roleInfo().getAwards().getAwardDBs().isEmpty()){
			return 0;
		}else{
			Integer lastKey = e.roleInfo().getAwards().getAwardDBs().lastKey();
			if(lastKey==Integer.MAX_VALUE){
				return 0;
			}
			return lastKey+1;
		}
	}
	
	/**
	 * 添加奖励
	 * @param awardDB
	 */
	public void addAward(AwardDB awardDB) {
		int newId = newId();
		awardDB.setIndex(newId);
		e.roleInfo().getAwards().getAwardDBs().put(newId, awardDB);
		if(e.roleInfo().getAwards().getAwardDBs().size()>=SIZE) {
			e.roleInfo().getAwards().getAwardDBs().pollFirstEntry();
			InjectorUtil.getInjector().dbCacheService.update(e);
		}
		 
	}
	
	
	/**
	 * 更新一些奖励到客户端
	 * @param awards
	 */
	public void sendAwardsToClient(Collection<AwardDB> awards){
		ResAwardCenterMessage centerMessage = AwardConverter.converterAwardCenterMsg(awards);
		
		e.send(centerMessage);
	}
	
	/**
	 * 领取奖励
	 * @param index
	 * @return
	 */
	public ReqGetAwardMessage receive(int index) {
		ReqGetAwardMessage reqGetAwardMessage = new ReqGetAwardMessage();
		reqGetAwardMessage.index = index;
		AwardDB awardDB = e.roleInfo().getAwards().getAwardDBs().get(index);
		if(awardDB == null) {
			Language.ERRORCODE.send(e, ErrorCodeEnum.E200_AWARD_CENTER.CODE1);
			return reqGetAwardMessage;
		}
		
		AwardUtil awardCenterSystem = AwardUtil.values()[awardDB.getOrdinal()];
		awardCenterSystem.receiveCenter(e, awardDB, GameLogSource.DEFAULT);
		e.roleInfo().getAwards().getAwardDBs().remove(index);
		
		InjectorUtil.getInjector().dbCacheService.update(e);
		Language.SUCCESSTIP.send(e, SuccessTipEnum.E200_AWARD_CENTER.CODE1);
		return reqGetAwardMessage;
	}
	
}
