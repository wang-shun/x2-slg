package com.xgame.rechargeuser.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.playersearch.service.IPlayerService;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/rechargeuser/player")
public class RechargeUserController extends BaseController{
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="充值用户列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/rechargeuser/list");
		return mav;
	}
	
	@RequestMapping(value="/listjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON listJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		params.put("roleId", roleId);
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		Pagination pagination = playerService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		JSONArray jsonArr = new JSONArray();
		for(Object obj : pagination.getList()){
			JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(obj));
//			JSONObject playerRTInfo = addPlayerRealTimeInfo(jsonObj.getIntValue("serverArea"),jsonObj.getLong("roleId"));
//			JSONObject basics = playerRTInfo.getJSONObject("basics");
//			jsonObj.put("kickOut", basics.getIntValue("serverArea"));
			jsonArr.add(jsonObj);
		}
		jsonMap.put("rows", jsonArr);
		return jsonMap;
	}
	
	@RequestMapping(value="/warning",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="鲸鱼用户预警")
	public ModelAndView warning(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/rechargeuser/warning");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/warningjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON warningJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
}
