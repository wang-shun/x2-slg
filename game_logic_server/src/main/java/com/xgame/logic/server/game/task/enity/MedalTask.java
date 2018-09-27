package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 勋章任务
 * @author zehong.he
 *
 */
public class MedalTask implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762779452094822638L;

	@Tag(1)
	@Setter
	@Getter
	private int taskId;	//任务ID
	
	@Tag(2)
	@Setter
	@Getter
	private List<Integer> currentValue = new ArrayList<Integer>();//完成的进度（完成的taskId）
	
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("taskId", taskId);
		jbaseData.put("currentValue", currentValue);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.taskId = jBaseData.getInt("taskId", 0);
		this.currentValue = jBaseData.getSeqInt("currentValue");
	}
}
