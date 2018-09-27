package com.xgame.logic.server.core.utils.geometry.space;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.HashBasedTable;
import com.xgame.logic.server.core.utils.MathUtil;
import com.xgame.logic.server.core.utils.geometry.FindBorderAreaResult;
import com.xgame.logic.server.core.utils.geometry.conf.SpaceConf;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.data.transform.SpriteTransform;
import com.xgame.logic.server.core.utils.geometry.matrix.Matrix;
import com.xgame.logic.server.core.utils.geometry.node.QuadNode;
import com.xgame.logic.server.core.utils.geometry.node.SpaceNode;

/**
 *二维空间对象  非线程安全   任何对其更改维护 都必须考虑线程安全问题
 *2016-8-31  21:31:55
 *@author ye.yuan
 *
 */
@Slf4j
public abstract class Space {
	
	/**
	 * 长,宽,半长,半宽 ,面积
	 */
	protected int capacityX,capacityY,halfX,halfY,size;
	
	/**
	 * 节点占用格子长度
	 */
	protected int nodeWidth;
	
	/**
	 * 节点占用格子高
	 */
	protected int nodeHeight;
	
	/**
	 * capacityX/nodeWidth 的值
	 */
	protected int normalWidth;
	
	/**
	 * capacityY/nodeHeight 的值
	 */
	protected int normalHeight;
	
	/**
	 * normalWidth*normalHeight 的值
	 */
	protected int normalSize;
	
	/**
	 * 二维空间节点对象表
	 */
	protected HashBasedTable<Integer, Integer, SpaceNode> nodes;
	
	/**
	 * 一维空间节点对象表 
	 */
	protected Map<Integer, SpaceNode> oneDimensionalNodes;
	
	/**
	 * 目前还能给精灵使用的空间节点队列   利用LinkedList 的特性来高效的操作  
	 */
	protected List<SpaceNode> canUseNodes;
	
	
	protected Matrix matrix;
	
	
	/**
	 * 初始化一个指定容量  空的节点对象表
	 * @param capacity
	 */
	public Space(SpaceConf conf) {
		this.capacityX=conf.getCapacityX();
		this.capacityY=conf.getCapacityY();
		this.size = capacityX * capacityY;
		this.halfX = capacityX/2;
		this.halfY = capacityY/2;
		this.nodeWidth=conf.getNodeWidth();
		this.nodeHeight=conf.getNodeHeight();
		this.normalWidth=capacityX/nodeWidth;
		this.normalHeight=capacityY/nodeHeight;
		this.matrix = new Matrix(conf.getH(),conf.getV());
		this.nodes = HashBasedTable.create(capacityX, capacityY);
		this.canUseNodes = new LinkedList<>();
		this.oneDimensionalNodes=new ConcurrentHashMap<>();
		this.normalSize=normalWidth*normalHeight;
	}
	
	/**
	 * 把这个节点添加到空间内
	 * @param spaceNode
	 * @return
	 */
	public boolean addNode(SpaceNode spaceNode) {
		Vector2 location = spaceNode.getSpaceTransform().getLocation();
		int index = mathIndex(location.getX(),location.getY());
		if(index>size){
			log.error(" location large index: "+index+" size: "+size);
			return false;
		}
		SpaceNode node = nodes.get(location.getX(), location.getY());
		if(node==null){
			spaceNode.setId(index);
			nodes.put(location.getX(), location.getY(), spaceNode);
			oneDimensionalNodes.put(index, spaceNode);
			if(!spaceNode.isBlock()){
				canUseNodes.add(spaceNode);
			}
			spaceNode.setSpace(this);
		}
		return true;
	}
	
	/**
	 * 转换成一维点
	 * @param x
	 * @param y
	 * @return
	 */
	public int mathIndex(int x,int y){
		return y*capacityX+x;
	}
	
	/**
	 * 获取一个空间点
	 * @param x
	 * @param y
	 * @return
	 */
	public SpaceNode getNode(int x,int y){
		return nodes.get(x, y);
	}
	
	/**
	 * 根据一维id获取节点
	 * @param id
	 * @return
	 */
	public SpaceNode getNode(int id){
		return oneDimensionalNodes.get(id);
	}
	
	/**
	 * 找空间位置 并且进入
	 * @param spaceSprite
	 * @return
	 */
	public SpaceNode findSpaceEnter(SpriteTransform transform){
		return null;
	}
	
	/**
	 * 精灵进入指定位置
	 * @param x
	 * @param y
	 * @param spaceSprite
	 * @return
	 */
	public SpaceNode enterSpace(int x,int y,SpriteTransform transform){
		//申请定量内存
		SpaceNode  node = getNode(x, y);
		//本次占地格子要被本地图完全包含 才能添加
		if(node!=null&&!transform.isSameLocation(x,y)){
			transform.setLastLocation(transform.getLocation().clone());
			transform.setLocation(node.getSpaceTransform().getLocation().clone());
			//成功后把格子标记为可使用
			
			if(node.setUser(transform.getUid())){
				canUseNodes.remove(node);
			}
			Vector2 location = transform.getLastLocation();
			quitSpace(location.getX(), location.getY(), transform);
		}
		return node;
	}
	
	/**
	 * 退出空间
	 * @param x
	 * @param y
	 * @param spaceSprite
	 */
	public boolean quitSpace(int x,int y,SpriteTransform transform){
		SpaceNode  node = getNode(x, y);
		if(node!=null){
			transform.setLastLocation(transform.getLocation().clone());
			transform.setLocation(new Vector2(-1, -1));
			node.unUser();
			canUseNodes.add(node);
			return true;
		}
		return false;
	}
	
	/**
	 * 找这个点的区域坐标
	 * @param x
	 * @param y
	 * @param nodeNum
	 * @return
	 */
	public List<Long> findRangeSprite(int x,int y,int nodeNum) {
		//区域范围
		int[] rectRange = MathUtil.getRectRange(x, y, nodeNum);
		return findRangeSprite(rectRange);
	}
	
	/**
	 * 找出区域范围内精灵
	 * @param rectRange
	 * @return
	 */
	public List<Long> findRangeSprite(int[] rectRange){
		List<Long> sprites = new ArrayList<>();
		for (int i = rectRange[0]; i <= rectRange[1]; i++) {
			for (int j = rectRange[2]; j <= rectRange[3]; j++) {
				if(i>=0&&i<nodes.size()){
					SpaceNode spaceNode = nodes.get(i, j);
					if(spaceNode!=null&&spaceNode.isUser()){
						sprites.add(spaceNode.getUserUid());
					}
				}else{
					break;
				}
			}
		}
		return sprites;
	}
	
	
	/**
	 * 找带描边的格子  给递归实现的方法
	 * 半递归, 如果完全递归找,会多踩很多个格子,所以只有x方向递归
	 * 描边是指 当前建筑额外格的范围 
	 * @param country 
	 * @param x
	 * @param y
	 * @param size
	 * @param bordSize 描边像素
	 * @return
	 */
	public FindBorderAreaResult findBorderArea(int x,int y,int size,int bordSize){
		FindBorderAreaResult borderAreaResult = new FindBorderAreaResult();
		//算出描边后的y
		int len = y + size+bordSize;
		//后的x
		int i=y-bordSize;
		if(i<0){
			i=y;
		}
		//后的y
		int j=x-bordSize;
		if(j<0){
			j=x;
		}
		//后的面积大小
		int n = size+bordSize+bordSize;
		//-->扫描   x-->递归    y-->遍历  
		for(;i<len;i++){
			QuadNode	 node = (QuadNode)getNode(j, i);
			if(node!=null){
				node.findBorderArea(borderAreaResult,0,n*n,x,y,size,j+n,i+n);
			}
		}
		return borderAreaResult;
	}

	public int getCapacityX() {
		return capacityX;
	}

	public void setCapacityX(int capacityX) {
		this.capacityX = capacityX;
	}

	public int getCapacityY() {
		return capacityY;
	}

	public void setCapacityY(int capacityY) {
		this.capacityY = capacityY;
	}

	public int getHalfX() {
		return halfX;
	}

	public void setHalfX(int halfX) {
		this.halfX = halfX;
	}

	public int getHalfY() {
		return halfY;
	}

	public void setHalfY(int halfY) {
		this.halfY = halfY;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
	}

	public int getNormalWidth() {
		return normalWidth;
	}

	public void setNormalWidth(int normalWidth) {
		this.normalWidth = normalWidth;
	}

	public int getNormalHeight() {
		return normalHeight;
	}

	public void setNormalHeight(int normalHeight) {
		this.normalHeight = normalHeight;
	}

	public int getNormalSize() {
		return normalSize;
	}

	public void setNormalSize(int normalSize) {
		this.normalSize = normalSize;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}
	
}

