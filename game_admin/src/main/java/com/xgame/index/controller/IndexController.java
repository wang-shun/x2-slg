package com.xgame.index.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.config.CHlanguage_ZhCN.CHlanguage_ZhCNPir;
import com.xgame.config.CHlanguage_ZhCN.CHlanguage_ZhCNPirFactory;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.redis.CrossRedisClient;

@Controller
public class IndexController extends BaseController{
	
	@RequestMapping(value="/index",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/index/index");
		int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"10":request.getParameter("pageSize"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber")==null?"1":request.getParameter("pageNumber"));
		int total = 2000;
//		RemoteService rs = (RemoteService)getBean("localServiceClient",RemoteService.class);
//		rs.sayHello();
		BuildingPir bp = BuildingPirFactory.getInstance().getFactory().get(1101);
		JSONObject jsonObject = JSON.parseObject(bp.toString());
		CHlanguage_ZhCNPir chlzhcnp = CHlanguage_ZhCNPirFactory.getInstance().getFactory().get(jsonObject.getIntValue("name"));
		System.out.println(chlzhcnp);
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setPageNumber(pageNumber);
		pagination.setTotal(total);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/index/json",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON json(HttpServletRequest request,HttpServletResponse response){
		CrossRedisClient crossRedisClient = new CrossRedisClient(1001);
		String roleId = request.getParameter("roleId");
		String player = crossRedisClient.hget("Player", roleId);
		JSONObject obj = JSON.parseObject(player,Feature.DisableSpecialKeyDetect);
		return obj;
	}
}
