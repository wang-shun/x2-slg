package com.xgame.operation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xgame.base.controller.BaseController;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/operation/inneraccount")
public class InnerAccountController extends BaseController {
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看内部账户")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/inneraccount/list");
		
		return mav;
	}
}
