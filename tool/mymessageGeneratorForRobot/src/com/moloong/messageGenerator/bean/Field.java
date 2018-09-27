package com.moloong.messageGenerator.bean;

/**  
 * @Description: 字段 pojo定义
 * @author ye.yuan
 * @date 2011年4月16日 下午6:10:08  
 *
 */
public class Field {
    /**字段类型*/
	private String className;
	/**字段名称*/
	private String name;
	/**字段说明*/
	private String explain;
	
	private int id;
	
	/**List字段所容纳的数据类型*/
	private int listType;

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getListType() {
		return this.listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
