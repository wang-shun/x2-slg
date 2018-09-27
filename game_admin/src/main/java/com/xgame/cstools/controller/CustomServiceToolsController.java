package com.xgame.cstools.controller;

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
import com.alibaba.fastjson.parser.Feature;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.playersearch.service.IPlayerService;
import com.xgame.redis.CrossRedisClient;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/cstools/tools")
public class CustomServiceToolsController extends BaseController {
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/account",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看封号禁言列表")
	public ModelAndView account(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/cstools/list_account");
		return mav;
	}
	
	/**
	 * 异步加载表格数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/accountjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON accountJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		String roleName = request.getParameter("roleName");
		String vipLevel = request.getParameter("vipLevel");
		params.put("roleId", roleId);
		params.put("roleName", roleName);
		params.put("vipLevel", vipLevel);
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
	
	@RequestMapping(value="/contact",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看联系客服列表")
	public ModelAndView contact(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/cstools/list_contact");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/gschat",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看聊天gs列表")
	public ModelAndView gschat(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/cstools/list_gschat");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
}
