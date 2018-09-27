package com;
/**
 *
 *2016-7-21  14:38:02
 *@author ye.yuan
 *
 */
public class AttributeBean {

	private int id;
	
	private String type;
	
	private String name;
	
	private String formName="";
	
	private String upperName="";
	
	private String allUpperName="";
	
	private String explain;
	
	public AttributeBean(int id, String type, String name,String explain) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.explain=explain;
		String[] split = name.split("_");
		for(int j=0;j<split.length;j++){
			String substring2 = split[j].substring(1);
			String substring = split[j].substring(0,1);
			String upperCase = substring.toUpperCase();
			if(j==0){
				formName = split[j];
			}
			else
			{
				formName += upperCase+substring2;
			}
			upperName += upperCase+substring2;
		}
		allUpperName=name.toUpperCase();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}

	public String getAllUpperName() {
		return allUpperName;
	}

	public void setAllUpperName(String allUpperName) {
		this.allUpperName = allUpperName;
	}
}
