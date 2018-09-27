/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import com.xgame.logic.server.core.db.cache.executor.ExecutorConfig;
import com.xgame.logic.server.core.db.cache.executor.LimitedIdentifyQueueExecutor;


/**
 * buffer数据入库
 * @author jiangxt
 *
 */
public class DumpExecutor extends LimitedIdentifyQueueExecutor {
	
	public DumpExecutor(ExecutorConfig executorConfig) {
		super(executorConfig, 0);
	}

	
}
