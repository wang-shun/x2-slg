package com.xgame.framework.network.server;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ListenableFuture;
import com.xgame.framework.executer.MapExecutor;
import com.xgame.framework.executer.ParallelExecutor;
import com.xgame.framework.executer.SyncExecutor;
import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;

/**
 * 统一管理请求线程
 * @author jacky.jiang
 *
 */
@Component
public class CommandProcessor {

	@Resource
	private SyncExecutor logicExecutor;
	@Resource
	private ParallelExecutor parallelExecutor;
	@Resource
	private MapExecutor mapExecutor;
	
	public void runSync(Runnable command) {
		logicExecutor.execute(command);
	}

	public void runAsync(long id, Runnable command) {
		parallelExecutor.execute(id, command);
	}

	public <V> ListenableFuture<V> runRpcCall(Callable<V> call){
		throw new RuntimeException("not support");
	}
	
	public ExecutorService getExecutor(long id) {
		return parallelExecutor.getPlayerExecutor(id);
	}
	
	public ExecutorService getGameExecutorService(long id) {
		return parallelExecutor.getPlayerExecutor(id);
	}
	
	public ExecutorService getSyncExecutor() {
		return logicExecutor.getService();
	}
	
	public ExecutorService getMapExecutor() {
		return mapExecutor.getService();
	}
	
	public MapExecutor getMapExecutorSource() {
		return mapExecutor;
	}
	
	@Shutdown(order=ShutdownOrder.COMMAND_PROCESSOR, desc = "游戏线程管理器关闭")
	public void stop() {
		logicExecutor.shutdown();
		parallelExecutor.shutdown();
		mapExecutor.shutdown();
	}
}