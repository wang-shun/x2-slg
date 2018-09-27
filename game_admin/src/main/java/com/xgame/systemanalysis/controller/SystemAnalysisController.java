package com.xgame.systemanalysis.controller;

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
@RequestMapping("/systemanalysis/analysis")
public class SystemAnalysisController extends BaseController {
	
	@RequestMapping(value="/group",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-军团")
	public ModelAndView group(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/group");
		return mav;
	}
	
	@RequestMapping(value="/groupjsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/groupjsonmoney",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonMoney(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/groupjsondonate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonDonate(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonhelp",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonHelp(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonbox",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonBox(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonactive",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonActive(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonsuper",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonSuper(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonexercise",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonExercise(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonexerciserate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON groupJsonExerciseRate(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/property",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-道具")
	public ModelAndView property(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/property");
		return mav;
	}
	
	@RequestMapping(value="/propertyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON propertyJson(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/event",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-事件")
	public ModelAndView event(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/event");
		return mav;
	}
	
	@RequestMapping(value="/eventjsonplayer",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON eventJsonPlayer(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/eventjsonhonour",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON eventJsonHonour(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/eventjsongroup",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON eventJsonGroup(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/eventjsontime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON eventJsonTime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/vip",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-VIP")
	public ModelAndView vip(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/vip");
		return mav;
	}
	
	@RequestMapping(value="/vipjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON vipJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String channel = request.getParameter("channel");
		String serverArea = request.getParameter("serverArea");
		params.put("channel", channel);
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/onlinesupply",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-在线补给")
	public ModelAndView onlinesupply(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/onlinesupply");
		return mav;
	}
	
	@RequestMapping(value="/onlinesupplyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON onlinesupplyJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/warevent",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-副本")
	public ModelAndView warevent(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/warevent");
		return mav;
	}
	
	@RequestMapping(value="/wareventjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON wareventJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/territory",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-领土")
	public ModelAndView territory(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/territory");
		return mav;
	}
	
	@RequestMapping(value="/territoryjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON territoryJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resources",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-资源")
	public ModelAndView resources(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/resources");
		return mav;
	}
	
	@RequestMapping(value="/resourcesjsonproduce",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON resourcesJsonProduce(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsonconsume",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON resourcesJsonConsume(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsoncirculate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON resourcesJsonCirculate(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/resourcesjsoncollect",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON resourcesJsonCollect(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/terrorist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-恐怖份子")
	public ModelAndView terrorist(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/terrorist");
		return mav;
	}
	
	@RequestMapping(value="/terroristjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON terroristJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/terroristjsoncount",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON terroristJsonCount(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/science",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-科技研究")
	public ModelAndView science(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/science");
		return mav;
	}
	
	@RequestMapping(value="/sciencejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON scienceJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/sciencejsontime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON scienceJsontime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/army",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-部队")
	public ModelAndView army(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/army");
		return mav;
	}
	
	@RequestMapping(value="/armyjsonsystem",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON armyJsonSystem(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/armyjsonblue",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON armyJsonBlue(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/armyjsonbluechange",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON armyJsonBlueChange(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equipmaterial",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-装备材料")
	public ModelAndView equipmaterial(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/equipmaterial");
		return mav;
	}
	
	@RequestMapping(value="/equipmaterialjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipmaterialJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equipmaterialjsontime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipmaterialJsontime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/littlegame",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-付费小游戏")
	public ModelAndView littlegame(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/littlegame");
		return mav;
	}
	
	@RequestMapping(value="/littlegamejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON littlegameJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/crossfight",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-超时空战斗")
	public ModelAndView crossfight(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/crossfight");
		return mav;
	}
	
	@RequestMapping(value="/crossfightjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON crossfightJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/developinstitude",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-勘探开发院")
	public ModelAndView developinstitude(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/developinstitude");
		return mav;
	}
	
	@RequestMapping(value="/developinstitudejson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON developinstitudeJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/liveness",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-活跃度")
	public ModelAndView liveness(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/liveness");
		return mav;
	}
	
	@RequestMapping(value="/livenessjsonreach",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON livenessJsonReach(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/livenessjsonaward",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON livenessJsonAward(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commander",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-指挥官")
	public ModelAndView commander(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/commander");
		return mav;
	}
	
	@RequestMapping(value="/commanderjsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonlevelcount",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonLevelCount(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonleaderlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonLeaderLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonbadgetime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonBadgeTime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonbadgecount",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonBadgeCount(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonbadgesettime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonBadgeSetTime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonbadgesetcount",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonBadgeSetCount(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonpowerconsume",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonPowerConsume(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonpowerbuy",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonPowerBuy(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/commanderjsonpowertype",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON commanderJsonPowerType(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equip",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-装备")
	public ModelAndView equip(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/equip");
		return mav;
	}
	
	@RequestMapping(value="/equipjsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipJsonLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equipJsonQuality",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipJsonQuality(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equipjsonproduce",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipJsonProduce(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/equipjsondestory",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON equipJsonDestory(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("serverArea", serverArea);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/parts",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-配件")
	public ModelAndView parts(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/parts");
		return mav;
	}
	
	@RequestMapping(value="/partsjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON partsJson(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/building",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="系统分析-建筑")
	public ModelAndView building(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/systemanalysis/building");
		return mav;
	}
	
	@RequestMapping(value="/buildingjsonlevel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON buildingJsonLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
	
	@RequestMapping(value="/buildingjsontime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON buildingJsonTime(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new  HashMap<String,Object>();
		String serverArea = request.getParameter("serverArea");
		params.put("serverArea", serverArea);
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
}
