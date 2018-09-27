package com.xgame.incomeanalysis.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xgame.base.controller.BaseController;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/incomeanalysis/analysis")
public class IncomeAnalysisController extends BaseController {
	
	@RequestMapping(value="/incomelist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="收入数据")
	public ModelAndView incomeList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/incomeanalysis/incomelist");
		return mav;
	}
	
	@RequestMapping(value="/incomelistjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonday",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonDay(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonbuild",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonBuild(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsoncommander",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonCommander(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonweek",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonWeek(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonmonth",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonMonth(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonweeksum",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonWeekSum(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsonmonthsum",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonMonthSum(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsontimeround",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonTimeRound(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/incomelistjsongiftround",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON incomeListJsonGiftRound(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String level = request.getParameter("level");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("level", level);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
}
