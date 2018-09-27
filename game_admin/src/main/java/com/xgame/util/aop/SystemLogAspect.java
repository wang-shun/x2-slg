package com.xgame.util.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.xgame.gm.entity.User;
import com.xgame.gm.entity.UserLog;
import com.xgame.gm.service.IUserLogService;

@Aspect
@Component
public class SystemLogAspect {
	
	@Resource
	private IUserLogService userLogService;
	
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
	
	//service层切点
	@Pointcut("@annotation(com.xgame.util.aop.SystemServiceLog)")
	public void serviceAspect(){
		
	}
	
	//controller层切点
	@Pointcut("@annotation(com.xgame.util.aop.SystemControllerLog)")
	public void controllerAspect(){
		
	}
	
	/**
	 * 拦截controller记录用户操作
	 * @param joinPoint
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint){

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		SecurityContextImpl securityContextImpl = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		User user = (User)securityContextImpl.getAuthentication().getPrincipal();
		String ip = request.getRemoteAddr();
		try {
			UserLog userLog = new UserLog();
			userLog.setAction(joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"()");
			userLog.setActionDetail(getControllerMethodDetail(joinPoint));
			userLog.setType("0");
			userLog.setIp(ip);
			userLog.setExceptionCode(null);
			userLog.setExceptionDetail(null);
			userLog.setParams(null);
			userLog.setCreateDate(new Date());
			userLog.setUserId(user.getId());
			userLog.setUserName(user.getUsername());
			userLog.setRealName(user.getRealname());
			userLogService.save(userLog);
		} catch (Exception e) {
			logger.error("异常信息{}", e.getMessage());
		}
	}
	
	/**
	 * 拦截service记录异常
	 * @param joinPoint
	 */
	@AfterThrowing(pointcut="serviceAspect()",throwing="e")
	public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		SecurityContextImpl securityContextImpl = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		User user = (User)securityContextImpl.getAuthentication().getPrincipal();
		String ip = request.getRemoteAddr();
		
		String params = "";
		if(joinPoint.getArgs() != null && joinPoint.getArgs().length>0){
			for(int i =0;i<joinPoint.getArgs().length;i++){
				params += JSONObject.toJSONString(joinPoint.getArgs()[i]) + ";";
			}
		}
		
		try {
			UserLog userLog = new UserLog();
			userLog.setAction(joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"()");
			userLog.setActionDetail(getControllerMethodDetail(joinPoint));
			userLog.setType("1");
			userLog.setIp(ip);
			userLog.setExceptionCode(e.getClass().getName());
			userLog.setExceptionDetail(e.getMessage());
			userLog.setParams(params);
			userLog.setCreateDate(new Date());
			userLog.setUserId(user.getId());
			userLog.setUserName(user.getUsername());
			userLog.setRealName(user.getRealname());
			userLogService.save(userLog);
		} catch (Exception ex) {
			logger.error("异常信息{}", ex.getMessage());
		}
	}
	
	/**
	 * 获取SystemServiceLog注解的description值
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static String getServiceMethodDetail(JoinPoint joinPoint) throws Exception{
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String detail = "";
		for(Method method : methods){
			if(method.getName().equals(methodName)){
				Class[] clazzs = method.getParameterTypes();
				if(clazzs.length == arguments.length){
					detail = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return detail;
	}
	
	/**
	 * 获取SystemControllerLog注解的description值
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static String getControllerMethodDetail(JoinPoint joinPoint) throws Exception{
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String detail = "";
		for(Method method : methods){
			if(method.getName().equals(methodName)){
				Class[] clazzs = method.getParameterTypes();
				if(clazzs.length == arguments.length){
					detail = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return detail;
	}

}
