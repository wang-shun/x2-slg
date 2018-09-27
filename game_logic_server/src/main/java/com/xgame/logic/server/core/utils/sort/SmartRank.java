/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgame.logic.server.core.utils.JsonUtil;


/**
 * 精巧又可爱的排序对象
 * @author jacky
 *
 */
public class SmartRank {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 列表最大排名数(比如前30名参与排名这里就是30)
	 */
	private int maxSize = Integer.MAX_VALUE;
	
	/**
	 * 标识排名是否有脏数据了
	 */
	private volatile boolean dirty = false;
	
	/**
	 * key: 主键 value:可排名对象
	 */
	private final ConcurrentMap<Object, MapRankable> rank_map = new ConcurrentHashMap<Object, MapRankable>();
	
	/**
	 * 排名锁
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**
	 * 排名列表
	 */
	private final List<Object> rankList = new ArrayList<Object>();
	
	/**
	 * 进入排名的门槛(当排名大小达到maxSize时出现排名门槛)
	 */
	private MapRankable threshold;
	
	/**
	 * 比较器
	 */
	private Comparator<MapRankable> comparator;
	
	/**
	 * 宿主键名
	 */
	private String ownerKey = "ownerId";
	
	/**
	 * 构建排序对象
	 * @param comparator 排序对象
	 * @param ownerKey map中的主键id名
	 * @param orderList 有序的排序对象
	 * @param maxSize 最大排序大小
	 */
	public SmartRank(Comparator<MapRankable> comparator, String ownerKey, List<MapRankable> orderList, int maxSize){
		this.comparator = comparator;
		if(maxSize > 0){
			this.maxSize = maxSize;
		}
		this.initialize(orderList);
	} 
	
	/**
	 * 构建排序对象
	 * @param comparator 排序对象
	 * @param ownerKey map中的主键id名
	 * @param maxSize 最大排序大小
	 */
	public SmartRank(Comparator<MapRankable> comparator, String ownerKey, int maxSize) {
		this.comparator = comparator;
		this.ownerKey = ownerKey;
		if(maxSize > 0){
			this.maxSize = maxSize;
		}
	}
	
	/**
	 * 构建排序对象
	 * @param comparator 排序对象
	 * @param maxSize 最大排序大小
	 */
	public SmartRank(Comparator<MapRankable> comparator, int maxSize){
		this.comparator = comparator;
		if(maxSize > 0){
			this.maxSize = maxSize;
		}
	}
	
	/**
	 * 构建排序对象(大小不限)
	 * @param comparator 排序对象
	 * @param ownerKey map中的主键id名
	 */
	public SmartRank(Comparator<MapRankable> comparator, String ownerKey){
		this.comparator = comparator;
		this.ownerKey = ownerKey;
	}
	
	/**
	 * 构建排序对象(大小不限)
	 * @param comparator 排序对象
	 */
	public SmartRank(Comparator<MapRankable> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * 直接从排序描述串构建
	 * @see #describe()
	 * @param rankDescription
	 */
	@SuppressWarnings("unchecked")
	public SmartRank(String rankDescription) {
		Map<String, Object> map = null;
		try {
			map = JsonUtil.fromJSON(rankDescription, HashMap.class);
		} catch (Exception e) {
			logger.error("从排序描述串反序列化异常!", e);
			throw new RuntimeException("从排序描述串反序列化异常!", e);
		} 
		
		if(map == null || map.isEmpty()) {
			throw new IllegalArgumentException("错误的排序描述串!");
		}
		
		//排行的大小
		this.maxSize = ((Number)map.get(SIZE)).intValue();
		//map的key
		this.ownerKey = (String)map.get(OWNER);
		
		String orderBy = (String)map.get(ORDER_BY);
		this.comparator = new MapRankableComparator(orderBy);
		
		//排序列表
		List<Map<String, Object>> rankList = (List<Map<String, Object>>)map.get(RANK_LIST);
		if(rankList != null &&  !rankList.isEmpty()) {
			for(Map<String, Object> rankable : rankList) {
				MapRankable mapRankable = MapRankable.valueOf(rankable);
				this.compareAndRefresh(mapRankable);
			}
		}
	}
	
	private Object getOwner(MapRankable mapRankable){
		return mapRankable.getValue(ownerKey);
	}
	
	
	public int getMaxSize() {
		return maxSize;
	}
	/**
	 * 将指定的列表作为当前排序列表
	 * @param orderList
	 */
	public void initialize(List<? extends MapRankable> orderList){
		this.rankList.clear();
		this.rank_map.clear();
		int rank = 0;
		for(MapRankable t : orderList){
			this.rankList.add(getOwner(t));
			rank_map.put(getOwner(t), t);
			t.setRank(++rank);
		}
		this.threshold = this.getLastOne();
	}

	/**
	 * 将当前的排名与rankable比较,如果发现rankable将导致排名列表能会变化则重新排序
	 * @param rankable
	 * @return true-影响了排名 false-没有影响排名
	 */
	public RefreshResult compareAndRefresh(MapRankable rankable){
		
		MapRankable add = null;
		MapRankable remove = null;
		
		lock.writeLock().lock();
		try {
			boolean rankChanged = false;
			
			if(dirty){//首先发现是脏的就先重新排一下
				this.reorder();
				this.dirty = false;
				this.threshold = this.getLastOne();
				rankChanged = true;
			} 
			
			final Object owner = getOwner(rankable);
			
			if(rank_map.containsKey(owner)){//原来就在排名中
				
				MapRankable existsOne = rank_map.get(owner);
				existsOne = rankable.merge(existsOne);
				rank_map.put(owner, existsOne);
				
				//与前一名比
				if(existsOne.getRank() > 1){
					MapRankable refer = this.getByRank(existsOne.getRank() - 1);//前一名
					if(refer == null || !this.compareBefore(refer, existsOne)){//修改后如果自己前面的不再在自己前面则需要重新排名
						this.dirty = true;
						rankChanged = true;
					}
				}
				
				if(!this.dirty){
					//与后一名比
					if(existsOne.getRank() < this.rankList.size()){
						MapRankable refer = this.getByRank(existsOne.getRank() + 1);//前一名
						if(refer == null || !this.compareAfter(refer, existsOne)){//修改后如果自己前面的不再在自己前面则需要重新排名
							this.dirty = true;
							rankChanged = true;
						}
					}
				}
				
			} else {
				if(this.rankList.size() >= this.maxSize){//排名列表已满则与最后一个比较看是否比他大如果比他大那么就将他挤出来并重新排名
					if(threshold != null){
						
						if(!dirty){//如果不需要重新排名就先与最后一名比较看是否直接替换最后一名就可以了
							
							if(this.compareBefore(rankable, threshold)){//endOne应该排在rankable后面了
								this.dirty = true;
								rankChanged = true;
							} else {//没有超越门槛直接返回
								rankChanged = false;
//								return false;
							}
							
						}
						
						if(dirty){//需要重新排名一下刷新threshold
							this.rankList.add(getOwner(rankable));//先加入进去排序
							rank_map.put(getOwner(rankable), rankable);
							rankable.setRank(this.rankList.size());
							
							this.reorder();
							this.dirty = false;
							
							Object lastOneId = this.rankList.remove(this.rankList.size() - 1);//将最后一名移除
							remove = this.rank_map.remove(lastOneId);
							
							this.threshold = this.getLastOne();//将门槛设置为最后一名
						}
					
						
					} else {
						logger.error("排名对象大小达到最大上限但是门槛对象为空!");
						this.reorder();
						this.dirty = false;
						this.threshold = this.getLastOne();
					}
					
				} else {//排名还没有满则直接放入列表
					
					add = rankable;
					this.rankList.add(getOwner(rankable));
					rankable.setRank(this.rankList.size());
					rank_map.put(getOwner(rankable), rankable);
					
					if(threshold != null){//与最后一名比较
						if(this.compareBefore(rankable, threshold)){//新加入的在组后的前面那么就设置为脏数据
							dirty = true;
						} 
					}
					
					if(dirty){
						this.reorder();
						this.dirty = false;
						this.threshold = this.getLastOne();
					} else {
						this.threshold = rankable;
					}
					
					if(this.rankList.size() == 1){
						this.threshold = rankable;
					}
					
					rankChanged = true;
				}
			}
			
			if(remove != null){
				return RefreshResult.replace(add, remove);
			} else if(add != null){
				return RefreshResult.add(add);
			} else {
				return RefreshResult.update(rankable, rankChanged);
			}
			
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 获取排行对象
	 * @param ownerId
	 * @return
	 */
	public MapRankable getRankable(Object ownerId){
		return this.rank_map.get(ownerId);
	}
	
	/**
	 * 获取指定排名的排名对象
	 * @param rank
	 * @return
	 */
	private MapRankable getByRank(int rank){
		if(rank > 0 && rank <= this.rankList.size()){
			Object ownerId = this.rankList.get(rank - 1);
			return this.rank_map.get(ownerId);
		}
		return null;
	}
	
	/**
	 * 获取最后一名
	 * @return
	 */
	private MapRankable getLastOne(){
		return this.getByRank(this.rankList.size());
	}
	
	/**
	 * rankable1应该在rankable2前面吗
	 * @param rankable1
	 * @param rankable2
	 * @return true - rankable1应该在rankable2前面
	 */
	private boolean compareBefore(MapRankable rankable1, MapRankable rankable2){
		return comparator.compare(rankable1, rankable2) < 0;
	}
	
	/**
	 * rankable1应该在rankable2后面吗
	 * @param rankable1
	 * @param rankable2
	 * @return true - rankable1应该在rankable2后面
	 */
	private boolean compareAfter(MapRankable rankable1, MapRankable rankable2){
		return comparator.compare(rankable1, rankable2) > 0;
	}
	
	/**
	 * 删除并刷新
	 * @param ownerId
	 */
	public RefreshResult removeAndRefresh(Object ownerId){
		lock.writeLock().lock();
		try {
			MapRankable existsRankable = rank_map.remove(ownerId);
			if(existsRankable != null){
				this.dirty = existsRankable.getRank() < this.rankList.size();
				this.rankList.remove(ownerId);
			}
			if(this.threshold != null && ownerId.equals(getOwner(threshold))){//如果移除的是门槛则刷新
				this.reorder();
				this.dirty = false;
				this.threshold = this.getLastOne();
			}
			return RefreshResult.remove(existsRankable);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	
	
	/**
	 * 查询指定id对应的排名(用于获取一个名次的快照)
	 * @param ownerId
	 * @return
	 */
	public int getOwnerRank(Object ownerId){
		if(!this.rank_map.containsKey(ownerId)){
			return 0;
		}
		boolean refreshAndGet = false;
		lock.readLock().lock();
		try {
			if(!this.dirty){
				MapRankable rankable = this.rank_map.get(ownerId);
				if(rankable != null){
					return rankable.getRank();
				}
				return 0;
			} else {
				refreshAndGet = true;
			}
		} finally {
			lock.readLock().unlock();
		}
		
		if(refreshAndGet){
			lock.writeLock().lock();
			try {
				if(dirty){
					this.reorder();
					this.dirty = false;
					this.threshold = this.getLastOne();
				}
				
				MapRankable rankable = this.rank_map.get(ownerId);
				if(rankable != null){
					return rankable.getRank();
				}
				
			} finally {
				lock.writeLock().unlock();
			}
		}
		
		return 0;
	}
	
	
	/**
	 * 分页获取排名(主要是面向客户端的)
	 * @param <VO>
	 * @param ownerId 如果不null则获取ownerId的排名
	 * @param startIndex 从列表的这个下标开始获取(>=0)
	 * @param fetchCount 从startIndex处开始获取这么多个元素(<0表示一直获取到最后一个元素)
	 * @param voFilter 过滤器
	 * @param converter 转换器
	 * @return
	 */
	public <S extends MapRankable, VO> RankResult<VO> pageRankListForView(Object ownerId, int startIndex, int fetchCount, Filter<VO> voFilter, Converter<S, VO> converter){
		
		if(startIndex < 0){
			startIndex = 0;
		}
		if(fetchCount <= 0){
			fetchCount = this.maxSize;
		}
		
		int totalSize = 0;
		
		List<VO> voList = new ArrayList<VO>();
		
		//owner的排行对象
		MapRankable ownerRankale = null;
		
		boolean refreshAndGet = false;
		lock.readLock().lock();
		try {
			if(!this.dirty){
				
				int ignoreCount = 0;
				
				for(int index = startIndex; index < Math.min(this.rankList.size(), startIndex + fetchCount + ignoreCount); index++){
					@SuppressWarnings("unchecked")
					S rankable = (S)this.rank_map.get(this.rankList.get(index));
					VO vo = converter.convert(rankable);
					if(vo != null){
						if(voFilter == null || voFilter.accept(vo)){//过滤
							voList.add(vo);
						} else {
							ignoreCount ++;
						}
						
					}
				}
				if(ownerId != null){
					MapRankable rankable = rank_map.get(ownerId);
					if(rankable != null){
						ownerRankale = rankable.clone();
					}
				}
				totalSize = this.rankList.size();
				
			} else {
				refreshAndGet = true;
			}
		} finally {
			lock.readLock().unlock();
		}
		
		if(refreshAndGet){
			lock.writeLock().lock();
			try {
				
				if(dirty){
					this.reorder();
					this.dirty = false;
					this.threshold = this.getLastOne();
				}
				
				int ignoreCount = 0;
				
				for(int index = startIndex; index < Math.min(this.rankList.size(), startIndex + fetchCount + ignoreCount); index++){
					@SuppressWarnings("unchecked")
					S rankable = (S)this.rank_map.get(this.rankList.get(index));
					VO vo = converter.convert(rankable);
					if(voFilter == null || voFilter.accept(vo)){//过滤
						voList.add(vo);
					} else {
						ignoreCount ++;
					}
				}
				if(ownerId != null){
					MapRankable rankable = rank_map.get(ownerId);
					if(rankable != null){
						ownerRankale = rankable.clone();
					}
				}
				totalSize = this.rankList.size();
			} finally {
				lock.writeLock().unlock();
			}
		}
		
		RankResult<VO> result = new RankResult<VO>();
		Page<VO> page = Page.valueOf(totalSize, startIndex, fetchCount, voList);
		result.setPage(page);
		result.setOwner(ownerRankale);
		return result;
	}
	
	
	
	
	/**
	 * 分页获取排名(主要是面向客户端的)
	 * @param <VO>
	 * @param ownerId 如果不null则获取ownerId的排名
	 * @param startIndex 从列表的这个下标开始获取(>=0)
	 * @param fetchCount 从startIndex处开始获取这么多个元素(<0表示一直获取到最后一个元素)
	 * @param converter 转换器
	 * @return
	 */
	public <S extends MapRankable, VO> RankResult<VO> pageRankListForView(Object ownerId, int startIndex, int fetchCount, Converter<S, VO> converter){
		return this.pageRankListForView(ownerId, startIndex, fetchCount, null, converter);
	}
	
	/**
	 * 获取排名列表的排名对象列表(对象是在内部遍历时的一份拷贝)
	 * @param startIndex
	 * @param fetchCount
	 * @return
	 */
	public List<MapRankable> getSnapshootRankList(int startIndex, int fetchCount){
		
		List<MapRankable> resultList = new ArrayList<MapRankable>();

		if(startIndex < 0){
			startIndex = 0;
		}
		if(fetchCount <= 0){
			fetchCount = this.maxSize;
		}
		
		boolean refreshAndGet = false;
		lock.readLock().lock();
		try {
			if(!this.dirty){
				int toIndex =  Math.min(this.rankList.size(), startIndex + fetchCount);
				for(int i = startIndex; i < toIndex; i ++){
					MapRankable rankable = this.rank_map.get(this.rankList.get(i));
					if(rankable != null){
						resultList.add((MapRankable)rankable.clone());
					}
				}
			} else {
				refreshAndGet = true;
			}
		} finally {
			lock.readLock().unlock();
		}
		
		if(refreshAndGet){
			lock.writeLock().lock();
			try {
				if(dirty){
					this.reorder();
					this.dirty = false;
					this.threshold = this.getLastOne();
				}
				
				int toIndex =  Math.min(this.rankList.size(), startIndex + fetchCount);
				for(int i = startIndex; i < toIndex; i ++){
					MapRankable rankable = this.rank_map.get(this.rankList.get(i));
					if(rankable != null){
						resultList.add((MapRankable)rankable.clone());
					}
				}
			} finally {
				lock.writeLock().unlock();
			}
		}
		
		return resultList;
	
	}
	
	/**
	 * 获取排名列表的id列表
	 * @param startIndex
	 * @param fetchCount
	 * @return
	 */
	public List<Object> getRankList(int startIndex, int fetchCount){
		if(startIndex < 0){
			startIndex = 0;
		}
		if(fetchCount <= 0){
			fetchCount = this.maxSize;
		}
		
		boolean refreshAndGet = false;
		lock.readLock().lock();
		try {
			if(!this.dirty){
				int toIndex =  Math.min(this.rankList.size(), startIndex + fetchCount);
				return this.rankList.subList(startIndex, toIndex);
			} else {
				refreshAndGet = true;
			}
		} finally {
			lock.readLock().unlock();
		}
		
		if(refreshAndGet){
			lock.writeLock().lock();
			try {
				
				if(dirty){
					this.reorder();
					this.dirty = false;
					this.threshold = this.getLastOne();
				}
				
				int toIndex =  Math.min(this.rankList.size(), startIndex + fetchCount);
				return this.rankList.subList(startIndex, toIndex);
			} finally {
				lock.writeLock().unlock();
			}
		}
		
		return null;
	}
	
	/**
	 * 重新排名列表
	 */
	private void reorder(){
		Collections.sort(this.rankList, new Comparator<Object>(){
			
			@Override
			public int compare(Object id1, Object id2) {
				MapRankable rankable1 = rank_map.get(id1);
				MapRankable rankable2 = rank_map.get(id2);
				return comparator.compare(rankable1, rankable2);
			}
		});
		int rank = 0;
		for(Object id : this.rankList){
			MapRankable rankable = rank_map.get(id);
			rankable.setRank(++rank);
		}
	}
	
	/**
	 * 清空排名
	 */
	public void clear(){
		lock.writeLock().lock();
		try {
			this.rank_map.clear();
			this.rankList.clear();
			this.dirty = false;
			this.threshold = null;
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 重置排行
	 */
	public void replace(List<? extends MapRankable> orderList){
		lock.writeLock().lock();
		try {
			
			//先清掉
			this.rank_map.clear();
			this.rankList.clear();
			this.dirty = false;
			this.threshold = null;
			
			//重新放入新的
			this.initialize(orderList);
			
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private static final String SIZE = "size";
	private static final String ORDER_BY = "orderBy";
	private static final String OWNER = "owner";
	private static final String RANK_LIST = "rankList";
	
	/**
	 * 将排序对象描述为json形式的字符串(相当于序列化)
	 * @return
	 */
	public String describe(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SIZE, this.maxSize);
		map.put(ORDER_BY, this.comparator.toString());
		map.put(OWNER, this.ownerKey);
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(MapRankable mapRankable : this.getSnapshootRankList(0, -1)){
			mapList.add(mapRankable.toMap());
		}
		
		map.put(RANK_LIST, mapList);
		try {
			return JsonUtil.toJSON(map);
		} catch (Exception e) {
			logger.error("排序对象json序列化异常!", e);
			throw new RuntimeException("排序对象json序列化异常!", e);
		}
	}
	
}
