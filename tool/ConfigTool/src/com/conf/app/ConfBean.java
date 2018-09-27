package com.conf.app;


/**
 *
 *2016-12-02  14:54:43
 *@author ye.yuan
 *
 */
public class ConfBean {
	
	private int index;
	
	private int key;

	private String see;
	
	private String fieldName;
	
	private String methodName;
	
	private String type;
	
	private	String  value;
	
	public ConfBean(int index,String see, String fieldName, String fieldType,String  value) {
		this.index=index;
		if(index==0){
//			this.key=Integer.parseInt(value);
		}
		this.see = see;
		this.fieldName = fieldName;
		if(fieldType.equals("str")){
			this.type = "Object";
		}else if(fieldType.equals("f")){
			this.type = "double";
		}else {
			this.type = fieldType;
		}
		this.value=value;
		String nameTitle = fieldName.substring(0,1);
		String nameBody = fieldName.substring(1);
		this.fieldName = fieldName;
		this.methodName = nameTitle.toUpperCase()+nameBody;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getSee() {
		return see;
	}

	public void setSee(String see) {
		this.see = see;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
