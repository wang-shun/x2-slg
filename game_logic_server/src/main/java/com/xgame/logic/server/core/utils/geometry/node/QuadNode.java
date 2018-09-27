package com.xgame.logic.server.core.utils.geometry.node;

import com.google.common.collect.HashBasedTable;
import com.xgame.logic.server.core.utils.geometry.FindBorderAreaResult;
import com.xgame.logic.server.core.utils.geometry.conf.SpaceConf;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;

/**
 *
 *2016-10-29  17:54:45
 *@author ye.yuan
 *
 */
public class QuadNode extends SpaceNode{


	/**
	 * 9点方向邻居
	 */
	QuadNode  leaf9;
	
	/**
	 * 12点邻居
	 */
	QuadNode  leaf12; 
	
	/**
	 * 3点方向邻居
	 */
	QuadNode  leaf3;
	
	/**
	 * 6点邻居
	 */
	QuadNode  leaf6;
	
	public QuadNode(int x, int y) {
		super(x, y);
	}
	
	
	public void load(SpaceConf conf,HashBasedTable<Integer, Integer, SpaceNode> spaceNodes){
		Vector2 location = spaceTransform.getLocation();
		QuadNode lastNode = null;
		int lastX=location.getX()-1;
		if (location.getX() != 0 && spaceNodes.contains(lastX, location.getY())) {
			lastNode = (QuadNode)spaceNodes.get(lastX, location.getY());
			if(conf.getH()==-1){
				lastNode.leaf9=this;
				leaf3 = lastNode;
			}else if(conf.getH()==1){
				lastNode.leaf3 = this;
				leaf9 = lastNode;
			}
		}
		int lastY=location.getY()-1;
		if (location.getY() != 0 && spaceNodes.contains(location.getX(), lastY)) {
			lastNode = (QuadNode)spaceNodes.get(location.getX(), lastY);
			if(conf.getV()==-1){
				leaf12=lastNode;
				lastNode.leaf6=this;
			}else if(conf.getV()==1){
				leaf6 = lastNode;
				lastNode.leaf12 = this;
			}
		}
	}
	
	
	/**
	 * 找指定格子数 并标出边界
	 * @param areaResult
	 * @param count 当前递归次数
	 * @param maxCount 最大递归次数 
	 * @param startX 开始X
	 * @param startY 开始Y
	 * @param targerX 目标x
	 * @param targerY 目标y
	 */
	public  void  findBorderArea(FindBorderAreaResult areaResult,int count,int maxCount,int startX,int startY,int size,int targerX,int targerY){
		if(count>=maxCount){ 
			return;
		}
		count++;
		int ix = spaceTransform.getLocation().getAbsX();
		int iy = spaceTransform.getLocation().getAbsY();
		if(ix<targerX&&iy<targerY){
			if(ix>=startX&&ix < startX+size&&
			   iy>=startY&&iy < startY+size){
				areaResult.getBuildMap().put(id, this);
			}else{
				areaResult.getBorderMap().put(id, this);
			}
			areaResult.getFindMap().put(id, this);
			if(leaf3!=null){
				leaf3.findBorderArea(areaResult,count,maxCount,startX,startY,size,targerX,targerY);
			}
		}
	}


	public QuadNode getLeaf9() {
		return leaf9;
	}

	public void setLeaf9(QuadNode leaf9) {
		this.leaf9 = leaf9;
	}

	public QuadNode getLeaf12() {
		return leaf12;
	}

	public void setLeaf12(QuadNode leaf12) {
		this.leaf12 = leaf12;
	}

	public QuadNode getLeaf3() {
		return leaf3;
	}

	public void setLeaf3(QuadNode leaf3) {
		this.leaf3 = leaf3;
	}

	public QuadNode getLeaf6() {
		return leaf6;
	}

	public void setLeaf6(QuadNode leaf6) {
		this.leaf6 = leaf6;
	}
	
}
