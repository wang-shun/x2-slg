package com.xgame.logic.server.game.vip;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.VIP.VIPPir;
import com.xgame.config.VIP.VIPPirFactory;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.vip.message.ResVipMessage;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VipManager extends AbstractComponent<Player> implements IAttributeAddModule{
	/**
	 * vip最大等级
	 */
	public static final int MAX_LEVEL = 15;
	
	/**
	 * 更新vip等级经验信息
	 */
	public void send() {
		ResVipMessage message = new ResVipMessage();
		message.vipBean.exp=e.roleInfo().getVipInfo().getExp();
		message.vipBean.vipLevel=e.roleInfo().getVipInfo().getVipLevel();
		e.send(message);
	}
	
	/**
	 * vip等级提升
	 * @param exp
	 */
	public void vipLevelUp(int exp){
		if(exp < 0){
			return;
		}
		int oldLevel = e.roleInfo().getVipInfo().getVipLevel();
		int vipLevel = e.roleInfo().getVipInfo().getVipLevel();
		int vipExp=e.roleInfo().getVipInfo().getExp();
		int currentExp = vipExp+exp;
		boolean flag = true;
		do{
			if(vipLevel == MAX_LEVEL){
				flag = false;
			}else{
				VIPPir vipPir = VIPPirFactory.get(vipLevel+1);
				if(vipPir == null){
					Language.ERRORCODE.send(e, ErrorCodeEnum.E001_LOGIN.CODE6, VIPPir.class.getSimpleName(),vipLevel+1);
					return;
				}
				if(currentExp < Integer.valueOf(vipPir.getPoint())){
					flag = false;
				}else{
					vipLevel++;
				}
			}
		}while(flag);
		e.roleInfo().getVipInfo().setExp(currentExp);
		e.roleInfo().getVipInfo().setVipLevel(vipLevel);
		InjectorUtil.getInjector().dbCacheService.update(e);
		//发送vip信息给客户端
		this.send();
		//vip等级变化刷新属性
		if(vipLevel != oldLevel){
			VIPPir nowVipPir = VIPPirFactory.get(vipLevel);
			if(nowVipPir == null){
				Language.ERRORCODE.send(e, ErrorCodeEnum.E001_LOGIN.CODE6, VIPPir.class.getSimpleName(),vipLevel);
				return;
			}
			//先删掉之前vip所增加的所有属性
//			e.getAttributeAppenderManager().deleteAppenderLibrary(AttributeAppenderEnum.VIP.ordinal(), this.getVipPir().getAttr());
//			//在把本次vip加的数值加进去
//			e.getAttributeAppenderManager().rebuild(this.getVipPir().getAttr(), 1, AttributeAppenderEnum.VIP.ordinal(), true);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(e));
		}
	}

	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.VIP, nodeAttrMap.get(node));
		}
		return resultMap;
	}

	@Override
	public Map<AttributeNodeEnum, Double> attributeAdd(Player player,
			AttributesEnum attributeEnum,
			AttributeNodeEnum... attributeNodeEnums) {
		int vipLevel = player.roleInfo().getVipInfo().getVipLevel();
		VIPPir nowVipPir = VIPPirFactory.get(vipLevel);
		AttributeConfMap confMap = nowVipPir.getAttr();
		double value = 0;
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		for(AttributeNodeEnum node : AttributeNodeEnum.values()){
			if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0){
				continue;
			}
			LibraryConf libraryConf = confMap.getLibraryConfs().get(1, node.getCode());
			if(libraryConf != null && libraryConf.containsKey(attributeEnum.getId())){
				double nodeValue = libraryConf.get(attributeEnum.getId());
				value += nodeValue;
				if(nodeValue > 0){
					if(!valueOfNodeMap.containsKey(node.getCode())){
						valueOfNodeMap.put(node, nodeValue);
					}else{
						valueOfNodeMap.put(node, valueOfNodeMap.get(node.getCode()) + nodeValue);
					}
				}
			}
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,VIPPir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}
}
