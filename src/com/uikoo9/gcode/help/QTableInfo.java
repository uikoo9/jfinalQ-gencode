package com.uikoo9.gcode.help;

import java.util.List;

/**
 * 表信息
 * @author qiaowenbin
 * @version 0.0.1.20141009
 */
public class QTableInfo {
	private String tableName;
	private String className;
	private String variableName;
	private String pkName;
	private List<QColumnInfo> columns;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public List<QColumnInfo> getColumns() {
		return columns;
	}
	public void setColumns(List<QColumnInfo> columns) {
		this.columns = columns;
	}
	
}
