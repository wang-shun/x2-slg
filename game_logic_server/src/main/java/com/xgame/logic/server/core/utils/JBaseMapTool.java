package com.xgame.logic.server.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.timertask.entity.job.data.CollectTimerTaskData;

public class JBaseMapTool {
	
	public static void main(String[] args) {
		CollectTimerTaskData collectTimerTaskData = new CollectTimerTaskData();
		JBaseData jBaseData = new JBaseData ();
		System.out.println("public JBaseData toJBaseData() {");
		System.out.println("JBaseData jbaseData = new JBaseData();");
		printlnToJBaseData(collectTimerTaskData);
		System.out.println("return jbaseData;");
		System.out.println("}");
		
		System.out.println("public void fromJBaseData(JBaseData jBaseData) {");
		printlnFromJBaseData(collectTimerTaskData,  jBaseData);
		System.out.println("}");
	}
	
	private static void printlnToJBaseData(Object obj) {
		for(Field field : obj.getClass().getDeclaredFields()) {
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			
			innerToJBaseData(obj, field);
		}
	}

	private static void innerToJBaseData(Object obj, Field field) {
		if(field.getType().getName().equals(Integer.class.getName()) || field.getType().equals(int.class)) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		} else if(field.getType().getName().equals(Double.class.getName()) || field.getType().equals(double.class)) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		} else if(field.getType().getName().equals(String.class.getName())) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		} else if(field.getType().getName().equals(Float.class.getName()) || field.getType().equals(float.class)) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		} else if(field.getType().getName().equals(Boolean.class.getName()) || field.getType().equals(boolean.class)) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		}else if(field.getType().getName().equals(Long.class.getName()) || field.getType().equals(long.class)) {
			System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getName()+");");
		}  else if(JBaseTransform.class.isAssignableFrom(field.getType()) ) {
			System.out.println("jbaseData.put(\""+field.getName()+"\",((JBaseTransform)"+field.getName()+").toJBaseData());");
		} else if(List.class.isAssignableFrom(field.getType())) {
			System.out.println("List<JBaseData> "+field.getName()+"List = new ArrayList<JBaseData>();");
			System.out.println("for(int i=0;i<"+field.getName()+".size();i++) {");
			System.out.println("Object obj = "+field.getName()+".get(i);");
			List<Object> objList;
			try {
				field.setAccessible(true);
				objList = (List<Object>)field.get(obj);
				Type t =field.getGenericType();
				Type[] types = ((ParameterizedType)t).getActualTypeArguments();
				for (int i = 0; i < objList.size(); i++) {
					Object innerObj = objList.get(i);
					if(types[0].getClass().equals(Integer.class) || types[0].getClass().equals(int.class)) {
						System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getType().getName()+");");
					} else if(types[0].getClass().equals(Double.class) || types[0].getClass().equals(double.class)) {
						System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getType().getName()+");");
					} else if(types[0].getClass().equals(String.class)) {
						System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getType().getName()+");");
					} else if(types[0].getClass().equals(Float.class) || types[0].getClass().equals(float.class)) {
						System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getType().getName()+");");
					} else if(types[0].getClass().equals(Long.class) || types[0].getClass().equals(long.class)) {
						System.out.println("jbaseData.put(\""+field.getName()+"\","+field.getType().getName()+");");
					} else {
						System.out.println(field.getName()+"List.add(((JBaseTransform)entry.getValue()).toJBaseData());");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("}");
		} else if(Map.class.isAssignableFrom(field.getType())) {
			System.out.println("List<JBaseData> "+field.getName()+"JBaseList = new ArrayList<JBaseData>();");
			System.out.println("for(Map.Entry entry:"+field.getName()+".entrySet()) {");
			Map mapObj = new HashMap();
			try {
				field.setAccessible(true);
				Type t =field.getGenericType();
				Type[] types = ((ParameterizedType)t).getActualTypeArguments();
				if(types[1].getClass().equals(Integer.class) || types[1].getClass().equals(int.class)) {
					System.out.println("JBaseData entryJBaseData = new JBaseData();");
					System.out.println("entryJBaseData.put(entry.getKey(), entry.getValue()");
				} else if(types[1].getClass().equals(Double.class) || types[1].getClass().equals(double.class)) {
					System.out.println("JBaseData entryJBaseData = new JBaseData();");
					System.out.println("entryJBaseData.put(entry.getKey(), entry.getValue()");
				} else if(types[1].getClass().equals(String.class)) {
					System.out.println("JBaseData entryJBaseData = new JBaseData();");
					System.out.println("entryJBaseData.put(entry.getKey(), entry.getValue()");
				} else if(types[1].getClass().equals(Float.class) || types[1].getClass().equals(float.class)) {
					System.out.println("JBaseData entryJBaseData = new JBaseData();");
					System.out.println("entryJBaseData.put(entry.getKey(), entry.getValue()");
				} else if(types[1].getClass().equals(Long.class) || types[1].getClass().equals(long.class)) {
					System.out.println("JBaseData entryJBaseData = new JBaseData();");
					System.out.println("entryJBaseData.put(entry.getKey(), entry.getValue()");
				} else {
					System.out.println(field.getName()+"JBaseList.add(((JBaseTransform)entry.getValue()).toJBaseData());");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			System.out.println("}");
		}
	}
	
	
	private static void printlnFromJBaseData(Object obj, JBaseData jBaseData) {
		for(Field field : obj.getClass().getDeclaredFields()) {
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			
			if(field.getType().getName().equals(Integer.class.getName()) || field.getType().equals(int.class)) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getInt(\"" + field.getName() + "\",0);");
			} else if(field.getType().getName().equals(Double.class.getName()) || field.getType().equals(double.class)) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getDouble(\"" + field.getName() + "\",0);");
			} else if(field.getType().getName().equals(String.class.getName())) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getString(\"" + field.getName() + "\");");
			} else if(field.getType().getName().equals(Float.class.getName()) || field.getType().equals(float.class)) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getFloat(\"" + field.getName() + "\",0);");
			} else if(field.getType().getName().equals(Boolean.class.getName()) || field.getType().equals(boolean.class)) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getBoolean(\"" + field.getName() + "\", false);");
			}else if(field.getType().getName().equals(Long.class.getName()) || field.getType().equals(long.class)) {
				System.out.println("this." + field.getName() + " = " + "jBaseData.getLong(\"" + field.getName() + "\",0);");
			}  else if(JBaseTransform.class.isAssignableFrom(field.getType()) ) {
				System.out.println("Class<?> clazz = field.getType();");
				System.out.println("JBaseTransform jBaseTransform = (JBaseTransform)clazz.newInstance()");
				System.out.println("JBaseData "+field.getName()+"JBaseData = jBaseData.getBaseData(\""+field.getName()+"\");");
				System.out.println("this." + field.getName() + " = " + "jBaseTransform.fromJBaseData("+field.getName()+"JBaseData)");
			} else if(List.class.isAssignableFrom(field.getType())) {
//				System.out.println("JBaseData entryJBaseData = new JBaseData();");
//				System.out.println("List<JBaseData> jbaseDataList = jBaseData.getSeqBaseData("+field.getName()+")");
//				System.out.println("for(JBaseData innerJbaseData : jbaseDataList) {");
//				Type[] types = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
//				Type type = types[0];
//				try {
//					Object innerObject = type.getClass().newInstance();
//					
//				} catch (InstantiationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				System.out.println("}");
			}
		}
	}

}
