package com.xgame.logic.server.core.system;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;

import lombok.extern.slf4j.Slf4j;

/**
 * 生命周期管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class LifeCycleManager implements BeanPostProcessor {
	
	/**
	 * 启动
	 */
	private Map<StartupOrder, Invoker> startUpInvokers = new HashMap<StartupOrder, Invoker>();
	
	/**
	 * 关闭
	 */
	private Map<ShutdownOrder, Invoker> shutdownInvokers = new HashMap<ShutdownOrder, Invoker>();
	
	/**
	 * 启动项
	 */
	public void start() {
		for(StartupOrder startupOrder : StartupOrder.values()) {
			try {
				Invoker invoker = startUpInvokers.get(startupOrder);
				invoker.invoke();
				log.error(String.format("[%s], 启动插件成功。", startupOrder));
			} catch(Exception e) {
				String message = String.format("%s, 启动插件失败:", startupOrder);
				log.error(message, e);
				throw new RuntimeException(message);
			}
		}
	}

	/**
	 * 停服项
	 */
	public void stop() {
		for(ShutdownOrder shutdownOrder : ShutdownOrder.values()) {
			Invoker invoker = shutdownInvokers.get(shutdownOrder);
			invoker.invoke();
		}
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		Method[] methods = bean.getClass().getDeclaredMethods();
		if(methods != null) {
			for(Method method : methods) {
				if(method.isAnnotationPresent(Startup.class)) {
					Startup startup = method.getAnnotation(Startup.class);
					Invoker invoker = Invoker.valueOf(bean, method);
					startUpInvokers.put(startup.order(), invoker);
				}
				
				if(method.isAnnotationPresent(Shutdown.class)) {
					Shutdown shutdown = method.getAnnotation(Shutdown.class);
					Invoker invoker = Invoker.valueOf(bean, method);
					this.shutdownInvokers.put(shutdown.order(), invoker);
				}
			}
		}
		return bean;
	}
}

/**
 * 
 * @author jacky.jiang
 *
 */
@Slf4j
class Invoker {
	/**
	 * 反射类名
	 */
	public Object obj;
	
	/**
	 * 反射方法名
	 */
	public Method method;
	
	public static Invoker valueOf(Object obj, Method method) {
		Invoker invoker = new Invoker();
		invoker.obj = obj;
		invoker.method = method;
		return invoker;
	}
	
	/**
	 * 反射信息
	 */
	public void invoke() {
		try {
			ReflectionUtils.invokeMethod(method, obj);
		} catch (Exception e) {
			log.error("反射方法异常:", e);
		}
	}
	
}
