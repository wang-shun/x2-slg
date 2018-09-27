package com.xgame.playeranalysis.controller;

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
@RequestMapping("/playeranalysis/analysis")
public class PlayerAnalysisController extends BaseController {
	
	@RequestMapping(value="/chargerate",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="付费率分析")
	public ModelAndView chargerate(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/playeranalysis/chargerate");
		return mav;
	}
	
	@RequestMapping(value="/chargeratejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON chargerateJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/chargeratejsontime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON chargerateJsonTime(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/chargeratejsongift",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON chargerateJsonGift(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/chargeratejsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON chargerateJsonLevel(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retention",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="存留分析")
	public ModelAndView retention(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/playeranalysis/retention");
		return mav;
	}
	
	@RequestMapping(value="/retentionjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonplayer",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonPlayer(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewleavetime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewLeaveTime(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewleavestart",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewLeaveStart(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewleavelevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewLeaveLevel(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewstaytime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewStayTime(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewstaystart",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewStayStart(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonnewstaylevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonNewStayLevel(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonlifecycle",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonLifeCycle(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/retentionjsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON retentionJsonLevel(HttpServletRequest request,HttpServletResponse response){
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

	@RequestMapping(value="/grade",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="等级分析")
	public ModelAndView grade(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/playeranalysis/grade");
		return mav;
	}
	
	@RequestMapping(value="/gradejsonbuild",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gradeJsonBuild(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gradejsoncommander",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gradeJsonCommander(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gamehabit",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="游戏习惯")
	public ModelAndView gamehabit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/playeranalysis/gamehabit");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/gamehabitjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gamehabitJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gamehabitjsonday",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gamehabitJsonDay(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gamehabitjsonper",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gamehabitJsonPer(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gamehabitround",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gamehabitJsonRound(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/gamehabitjsonrolenum",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON gamehabitJsonRolenum(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/account",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="小号分析")
	public ModelAndView account(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/playeranalysis/account");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/accountjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON accountJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/authentication",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="身份验证")
	public ModelAndView authentication(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/playeranalysis/authentication");
		return mav;
	}
	
	@RequestMapping(value="/authenticationjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON authenticationJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/authenticationjsonage",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON authenticationJsonAge(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/authenticationjsonarea",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON authenticationJsonArea(HttpServletRequest request,HttpServletResponse response){
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
