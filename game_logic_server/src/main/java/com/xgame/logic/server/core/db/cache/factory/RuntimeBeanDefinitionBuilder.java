/**
 * 
 */
package com.xgame.logic.server.core.db.cache.factory;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * 运行时BeanDefinition构建者
 * @author jacky
 *
 */
class RuntimeBeanDefinitionBuilder {
	
	private BeanDefinitionBuilder beaDefinitionBuilder;
	
	private Class<?> beanClass;

	/**
	 * @param beanClass  如果是一个工程bean 在注册的实际是一个 工厂生成的对象
	 */
	public RuntimeBeanDefinitionBuilder(Class<?> beanClass) {
		this.beaDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
		//设置成单例模式
		this.beaDefinitionBuilder.getRawBeanDefinition().setScope(BeanDefinition.SCOPE_SINGLETON);
		this.beanClass = beanClass;
	}
	
	public RuntimeBeanDefinitionBuilder addConstructorArgReference(String beanName){
		this.beaDefinitionBuilder.addConstructorArgReference(beanName);
		return this;
	}
	
	public RuntimeBeanDefinitionBuilder addConstructorArgValue(Object value){
		this.beaDefinitionBuilder.addConstructorArgValue(value);
		return this;
	}
	
	public RuntimeBeanDefinitionBuilder addPropertyValue(String name, Object value){
		this.beaDefinitionBuilder.addPropertyValue(name, value);
		return this;
	}
	
	public RuntimeBeanDefinitionBuilder setFactoryBean(String beanName, String factoryMethodName){
		this.beaDefinitionBuilder.getRawBeanDefinition().setFactoryBeanName(beanName);
		this.beaDefinitionBuilder.getRawBeanDefinition().setFactoryMethodName(factoryMethodName);
		return this;
	}
	
	/**
	 * 注册到容器中
	 * @param applicationContext
	 */
	public Object register(ApplicationContext applicationContext){
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
		
		Class<?> beanClassToUse = this.beanClass;
		
		String beanName = SpringHelper.generateBeanName(this.beanClass);//如果是工程类就是工程类的beanName
		beanFactory.registerBeanDefinition(beanName, this.beaDefinitionBuilder.getBeanDefinition());
		
		if(TypeUtils.isAssignable(this.beanClass, FactoryBean.class)){//如果注册的工程类
			FactoryBean<?> factoryBean = (FactoryBean<?>)applicationContext.getBean(beanClass);
			beanFactory.autowireBean(factoryBean);
			beanClassToUse = factoryBean.getObjectType();
		} 
		
		Object beanObject = applicationContext.getBean(beanName, beanClassToUse);
		beanFactory.autowireBean(beanObject);
		
		return beanObject;
	}
	
	
	public static void main(String[] args) {
		String beanClassName = "Foo";
		char firstChar = beanClassName.charAt(0);
		StringBuilder builder = new StringBuilder().append(Character.toLowerCase(firstChar));
		if(beanClassName.length() > 1){
			builder.append(beanClassName.substring(1));
		}
		System.out.println(builder.toString());
		
	}
	

}
