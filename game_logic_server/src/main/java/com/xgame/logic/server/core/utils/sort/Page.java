package com.xgame.logic.server.core.utils.sort;

import java.io.Serializable;
import java.util.List;


/**
 * 分页信息
 * @author jiangxt
 *
 * @param <T>
 */
public class Page<T> implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8419977984313906672L;


	private int totalSize;
	
	private int startIndex;
	
	private int pageSize;
	
	private List<T> pageList;
	
	private int totalCount;

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getPageCount() {
		return ((totalSize - 1) / pageSize) + 1;
	}
	
	public static <T> Page<T> valueOf(int totalSize, int startIndex, int pageSize, List<T> pageList) {
		Page<T> page = new Page<T>();
		page.totalSize = pageSize;
		page.startIndex = startIndex;
		page.pageSize = pageSize;
		page.pageList = pageList;
		page.totalCount = page.getPageCount();
		return page;
	}
	
}
