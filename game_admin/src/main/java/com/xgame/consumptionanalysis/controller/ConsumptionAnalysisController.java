package com.xgame.consumptionanalysis.controller;

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
@RequestMapping("/consumptionanalysis/analysis")
public class ConsumptionAnalysisController extends BaseController {
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/habit",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="消费喜好")
	public ModelAndView habit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/habit");
		return mav;
	}
	
	@RequestMapping(value="/habitjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON habitJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/diamond",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="钻石消耗")
	public ModelAndView diamond(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/diamond");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/diamondjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON diamondJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/diamondjsonbuild",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON diamondJsonBuild(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/diamondjsoncommander",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON diamondJsonCommander(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/diamonddetail",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="钻石详情")
	public ModelAndView diamonddetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/diamonddetail");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/diamonddetailjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON diamonddetailJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String channel = request.getParameter("channel");
		params.put("serverArea", serverArea);
		params.put("channel", channel);
		Pagination pagination = playerService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		JSONArray jsonArr = new JSONArray();
		for(Object obj : pagination.getList()){
			JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(obj));
			JSONObject playerRTInfo = addPlayerRealTimeInfo(jsonObj.getIntValue("serverArea"),jsonObj.getLong("roleId"));
			if(playerRTInfo != null){
				JSONObject commanderData = playerRTInfo.getJSONObject("commanderData");
				if(commanderData != null){
					jsonObj.put("commanderLevel", commanderData.getIntValue("level"));
				}
				JSONObject currency = playerRTInfo.getJSONObject("currency");
				if(currency != null){
					jsonObj.put("diamond", currency.getLongValue("diamond"));
				}
			}
			jsonArr.add(jsonObj);
		}
		jsonMap.put("rows", jsonArr);
		return jsonMap;
	}
	
	@RequestMapping(value="/currency",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="货币剩余")
	public ModelAndView currency(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/currency");
		return mav;
	}
	
	@RequestMapping(value="/currencyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON currencyJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/property",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="道具剩余")
	public ModelAndView property(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/property");
		return mav;
	}
	
	@RequestMapping(value="/propertyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON propertyJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equip",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="装备剩余")
	public ModelAndView equip(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/equip");
		return mav;
	}
	
	@RequestMapping(value="/equipjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/army",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="部队剩余")
	public ModelAndView army(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/consumptionanalysis/army");
		return mav;
	}
	
	@RequestMapping(value="/armyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON armyJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
}
