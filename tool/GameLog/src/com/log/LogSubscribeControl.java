package com.log;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.log.template.BaseLog;
import com.log.util.MetaData;

/**
 *
 *2016-10-17  10:52:12
 *@author ye.yuan
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogSubscribeControl {
	
	@Autowired
	private GameLogSystem gameLogSystem;
	
	private Map<Class<?>, LogSubscribeTable> tables = new ConcurrentHashMap<>();
	
	private long uid;
	
	public void load(long uid){
		this.uid=uid;
		gameLogSystem.registerListener(this);
	}
	
	/**
	 * 订阅全部
	 * @param listener
	 */
	public void subscribe(Object listener) {
		Iterator<Entry<Class<? extends BaseLog>, List<MetaData>>> iterator = BaseLog.getMetadataMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Class<? extends BaseLog>, List<MetaData>> entry = iterator.next();
			LogSubscribeTable table = tables.get(entry.getKey());
			if(table==null){
				tables.put(entry.getKey(),table = new LogSubscribeTable());
			}
			table.getBus().register(listener);
		}
	}
	
	/**
	 * 订阅1个
	 * @param targetClass 目标日志类
	 * @param sourceId 来源
	 * @param actionId 行为
	 * @param listener 接收者
	 */
	public void subscribe(Class<?> targetClass,int sourceId,int actionId,Object listener) {
//		LogSubscribeTable table = tables.get(targetClass);
//		if(table==null){
//			tables.put(targetClass,table = new LogSubscribeTable());
//		}
//		LogSubscribe  logSubscribe = null;
//		if((logSubscribe = table.getSubscribes().get(sourceId, actionId))==null){
//			table.getSubscribes().put(sourceId, actionId,logSubscribe = new LogSubscribe());
//		}
//		logSubscribe.getBus().register(listener);
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Map<Class<?>, LogSubscribeTable> getTables() {
		return tables;
	}

	public void setTables(Map<Class<?>, LogSubscribeTable> tables) {
		this.tables = tables;
	}
	
}
