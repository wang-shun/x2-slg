package com.xgame.logic.server.core.gamelog.logs;
/**
 *	@author ye.yuan
 *	@version 1.0
 */
public class MetaData {
	private String fieldName;
	private String fieldType;
	private String remark;

	public MetaData() {
	}

	public MetaData(String fieldName, String fieldType, String remark) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.remark = remark;
	}

	public ColumnInfo toColumnInfo() {
		ColumnInfo info = new ColumnInfo();
		info.setName(getFieldName());
		if (this.fieldType.contains("(")) {
			String replace = this.fieldType.replace(")", "");
			String[] split = replace.split("\\(");
			info.setType(split[0]);
			info.setSize(Integer.valueOf(split[1]));
			info.setNullable(Boolean.valueOf(true));
		} else {
			info.setType(getFieldType());
			info.setSize(Integer.valueOf(0));
			info.setNullable(Boolean.valueOf(true));
		}
		return info;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("");
		if ((this.fieldName != null) && (this.fieldType != null)) {
			sb.append("`" + this.fieldName + "`\t" + this.fieldType
					+ "\t COMMENT '" + this.remark + "'");
		}
		return sb.toString();
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
