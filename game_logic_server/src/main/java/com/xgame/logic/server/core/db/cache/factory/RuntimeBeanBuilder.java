/**
 * 
 */
package com.xgame.logic.server.core.db.cache.factory;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils.FieldCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * 运行时bean构建器
 * @author jacky
 *
 */
@Slf4j
public class RuntimeBeanBuilder {
	
	private static final AtomicInteger idGenerator = new AtomicInteger(0);
	
	private Object bean;
	
	private Class<?> beanClass;
	
	private String beanName;
	
	//依赖的bean名
	private final Set<String> dependentBeanNames = new LinkedHashSet<String>();
	
	//依赖的bean
	private final Set<Object> dependentBeans = new LinkedHashSet<Object>();
	
	
	public RuntimeBeanBuilder(Object bean){
		this.bean = bean;
		this.beanClass = bean.getClass();
		this.beanName = SpringHelper.generateBeanName(beanClass) + "_" + idGenerator.incrementAndGet();
	}
	
	public RuntimeBeanBuilder(String beanName, Object bean){
		this.bean = bean;
		this.beanClass = bean.getClass();
		this.beanName = beanName;
	}
	
	public RuntimeBeanBuilder(Class<?> beanClass){
		this.beanClass = beanClass;
		this.beanName = SpringHelper.generateBeanName(beanClass) + "_" + idGenerator.incrementAndGet();
	}
	
	/**
	 * 添加一个依赖的bean名
	 * @param dependentBeanName
	 * @return
	 */
	public RuntimeBeanBuilder addDependentBeanByName(String dependentBeanName){
		dependentBeanNames.add(dependentBeanName);
		return this;
	}
	
	/**
	 * 添加一个依赖的bean
	 * @param dependentBean
	 * @return
	 */
	public RuntimeBeanBuilder addDependentBean(final Object dependentBean){
		dependentBeans.add(dependentBean);
		return this;
	}
	
	/**
	 * 注册到指定的容器
	 * @param applicationContext
	 */
	public Object register(ApplicationContext applicationContext) {
		ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
		if(beanFactory.containsBean(beanName)){
			String message = String.format("动态向容器注册 已经存在的 bean ：[%s]", beanName);
			log.error(message);
			throw new RuntimeException(message);
		}
		
		if(this.bean == null){
			this.bean = beanFactory.createBean(beanClass);
		}
		
		if(bean instanceof BeanPostProcessor){
			beanFactory.addBeanPostProcessor((BeanPostProcessor)bean);
		}
		
		for(String dependentBeanName : dependentBeanNames){
			beanFactory.registerDependentBean(this.beanName, dependentBeanName);
		}
		
		//设置依赖的bean
		for(Object dependentBean : dependentBeans) {
			final Object beanRef = dependentBean;
			org.springframework.util.ReflectionUtils.doWithFields(beanClass, new FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException,
						IllegalAccessException {
					field.setAccessible(true);
					if(TypeUtils.isInstance(beanRef, field.getType())){
						field.set(bean, beanRef);
					}
				}
			});
		}
		
		beanFactory.registerSingleton(beanName, this.bean);
		Object contextBean = applicationContext.getBean(beanName);
		beanFactory.autowireBean(contextBean);
		return contextBean;
	}

}
