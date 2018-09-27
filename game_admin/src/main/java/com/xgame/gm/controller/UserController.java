package com.xgame.gm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.gm.entity.Authority;
import com.xgame.gm.entity.User;
import com.xgame.gm.service.IAuthorityService;
import com.xgame.gm.service.IUserService;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping(value="/backadmin/user")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IAuthorityService authorityService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看用户列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/user/list");
		return mav;
	}
	
	/**
	 * 异步加载表格数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/json",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="加载用户列表数据")
	public @ResponseBody JSON json(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String username = request.getParameter("username");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("username", username);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = userService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", new JSONArray(pagination.getList()));
		return new JSONObject(jsonMap);
	}
	
	@RequestMapping(value="/edit",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="新增修改用户信息")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/user/edit");
		List<Authority> menus = authorityService.getParentAuthority();
		String id = request.getParameter("id");
		User user = new User();
		if(null != id && !"".equals(id)){
			user = userService.findById(id);
		}
		mav.addObject("user", user);
		mav.addObject("authorities", user.getAuthorities());
		mav.addObject("menus", menus);
		return mav;
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="保存用户信息")
	public @ResponseBody JSON save(HttpServletRequest request,HttpServletResponse response,User user,String[] ids){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String password = md5.encodePassword("888888", "xgame");
		List<Authority> list = authorityService.getByIds(ids);
		if(!list.isEmpty()){
			user.setAuthorities(new HashSet<Authority>(list));
		}
		if(null == user.getId() || "".equals(user.getId())){
			user.setCreateDate(new Date());
			user.setEnabledFlag("Y");
			user.setNetworkFlag("Y");
			user.setPassword(password);
			userService.save(user);
		}else{
			userService.update(user);
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "修改成功");
		return new JSONObject(jsonMap);
	}
	
	@RequestMapping(value="/resetpassword",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="重置用户密码")
	public @ResponseBody JSON resetPassword(HttpServletRequest request,HttpServletResponse response,String id){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String password = md5.encodePassword("888888", "xgame");
		User user = userService.findById(id);
		user.setPassword(password);
		userService.update(user);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "重置成功，密码888888");
		return new JSONObject(jsonMap);
	}
	
	@RequestMapping(value="/updatestatus",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="更新用户状态")
	public @ResponseBody JSON updateStatus(HttpServletRequest request,HttpServletResponse response,String id,String enabledFlag,String networkFlag){
		User user = userService.findById(id);
		user.setEnabledFlag(enabledFlag);
		user.setNetworkFlag(networkFlag);
		userService.update(user);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "修改成功");
		return new JSONObject(jsonMap);
	}
}
