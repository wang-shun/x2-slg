package com.xgame.config.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:40 
 */
public class TaskPirFactory extends BasePriFactory<TaskPir>{
	

	public void init(TaskPir pir) {
		
	}
	
	@Override
	public void load(TaskPir pir) {
		
	}
	
	
	/**
	 * key:type
	 */
	private static final Map<Integer,List<TaskPir>> taskPirMap = new HashMap<>(); 
	
	private static final List<TaskPir> initTask = new ArrayList<>();
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	@Override
	public void loadOver(String programConfigPath, Map<Integer,TaskPir> data){
		for(TaskPir taskPir : data.values()){
			if(!taskPirMap.containsKey(taskPir.type)){
				taskPirMap.put(taskPir.type, new ArrayList<TaskPir>());
			}
			taskPirMap.get(taskPir.type).add(taskPir);
			if(taskPir.getRequireTask() == 0){
				initTask.add(taskPir);
			}
		}
	}
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,TaskPir pir){
		
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,TaskPir pir){
		
	}
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,TaskPir pir){
		
	}
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,TaskPir pir){
		
	}
	
	/**
	 *自定义解析  index
	 */
	@ConfParse("index")
	public void indexPares(String conf,TaskPir pir){
		
	}
	
	/**
	 *自定义解析  goto
	 */
	@ConfParse("goto")
	public void gotoPares(String conf,TaskPir pir){
		
	}
	
	@Override
	public TaskPir newPri() {
		return new TaskPir();
	}
	
	public static TaskPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TaskPirFactory instance = new TaskPirFactory(); 
	
	
	public static TaskPirFactory getInstance() {
		return instance;
	}
	
	public static List<TaskPir> getByType(int type){
		return taskPirMap.get(type);
	}
	
	public static List<TaskPir> getInitTask(){
		return initTask;
	}
	
	public static List<TaskPir> getByRequireTask(int taskId){
		List<TaskPir> list = new ArrayList<>();
		for(TaskPir taskPir : instance.getFactory().values()){
			if(taskPir.getRequireTask() == taskId){
				list.add(taskPir);
			}
		}
		
		return list;
	}
}