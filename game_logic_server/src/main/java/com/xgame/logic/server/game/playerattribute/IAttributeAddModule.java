package com.xgame.logic.server.game.playerattribute;

import java.util.Map;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

/**
 * 属性加成接口
 * @author zehong.he
 *
 */
public interface IAttributeAddModule {

	/**
	 * 查询某个属性来源与各个模块的值
	 * @param playerId
	 * @param attributeEnum
	 * @param node
	 * @return
	 */
	public Map<AttributeFromEnum,Double> selectAttributeAdd(Player player,AttributesEnum attributeEnum,AttributeNodeEnum node);
	
	/**
	 * 玩家各节点属性
	 * @param player
	 * @param attributeEnum
	 * @param attributeNodeEnums 不传该参数就返回所有节点的值
	 * @return
	 */
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player,AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums);
}
