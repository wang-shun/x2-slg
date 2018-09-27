/**
 * 
 */
package com.xgame.logic.server.core.utils.scheduler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.support.MethodInvokingRunnable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.xgame.logic.server.core.utils.scheduler.impl.CronTrigger;
import com.xgame.logic.server.core.utils.scheduler.impl.SimpleScheduler;

/**
 * 定时器工厂bean
 * @author jacky.jiang
 *
 */
public class SchedulerFactoryBean implements BeanPostProcessor, BeanFactoryAware {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**活动间隔毫秒数(每次活动将重新检查到期元素)*/
	private long liveIntervals = 60000L;
	
	/**核心线程大小*/
	private int coreThreadSize = 5;
	
	/**自动定时接口(空标识不做自动定时)*/
	private String autoSchdulingInterface;
	
	public long getLiveIntervals() {
		return liveIntervals;
	}

	public void setLiveIntervals(long liveIntervals) {
		this.liveIntervals = liveIntervals;
	}

	public int getCoreThreadSize() {
		return coreThreadSize;
	}

	public void setCoreThreadSize(int coreThreadSize) {
		this.coreThreadSize = coreThreadSize;
	}
	
	public String getAutoSchdulingInterface() {
		return autoSchdulingInterface;
	}

	public void setAutoSchdulingInterface(String autoSchdulingInterface) {
		this.autoSchdulingInterface = autoSchdulingInterface;
	}

	/** 定时任务调度器 */
	private SimpleScheduler scheduler;
	
	
	public void init() {
		scheduler = (SimpleScheduler) this.beanFactory.getBean(Scheduler.class);
		scheduler.init(liveIntervals, coreThreadSize);
		
		for (Entry<ScheduledTask, String> entry : tasks.entrySet()) {
			try {
				scheduler.schedule(entry.getKey(), new CronTrigger(entry.getValue()));
			} catch (Exception e) {
				throw new RuntimeException(String.format("提交定时任务 [%s] 异常!", entry.getKey().getName()), e);
			}
		}
		
		if(autoSchdulingInterface != null && autoSchdulingInterface.length() > 0){
			try {
				setupAutoScheduling();
			} catch (ClassNotFoundException e) {
				logger.error("创建自动定时异常!", e);
				throw new RuntimeException("创建自动定时异常!", e);
			}
		}
	}
	
	// 定时任务注册部分
	private final Map<ScheduledTask, String> tasks = new HashMap<ScheduledTask, String>();

	/** 获取定时任务信息 */
	@Override
	public Object postProcessAfterInitialization(final Object bean, String beanName) {
		ReflectionUtils.doWithMethods(bean.getClass(), new MethodCallback() {
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				Scheduled annotation = AnnotationUtils.getAnnotation(method, Scheduled.class);
				if (annotation == null) {
					return;
				}
				ScheduledTask task = createTask(bean, method, annotation);
				String experssion = resolveExperssion(bean, annotation);
				tasks.put(task, experssion);
			}
		});
		return bean;
	}

	/** 创建定时任务 */
	private ScheduledTask createTask(Object bean, Method method, Scheduled annotation) {
		if (!void.class.equals(method.getReturnType())) {
			throw new IllegalArgumentException("定时方法的返回值必须为 void");
		}
		if (method.getParameterTypes().length != 0) {
			throw new IllegalArgumentException("定时方法不能有参数");
		}

		final MethodInvokingRunnable runnable = new MethodInvokingRunnable();
		runnable.setTargetObject(bean);
		runnable.setTargetMethod(method.getName());
		runnable.setArguments(new Object[0]);
		try {
			runnable.prepare();
		} catch (Exception e) {
			throw new IllegalStateException("无法创建定时任务", e);
		}

		final String name = annotation.name();
		return new ScheduledTask() {
			@Override
			public void run() {
				runnable.run();
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}

	/** 获取Cron表达式 */
	private String resolveExperssion(Object bean, Scheduled annotation) {
		String result = null;
		switch (annotation.type()) {
		case EXPRESSION:
			result = annotation.value();
			break;
		case BEANNAME:
			String name = annotation.value();
			try {
				Object obj = beanFactory.getBean(name);
				if (obj instanceof String) {
					result = (String) obj;
				} else {
					result = annotation.defaultValue();
				}
			} catch (NoSuchBeanDefinitionException e) {
				result = annotation.defaultValue();
			}
			break;
		default:
			break;
		}
		return result;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	private BeanFactory beanFactory;
	

	/**类名_方法名 <--> 方法对象*/
	private final ConcurrentHashMap<String, Method> methods = new ConcurrentHashMap<>();
	
	/**
	 * 自动定时处理
	 * @throws ClassNotFoundException
	 */
	private void setupAutoScheduling() throws ClassNotFoundException{
		
		final Class<?> clazz = Class.forName(autoSchdulingInterface);
		
		//对接口方法进行扫描
		ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
			
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				final AutoScheduled scheduled = method.getAnnotation(AutoScheduled.class);
				if(scheduled != null){
					scheduler.schedule(new ScheduledTask() {
						
						@Override
						public void run() {
							try {
								//调用代理对象方法
								Object targetBean = beanFactory.getBean(scheduled.target());
								targetBean = AopUtils.getTarget(targetBean);
								
								String key = String.format("%s_%s", scheduled.target().getName(), scheduled.method());
								Method method = methods.get(key);
								if(method == null){
									method = scheduled.target().getDeclaredMethod(scheduled.method());
									method.setAccessible(true);
									methods.putIfAbsent(key, method);
								}
								
								method.invoke(targetBean);
								
							} catch (Exception e) {
								logger.error(String.format("定时任务 [%s] 执行异常!", scheduled.name()), e);
							}
						}
						
						@Override
						public String getName() {
							return scheduled.name();
						}
						
					}, scheduled.cron());
					
				} else {
					logger.warn("定时接口类 [{}] [{}] 方法没有被注解...", clazz.getName(), method.getName());
				}
			}
		});
	}

	
	/**
	 * 停止定时器
	 */
	@PreDestroy
	public void stop(){
		if(this.scheduler != null) {
			this.scheduler.stop();
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
