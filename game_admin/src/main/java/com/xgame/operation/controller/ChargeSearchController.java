package com.xgame.operation.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/operation/chargesearch")
public class ChargeSearchController extends BaseController {
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看充值记录")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/chargesearch/list");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/listjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON listjson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		params.put("roleId", roleId);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/mislist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看漏单记录")
	public ModelAndView mislist(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/chargesearch/list_miss");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/mislistjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON mislistjson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		params.put("roleId", roleId);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
}
