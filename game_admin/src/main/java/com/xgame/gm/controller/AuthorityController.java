package com.xgame.gm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgame.base.controller.BaseController;
import com.xgame.gm.entity.Authority;
import com.xgame.gm.service.IAuthorityService;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping(value="/backadmin/authority")
public class AuthorityController extends BaseController {
	
	@Autowired
	private IAuthorityService authorityService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看菜单列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/authority/list");
		List<Authority> menus = authorityService.getParentAuthority();
		List<Authority> list = new ArrayList<Authority>();
		String id = request.getParameter("id");
		if(null != id && !"".equals(id)){
			Authority authority = authorityService.findById(id);
			list = authority.getChild();
		}else{
			list = menus;
		}
		mav.addObject("list", list);
		mav.addObject("menus", menus);
		mav.addObject("parentId", id);
		return mav;
	}
	
	@RequestMapping(value="/edit",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="新增修改菜单")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/authority/edit");
		List<Authority> menus = authorityService.getParentAuthority();
		String parentId = request.getParameter("parentId");
		String id = request.getParameter("id");
		Authority authority = new Authority();
		if(null != id && !"".equals(id)){
			authority = authorityService.findById(id);
		}
		mav.addObject("authority", authority);
		mav.addObject("parentId", parentId);
		mav.addObject("menus", menus);
		return mav;
	}
	
	@RequestMapping(value="/delete",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="删除菜单")
	public ModelAndView delete(String id,String parentId){
		ModelAndView mav = new ModelAndView("redirect:/backadmin/authority/list");
		authorityService.deleteById(id);
		mav.addObject("id", parentId);
		return mav;
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="保存菜单")
	public @ResponseBody JSON save(HttpServletRequest request,HttpServletResponse response,Authority authority){
		if(authority.getParent()==null || "".equals(authority.getParent().getId())){
			authority.setParent(null);
		}
		if(null == authority.getId() || "".equals(authority.getId())){
			authority = authorityService.save(authority);
		}else{
			authority = authorityService.update(authority);
		}
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "修改成功");
		return new JSONObject(jsonMap);
	}
}
