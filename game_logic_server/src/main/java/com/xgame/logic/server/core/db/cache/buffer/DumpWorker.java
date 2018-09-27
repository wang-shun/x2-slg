/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * dump工作者线程
 * @author jacky
 *
 */
public class DumpWorker extends Thread {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, Buffer> buffers = new ConcurrentHashMap<Class, Buffer>();
	
	/**dump的时间间隔(毫秒)*/
	private final long dumpIntervals;
	
	/**历史最大dump数量*/
	private int maxDumps;
	
	@SuppressWarnings("rawtypes")
	public DumpWorker(long dumpIntervals, Map<Class, Buffer> buffers){
		this.dumpIntervals = dumpIntervals;
		if(buffers != null){
			this.buffers.putAll(buffers);
		}
		this.setName("dbcache数据倾倒线程");
		this.setDaemon(true);
	}
	
	//是否被中断了
	private boolean interupted = false;
	
	@Override
	public void interrupt() {
		this.interupted = true;
		super.interrupt();
		logger.error("dbcache数据倾倒线程被中断");
	}


	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		
		while(!interupted){
			
			try {
				
				synchronized (this) {
					try {
						this.wait(dumpIntervals);
					} catch (InterruptedException e) {
						if(!interupted){
							logger.error("dbcache数据倾倒线程被异常打断...", e);
						}
					} catch (Exception e) {
						logger.error("dbcache数据倾倒线程异常...", e);
					}
				}
				
				int totalDumpCount = 0;
				
				for(Entry<Class, Buffer> entry : buffers.entrySet()) {
					Class clazz = entry.getKey();
					Buffer buffer = entry.getValue();
					try {
						int dumpCount = buffer.dump();
						totalDumpCount += dumpCount;
					} catch (Exception e) {
						logger.error(String.format("dbcache定时倾倒数据时异常:%s", clazz.getName()), e);
					}
				}
				
				//超过最大就log一次
				if(totalDumpCount > maxDumps){
					maxDumps = totalDumpCount;
					logger.info(String.format("dbcache dump 对象 [%s] 个", maxDumps));
				}
				
			} catch (Exception e) {
				logger.error("dbcache定时倾倒数据时异常!", e);
			}
			
		}
		
		logger.info("dbcache数据倾倒线程退出...");
	}
}
