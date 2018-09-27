package com.xgame.gm.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xgame.gm.entity.Authority;
import com.xgame.gm.entity.User;
import com.xgame.gm.entity.UserLog;
import com.xgame.gm.service.IAuthorityService;
import com.xgame.gm.service.IUserLogService;

public class ValidateCodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Resource
	private IAuthorityService authorityService;
	@Resource
	private IUserLogService userLogService;
	
	private boolean postOnly = true;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		//添加部分业务逻辑
		//session数据填入
		if (!"".equals(username)) {
			HttpSession session = request.getSession(false);

			if (session != null ){
				List<Authority> list = authorityService.getParentAuthority();
				HttpSession nowSession  = request.getSession();
				nowSession.setAttribute("_menulist", list);
			}
		}
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		Authentication auth = this.getAuthenticationManager().authenticate(authRequest);
		//设置用户登录日志
		User user = (User)auth.getPrincipal();
		user.setLastLoginDate(new Date());
		if(auth.isAuthenticated()){
			UserLog userLog = new UserLog();
			userLog.setAction("com.xgame.gm.service.impl.ValidateCodeUsernamePasswordAuthenticationFilter.attemptAuthentication()");
			userLog.setActionDetail("登录");
			userLog.setType("0");
			userLog.setIp(request.getRemoteAddr());
			userLog.setExceptionCode(null);
			userLog.setExceptionDetail(null);
			userLog.setParams(null);
			userLog.setCreateDate(new Date());
			userLog.setUserId(user.getId());
			userLog.setUserName(user.getUsername());
			userLog.setRealName(user.getRealname());
			userLogService.save(userLog);
		}
		return auth;
	}
}
