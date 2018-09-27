package com.xgame.logic.server.game.awardcenter.converter;

import java.util.Collection;

import com.xgame.logic.server.game.awardcenter.bean.AwardPro;
import com.xgame.logic.server.game.awardcenter.entity.AwardDB;
import com.xgame.logic.server.game.awardcenter.message.ResAwardCenterMessage;

public class AwardConverter {
	
	public static ResAwardCenterMessage converterAwardCenterMsg(Collection<AwardDB> awardDBs){
		ResAwardCenterMessage resAwardCenterMessage = new ResAwardCenterMessage();
		for(AwardDB awardDB : awardDBs){
			AwardPro awardPro = new AwardPro();
			awardPro.index = awardDB.getIndex();
			awardPro.itemId = awardDB.getAwardId();
			awardPro.num = awardDB.getNum();
			awardPro.type = awardDB.getType();
			resAwardCenterMessage.awardList.add(awardPro);
		}
		return resAwardCenterMessage;
	}
}
