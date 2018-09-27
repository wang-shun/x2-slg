package com.xgame.logic.server.game.task.constant;

import java.util.Comparator;

import com.xgame.logic.server.game.task.enity.BaseTask;

public class Sequence {

	
	public static final Comparator<BaseTask> BASETASK_SORT = new Comparator<BaseTask>(){

		@Override
		public int compare(BaseTask arg0, BaseTask arg1) {
			return arg0.getOrder() - arg1.getOrder();
		}
	};
}
