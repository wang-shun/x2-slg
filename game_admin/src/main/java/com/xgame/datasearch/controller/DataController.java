package com.xgame.datasearch.controller;

import java.text.SimpleDateFormat;
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
@RequestMapping("/datasearch/datalist")
public class DataController extends BaseController{
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/summary",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="数据总览")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/datalist/summary");
		Map<String,Object> params = new  HashMap<String,Object>();
		Pagination pagination = new Pagination(pageNumber, pageSize, 0);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/summaryjsontotal",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonTotal(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONObject jsonMap = new JSONObject();
		return jsonMap;
	}
	
	@RequestMapping(value="/summaryjsonkeytarget",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonKeyTarget(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/summaryjsonconsumerate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonConsumeRate(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/summaryjsonretention",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonRetention(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/summaryjsongametime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonGameTime(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/summaryjsonnewvalue",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON summaryJsonNewValue(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/conversionrate",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="转化率")
	public ModelAndView conversionrate(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/datalist/conversionrate");
		return mav;
	}
	
	@RequestMapping(value="/conversionratejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON conversionrateJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/onlinelist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="在线统计")
	public ModelAndView onlinelist(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/datalist/onlinelist");
		return mav;
	}
	
	@RequestMapping(value="/onlinelistjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON onlinelistJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/onlinerole",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="在线角色")
	public ModelAndView onlinerole(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/datalist/onlinerole");
		return mav;
	}
	
	@RequestMapping(value="/onlinerolejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON onlineroleJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		String roleName = request.getParameter("roleName");
		String serverArea = request.getParameter("serverArea");
		params.put("roleId", roleId);
		params.put("roleName", roleName);
		params.put("serverArea", serverArea);
		Pagination pagination = playerService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArr = new JSONArray();
		for(Object obj : pagination.getList()){
			JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(obj));
			JSONObject playerRTInfo = addPlayerRealTimeInfo(jsonObj.getIntValue("serverArea"),jsonObj.getLong("roleId"));
			if(playerRTInfo != null){
				JSONObject basics = playerRTInfo.getJSONObject("basics");
				if(basics != null){
					if(basics.getDate("loginTime") != null){
						jsonObj.put("lastLoginDate", sdf.format(basics.getDate("loginTime")));
					}
				}
			}
			jsonArr.add(jsonObj);
		}
		jsonMap.put("rows", jsonArr);
		return jsonMap;
	}
	
	@RequestMapping(value="/serverinfo",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="服务器信息")
	public ModelAndView serverinfo(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/datalist/serverinfo");
		return mav;
	}
	
	@RequestMapping(value="/serverinfojson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON serverinfoJson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(rows), 0);
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return jsonMap;
	}
	
	@RequestMapping(value="/operationlist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="运营商信息")
	public ModelAndView operationlist(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/datalist/operationlist");
		return mav;
	}
	
	@RequestMapping(value="/operationlistjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON operationlistJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
}
