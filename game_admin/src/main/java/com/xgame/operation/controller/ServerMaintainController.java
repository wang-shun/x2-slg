package com.xgame.operation.controller;

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
@RequestMapping("/operation/servermaintain")
public class ServerMaintainController extends BaseController {
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="服务器维护")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/servermaintain/list");
		return mav;
	}
	
	@RequestMapping(value="/listjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON listjson(HttpServletRequest request,HttpServletResponse response){
		JSONArray jsonArr = new JSONArray();
		return jsonArr;
	}
}
