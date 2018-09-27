package com.xgame.gm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.gm.service.IUserLogService;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping(value="/backadmin/userlog")
public class UserLogController extends BaseController {
	@Resource
	private IUserLogService userLogService;
	
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看用户日志列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/userlog/list");
		return mav;
	}
	
	/**
	 * 异步加载表格数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/json",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="加载用户日志列表数据")
	public @ResponseBody JSON json(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String realName = request.getParameter("realName");
		String actionDetail = request.getParameter("actionDetail");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("realName", realName);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		params.put("actionDetail", actionDetail);
		Pagination pagination = userLogService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return new JSONObject(jsonMap);
	}
}
