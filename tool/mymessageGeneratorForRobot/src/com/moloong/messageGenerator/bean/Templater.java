package com.moloong.messageGenerator.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *2016-11-09  10:49:51
 *@author ye.yuan
 *
 */
public class Templater {
	
	protected String extendsName;
	
	protected List<String> implementList = new ArrayList<String>();

	public String getExtendsName() {
		return extendsName;
	}

	public void setExtendsName(String extendsName) {
		this.extendsName = extendsName;
	}

	public List<String> getImplementList() {
		return implementList;
	}

	public void setImplementList(List<String> implementList) {
		this.implementList = implementList;
	}
	
}
