package com.xgame.logic.server.core.utils.geometry.space;

import com.xgame.logic.server.core.utils.MathUtil;
import com.xgame.logic.server.core.utils.geometry.conf.RingConf;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.matrix.Rect;
import com.xgame.logic.server.core.utils.geometry.node.QuadNode;

/**
 *  环形空间 类似 这样子   环形嵌套环形 每个环都在大环的正中间
 *  环形空间用于快捷精准的放置管理需要分层的地图格子  
 *  
 *  ##################
	##################
	##################
	###%%%%%%%%%%%%###
	###%%%%%%%%%%%%###
	###%%%%%%%%%%%%###
	###%%%@@@@@@%%%###
	###%%%@@@@@@%%%###
	###%%%@@++@@%%%###
	###%%%@@++@@%%%###
	###%%%@@@@@@%%%###
	###%%%@@@@@@%%%###
	###%%%%%%%%%%%%###
	###%%%%%%%%%%%%###
	###%%%%%%%%%%%%###
	##################
	##################
	##################
 * 
 * 2016-10-28 17:03:51
 *
 * @author ye.yuan
 *
 */
public class RingSpace extends Space {

	/**
	 * 区域环数组  低位为小环  高位大环
	 */
	RingArea[] ringAreas;

	/**
	 * 初始化环形世界  给定的环形大小一定是低位<高位
	 * 初始化是没排序的 依赖传入环的顺序
	 * @param ringConf
	 */
	public RingSpace(RingConf ringConf) {
		super(ringConf);
		/*
		 * 这里环形区域初始化
		 * 每个环形区域对象有2个环形遍 较大的边和内边  外边一定包含内边 
		 * 把较小的环当作较大的环内环边  
		 * 
		 */
		initArea: {
			int[][] arr = ringConf.getSize();
			ringAreas = new RingArea[arr.length];
			Rect minRect = new Rect();
			for (int i = 0; i < arr.length; i++) {
				RingArea ringArea = new RingArea();
				ringArea.setIndex(i);
				//设置当前环对象的外边矩形
				ringArea.setMaxRect(new Rect(ringConf.getH(), ringConf.getV(), halfX- (arr[i][0] / 2), halfY - (arr[i][1] / 2), arr[i][0],arr[i][1]));
				//把上一个内环区域的外边当本环的内边
				ringArea.setMinRect(minRect);
				minRect = ringArea.getMaxRect();
				ringAreas[i] = ringArea;
			}
			break initArea;
		}

		/*
		 * 初始化节点  用归一化的长宽来扫出格子区域
		 */
		initNode: {
			int y = 0;
			for (int i = 0; i < normalSize; i++) {
				//这里证明已到下一行  (即y++的时候)
				int x = i % normalWidth;
				if (i != 0 && x == 0) {
					y += 1;
				}
				//找到所属环
				RingArea newArea = findArea(ringConf.getNodeWidth(),ringConf.getNodeHeight(), x, y);
				//算出实际坐标
				int mx = matrix.transfomX(x)*nodeWidth;
				int my = matrix.transfomY(y)*nodeHeight;
				//插入环内
				Vector2 vector2 = new Vector2(mx, my);
				newArea.getNodeCoordinates().put(mx, my, vector2);
				newArea.getLinkedList().add(vector2);
				//生成节点
				QuadNode newNode = (QuadNode) ringConf.getFactory().newNode(newArea,mx,my);
				//加入节点
				addNode(newNode);
				//节点初始化
				newNode.load(ringConf, nodes);
			}
			//测试标记
//			for (int i = 0; i < canUseNodes.size(); i++) {
//				if (i % normalWidth == 0) {
//					System.out.print("\r\n");
//				}
//				System.out.print(canUseNodes.get(i).see);
//			}
			break initNode;
		}
	}

	/**
	 * 找到所属区域
	 * @param nodeWidth
	 * @param nodeHeight
	 * @param index
	 * @param x
	 * @param y
	 * @return
	 */
	private RingArea findArea(int nodeWidth, int nodeHeight, int x, int y) {
		for (int i = 0; i < ringAreas.length; i++) {
			Rect maxRect = ringAreas[i].getMaxRect();
			int w1 = maxRect.getSpaceTransform().getLeftDownPoint().getAbsX()/nodeWidth;
			int w2 = maxRect.getSpaceTransform().getRightUpPoint().getAbsX()/nodeWidth;
			int h1 = maxRect.getSpaceTransform().getLeftDownPoint().getAbsY()/nodeHeight;
			int h2 = maxRect.getSpaceTransform().getRightUpPoint().getAbsY()/nodeHeight;
			Rect minRect = ringAreas[i].getMinRect();
			//计算碰撞大边
			boolean maxResult = MathUtil.rectContain(w1, w2, h1, h2, x, y);
			w1 = minRect.getSpaceTransform().getLeftDownPoint().getAbsX()/nodeWidth;
			w2 = minRect.getSpaceTransform().getLeftDownPoint().getAbsX()/nodeWidth;
			h1 = minRect.getSpaceTransform().getLeftDownPoint().getAbsY()/nodeHeight;
			h2 = minRect.getSpaceTransform().getLeftDownPoint().getAbsY()/nodeHeight;
			//计算碰撞小边
			boolean minResult = MathUtil.rectContain(w1, w2, h1, h2, x, y);
			if (maxResult && !minResult) {
				return ringAreas[i];
			}
		}
		throw new IllegalArgumentException(
				" can no find the area type,   result: " + "    " + x + ":" + y);
	}
	
	public RingArea firstRingArea() {
		return ringAreas[0];
	}
	
	public RingArea lastRingArea() {
		return ringAreas[ringAreas.length-1];
	}

	public RingArea[] getRingAreas() {
		return ringAreas;
	}

	public void setRingAreas(RingArea[] ringAreas) {
		this.ringAreas = ringAreas;
	}
	

}
