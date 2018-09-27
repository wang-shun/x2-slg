package com.xgame.logic.server.core.utils.geometry.space;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.xgame.logic.server.core.utils.geometry.conf.SpaceConf;
import com.xgame.logic.server.core.utils.geometry.data.transform.SpriteTransform;
import com.xgame.logic.server.core.utils.geometry.node.SpaceNode;


/**
 *大型空间实现类   该地图太大  不提供时时监控  所以   精灵改变位置都当作  传送处理   任何其他地方改变位置信息的方法均无效     
 *2016-8-31  21:32:22
 *@author ye.yuan
 *
 */
public class BigSpace extends Space{

	
	
	public BigSpace(SpaceConf conf) {
		super(conf);
	}

	/**
	 * 找空间位置 并且进入
	 * @param spaceSprite
	 * @return
	 */
	public SpaceNode findSpaceEnter(SpriteTransform transform){
		SpaceNode  spaceNodes = null;
		SpaceNode coor = findSpaceTransform(transform);
		if(coor!=null){
			spaceNodes = enterSpace(coor.getSpaceTransform().getLocation().getX(), coor.getSpaceTransform().getLocation().getY(), transform);
		}
		return spaceNodes;
	}
	
	/**
	 * 在整张地图上找个可用的格子 放玩家
	 * 如果200次都没命中  那么在空节点队列里面取一个给精灵使用 如果空格队列已不存在 直接返回
	 * @param spaceSprite
	 * @return
	 */
	public SpaceNode findSpaceTransform(SpriteTransform transform) {
		SpaceNode  spaceNode = null;
		if(canUseNodes.size()>0){
			int nextInt = ThreadLocalRandom.current().nextInt(0, canUseNodes.size()-1);
			spaceNode = canUseNodes.get(nextInt);
			if(spaceNode==null){
				Iterator<SpaceNode> iterator = canUseNodes.iterator();
				if (iterator.hasNext()) {
					spaceNode =  iterator.next();
				}
			}
		}
		return spaceNode;
	}
	
	
}
