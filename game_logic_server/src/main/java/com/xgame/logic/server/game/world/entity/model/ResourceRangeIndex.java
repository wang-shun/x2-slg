package com.xgame.logic.server.game.world.entity.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.ziYuanShengCheng.ZiYuanShengChengPir;
import com.xgame.config.ziYuanShengCheng.ZiYuanShengChengPirFactory;
import com.xgame.logic.server.game.world.constant.WorldConstant;


/**
 * 根据范围存储的坐标点列表
 * 存储游戏当中的坐标点信息, 根据策划在{@link ZiYuanShengChengPir}配置的信息, 将地图当中的坐标点转换成根据配置范围获取的坐标点列表
 * Map<range, List<Grid>> 
 * @author jacky.jiang
 *
 */
@Component
public class ResourceRangeIndex {
	
	/**
	 * 每个范围的内的坐标
	 */
	private Map<Integer, List<Integer>> rangeGrid = new HashMap<>();
	
	
	public void init() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(ZiYuanShengChengPirFactory.getInstance().getRangeList());
		Collections.sort(list);
		int centerY = WorldConstant.Y_GRIDNUM / 2;
		int centerX = WorldConstant.X_GRIDNUM / 2;
		int i = 0;
		if(list != null) {
			for(Integer range : list) {
				List<Integer> rangList = new ArrayList<>();
				if(i > 0) {
					int lastRange = list.get(i-1);
					for (int m = centerY - range; m < centerY + range; m++) {
						for(int n = centerX - range ; n < centerX + range; n++) {
							if(m < centerY + lastRange -1 && m > centerY - lastRange && n < centerX + lastRange - 1 && n > centerX - lastRange) {
								continue;
							}
							
							if(m >=512 || n >= 512) {
								continue;
							}
							
							rangList.add(m * WorldConstant.X_GRIDNUM + n);
						}
					}
					rangeGrid.put(range, rangList);
				} else {
					for (int m = centerY - range; m < centerY + range; m++) {
						for(int n = centerX - range; n < centerX + range; n++) {
							rangList.add(n * WorldConstant.X_GRIDNUM + m);
						}
					}
					rangeGrid.put(range, rangList);
				}
				i++;
			}
		}
	}
	
	/**
	 * 根据范围获取坐标点
	 * @param range
	 * @return
	 */
	public List<Integer> getListByRange(int range) {
		return rangeGrid.get(range);
	}
}
