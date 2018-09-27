package com.xgame.logic.server.core.utils.geometry.data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.utils.geometry.data.transform.SpriteTransform;

import io.protostuff.Tag;

/**
 *
 *2016-9-02  20:25:39
 *@author ye.yuan
 *
 */
public class SpaceSpriteInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* db */
	/**
	 * 唯一id 如果是玩家  就是roleId
	 */
	@Tag(1)
	public long uid;

	/**
	 * 精灵名字   只有玩家精灵有  其他自己读多语言配置
	 */
	@Tag(2)
	public String spriteName;

	/**
	 * 精灵等级
	 */
	@Tag(3)
	public int level=1;

	/**
	 * 精灵类型
	 */
	@Tag(4)
	public int spriteType;

	/**
	 * 所在服务器id 如果是玩家 指的是玩家所在逻辑服id 如果是 精灵   就是世界服id
	 */
	@Tag(5)
	public int serverKey;

	/**
	 * 显示类型  目前没用到   
	 */
	@Tag(6)
	public int showType;

	/**
	 * 空间变换信息 
	 */
	@Tag(7)
	public SpriteTransform transform;

	/**
	 * 是否在地图上显示
	 */
	@Tag(8)
	public boolean isShow;
	
	/**
	 * 当前正在和我交互的人
	 */
	@Tag(9)
	public InteractiveInfo interactiveInfo = new InteractiveInfo();
	
	/**
	 * 我的出征队伍
	 */
	@Tag(10)
	public  Map<Long, SodierTeam> myTeam = new ConcurrentHashMap<>();
	
	/**
	 * 被攻打时间
	 */
	@Tag(12)
	public long attackTime;
	

}
