package com.xgame.util.rmi.impl;

import org.springframework.stereotype.Component;

import com.xgame.util.rmi.RemoteService;

/**
 * 跨服请求接口
 * @author jacky.jiang
 *
 */
@Component
public class RemoteServiceImpl implements RemoteService {

	@Override
	public void sayHello() {
		System.out.println("aaaaaaaaaaaaaaaaaaaaa");
	}
	
}
