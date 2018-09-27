package com.xgame.common;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	
	private int pageSize=10;
	private int pageNumber=1;
	private long totalPage;
	private long total=0;
	private List list = new ArrayList();
	
	public Pagination(){
		
	}
	
	public Pagination(int pageNumber,int pageSize,long total){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.total = total;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.totalPage = this.total==0?0:this.total/(this.pageSize+1)+1;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	public int getFirstResult(){
		return this.pageNumber<2?0:(this.pageNumber-1)*this.pageSize;
	}
}
