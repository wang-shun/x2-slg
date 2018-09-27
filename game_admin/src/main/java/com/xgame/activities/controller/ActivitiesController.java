package com.xgame.activities.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/activities/activity")
public class ActivitiesController extends BaseController {
	
	@RequestMapping(value="/status",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="运营活动状态")
	public ModelAndView status(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/activities/status");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/start",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="运营活动开启")
	public ModelAndView start(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/activities/start");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="活动内容增加")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/activities/add");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看活动库")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/activities/list");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/define",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="事件系统设定")
	public ModelAndView define(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/activities/define");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
}
