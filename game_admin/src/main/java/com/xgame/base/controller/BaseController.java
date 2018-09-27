package com.xgame.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xgame.gm.entity.User;
import com.xgame.redis.CrossRedisClient;

public class BaseController {
	
	protected User getUser(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		SecurityContextImpl securityContextImpl = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		User user = (User)securityContextImpl.getAuthentication().getPrincipal();
		return user;
	}
	
	/**
	 * 实时解析远程redis库的数据拼装进jsonObj中
	 * @param jsonObj
	 */
	protected JSONObject addPlayerRealTimeInfo(int serverId,long roleId){
		CrossRedisClient crossRedisClient = new CrossRedisClient(serverId);
		String playerInfo = crossRedisClient.hget("Player", roleId);
		JSONObject obj = JSON.parseObject(playerInfo,Feature.DisableSpecialKeyDetect);
		return obj;
	}
	
	protected <T> T getBean(String beanId,Class<T> clazz){
		WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
		
		return wac.getBean(beanId, clazz);
	}
}
