package com.xgame.operation.controller;

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
import com.xgame.playersearch.entity.Player;
import com.xgame.playersearch.service.IPlayerService;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/operation/mail")
public class MailController extends BaseController{
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/broadcast",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="添加广播")
	public ModelAndView broadcast(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/mail/broadcast");
		return mav;
	}
	
	@RequestMapping(value="/send",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="添加邮件")
	public ModelAndView send(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/mail/send");
		return mav;
	}
	
	@RequestMapping(value="/showreceives",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON showreceives(HttpServletRequest request,HttpServletResponse response){
		String roleIds = request.getParameter("roleIds");
		StringBuffer sb = new StringBuffer("");
		if(roleIds != null && !"".equals(roleIds)){
			for(String roleId : roleIds.split(";")){
				Player player = roleId==null?null:playerService.findById(Long.parseLong(roleId));
				if(player != null){
					sb.append(player.getServerArea()+"#"+player.getRoleName()+"#"+player.getVipLevel()+"#"+player.getLevel()+"</br>");
				}
			}
		}
		
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("success", true);
		jsonMap.put("message", sb.toString());
		return jsonMap;
	}
	
	@RequestMapping(value="/history",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看历史邮件")
	public ModelAndView history(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/mail/history");
		return mav;
	}
	
	@RequestMapping(value="/historyjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON historyjson(HttpServletRequest request,HttpServletResponse response){
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
