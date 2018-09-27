package com.xgame.logic.server.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 对象拷贝工具类
 * @author jacky.jiang
 *
 */
public abstract class BeanUtils {
	
	private static Logger log = LoggerFactory.getLogger(BeanUtils.class);
	
	private static  final BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
	
	/**
	 * 转换器
	 */
	private static  final ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
	
	
	private BeanUtils(){}
	
	
	/**
	 * bean属性拷贝
	 * @param source
	 * @param target
	 * @param ignoreFields 可以不传 如：copyProperties(Object source, Object target)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperties(Object source, Object target, String... ignoreFields){
		if(source == null || target == null){
			return;
		}
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreFields != null) ? Arrays.asList(ignoreFields) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreFields == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						
						Class sourceType = sourcePd.getPropertyType();
	                	PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(target.getClass(), targetPd.getName());
	                	Class targetType = pd.getPropertyType();
	                	
	                	if(sourceType.isEnum() && (Integer.class.equals(targetType) || int.class.equals(targetType))){//源对象属性是枚举
	                    	if(value == null){
	                    		value = 0;
	                    	} else {
	                    		value = Enum.valueOf(sourceType, String.valueOf(value)).ordinal();
	                    	}
	                    } else if(targetType.isEnum() && (Integer.class.equals(sourceType) || int.class.equals(sourceType))){//目标对象属性是枚举
	                    	if(value == null){
	                    		value = 0;
	                    	}
	                    	int intValue = (Integer)value;
	                    	Method method = targetType.getMethod("values");
	                    	Object[] enumValues = (Object[])method.invoke(targetType);
	                    	if(intValue >= 0 &&  intValue <  enumValues.length){
	                    		value = enumValues[intValue];
	                    	} else {
	                    		continue;
	                    	}
	                    	
	                    } 
						
	                	if(String.class.equals(sourceType) && Number.class.isAssignableFrom(targetType)){
	                		Constructor constructor = targetType.getConstructor(String.class);
							value = constructor.newInstance(String.valueOf(value));
	                	} else if(String.class.equals(targetType) && Number.class.isAssignableFrom(sourceType)){
	                		value = String.valueOf(value);
	                	}
	                	
	                	if((Boolean.class.equals(sourceType) || boolean.class.equals(sourceType)) 
	                		&& (Integer.class.equals(targetType) || int.class.equals(targetType)))
	                	{
	                		value = (Boolean)value ? 1 : 0;
	                	} else if((Boolean.class.equals(targetType) || boolean.class.equals(targetType)) 
		                		&& (Integer.class.equals(sourceType) || int.class.equals(sourceType)))
	                	{
	                		value = (Integer)value > 0 ? true : false;
	                	}
	                	
	                	
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						
						writeMethod.invoke(target, value);
					}
					catch (Throwable e) {
						log.error("BeanUtil 对象复制出错:", e);
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	
	
    /**
     * 克隆对象
     * @param bean
     * @return
     */
    public static Object cloneBean(Object bean){
    	try{
    		return  beanUtilsBean.cloneBean(bean);
    	} catch (Throwable e) {
    		log.error("BeanUtil 对象克隆出错:", e);
    		throw new RuntimeException(e);
		}
    }
	
    
    /**
     *  拷贝属性给对象(类型宽松)
     * @param bean
     * @param name 属性名
     * @param value 属性值
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperty(Object bean, String name, Object value){
    	try{
    		
    		PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(bean.getClass(), name);
    		if(pd != null && pd.getWriteMethod() != null){
    			Class propertyClazz = pd.getPropertyType();
    			if(propertyClazz.isEnum() && value instanceof Integer){//属性枚举型 目标值是整型
                	int intValue = (Integer)value;
                	Method method = propertyClazz.getMethod("values");
                	Object[] enumValues = (Object[])method.invoke(propertyClazz);
                	if(intValue >= 0 &&  intValue <  enumValues.length){
                		value = enumValues[intValue];
                	} else {//不合理的int值范围就不修改
                		return;
                	}
        		} else {
        			value = convertUtilsBean.convert(value);
        		}
        		beanUtilsBean.copyProperty(bean, name, value);
    		}
    		
    	} catch (Throwable e) {
    		log.error("BeanUtil 对象属性赋值出错:", e);
    		throw new RuntimeException(e);
		}
    	
    }
    
    /**
     * 将bean装换为一个map(不能将枚举转换为int)
     * @param bean
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
	public static Map describe(Object bean){
    	try{
    		return beanUtilsBean.describe(bean);
    	} catch (Throwable e) {
    		log.error("BeanUtil 对象克隆出错:", e);
    		throw new RuntimeException(e);
		}
    }
    
    
    /**
     * 将bean装换为一个map(能将枚举转换为int)
     * @param bean
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map buildMap(Object bean){
    	if(bean == null){
    		return null;
    	}
    	try{
    		Map map = describe(bean);
        	PropertyDescriptor[] pds = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(bean);
        	for(PropertyDescriptor pd : pds){
        		Class type = pd.getPropertyType();
        		if(type == null){
        			continue;
        		}
        		if(type.isEnum()){
        			Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, pd.getName());
        			int intValue = 0;
        			if(value != null){
        				intValue = Enum.valueOf(type, String.valueOf(value)).ordinal();
        			}
        			map.put(pd.getName(), intValue);
        			
        		} else if(type == java.util.Date.class){//防止是Timestamp
        			Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, pd.getName());
        			if(value != null){
        				Calendar cal = Calendar.getInstance();
        				cal.setTime((java.util.Date)value);
        				map.put(pd.getName(),  cal.getTime());
        			}
        		}
        	}
        	map.remove("class");//移掉class
        	return map;
    	} catch (Throwable e) {
    		log.error("BeanUtil 创建Map失败:", e);
    		throw new RuntimeException(e);
		}
    	
    	
    }
    
    /**
     * 将bean列表转换成map的列表
     * @param beanList
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static List<Map> buildMapList(List beanList){
    	if(beanList != null && !beanList.isEmpty()){
    		List<Map> mapList = new ArrayList<Map>();
    		for(Object bean : beanList){
    			mapList.add(buildMap(bean));
    		}
    		return mapList;
    	}
    	return null;
    }
    
    
    /**
     * 将map转Bean
     * @param map
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object buildBean(Map map, Class clazz){
    	if(map == null){
    		return null;
    	}
    	Object bean = null;
    	try{
    		bean = clazz.newInstance();
    		PropertyDescriptor[] pds = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(clazz);
    		for(PropertyDescriptor pd : pds){
    			String fieldName = pd.getName();
    			if(map.containsKey(fieldName)){
    				Object mapValue = map.get(fieldName);
    				Class beanType = pd.getPropertyType();
    				Object beanValue = mapValue;
    				
    				
        			if(beanType.isEnum()){
        				if(mapValue != null){
              				if(mapValue instanceof String){
            					if(String.valueOf(mapValue).matches("\\d+")){//数字型
            						mapValue = Integer.parseInt(String.valueOf(mapValue));
        	                    	int intValue = (Integer)mapValue;
        	                    	
        	                    	Method method = beanType.getMethod("values");
        	                    	Object[] enumValues = (Object[])method.invoke(beanType);
        	                    	if(intValue >= 0 &&  intValue <  enumValues.length){
        	                    		beanValue = enumValues[intValue];
        	                    	} else {
        	                    		continue;
        	                    	}
            					} else {//字符串标识的枚举值 
        	                    	try{
                						beanValue = Enum.valueOf(beanType, String.valueOf(mapValue));
                					} catch (IllegalArgumentException e) {//是一个错误的值
                						continue;
        							}
            					}
            					
            				} else if(mapValue instanceof Integer){//整型
            					int intValue = (Integer)mapValue;
            					Method method = beanType.getMethod("values");
    	                    	Object[] enumValues = (Object[])method.invoke(beanType);
    	                    	if(intValue >= 0 &&  intValue <  enumValues.length){
    	                    		beanValue = enumValues[intValue];
    	                    	} else {//超过了枚举的int值范围
    	                    		continue;
    	                    	}
            				}
        				}
        			} else if(beanType.equals(java.util.Date.class)){
        				if(mapValue != null){
        					if(mapValue instanceof String){
        						try{
        							DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        							beanValue = format.parse(String.valueOf(mapValue));
        						} catch (ParseException e) {
									log.error("BeanUtil buildBean string 转 Date 出错!");
									continue;
								}
        						
        					}
        				}
        			}
        			
        			beanUtilsBean.copyProperty(bean, fieldName, beanValue);
        				 
    			}
    			
    		}
    		return bean;
    	}catch (Throwable e) {
    		log.error("BeanUtil 根据map创建bean出错:", e);
    		throw new RuntimeException(e);
		}
    }
    
    /**
     *  拷贝属性给对象(类型严格)
     * @param bean
     * @param name 属性名
     * @param value 属性值
     */
    public static void setProperty(Object bean, String name, Object value){
    	try{
    		beanUtilsBean.setProperty(bean, name, value);
    	} catch (Throwable e) {
    		log.error("BeanUtil 给对象属性赋值出错:", e);
    		throw new RuntimeException(e);
		}
    	
    }
    
    /**
     * 获取对象属性值
     * @param bean
     * @param name
     * @return
     */
    public static Object getProperty(Object bean, String name){
    	try{
    		return beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, name);
    	} catch (Throwable e) {
    		log.error("BeanUtil 获取对象属性值出错:", e);
    		throw new RuntimeException(e);
		}
    	
    }
    
    /**
     * 将map里面的键值拷贝到bean中(map中没有的属性就用bean当前的值)
     * @param map
     * @param bean
     * @param ignoreFields 需要忽略拷贝的属性集合
     */
    public static void copyMapProperties(Map<String, Object> map, Object bean, String... ignoreFields){
    	if(map != null && !map.isEmpty()){
    		Set<String> ignores = new HashSet<String>();
    		if(ignoreFields != null){
    			for(String fieldName : ignoreFields){
    				ignores.add(fieldName);
    			}
    		}
    		for(Entry<String, Object> entry : map.entrySet()){
    			if(!ignores.contains(entry.getKey())){
    				copyProperty(bean, entry.getKey(), entry.getValue());
    			}
    		}
    	}
    }
    
    
}
