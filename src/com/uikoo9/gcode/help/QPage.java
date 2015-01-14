package com.uikoo9.gcode.help;

import java.util.List;

/**
 * 分页bean
 * @author qiaowenbin
 * @version 0.0.3.20140825
 */
public class QPage {

	private String ids;
	private String str;
	private int pageNumber;				
	private int pageSize;				
	private int totalPage;				
	private int totalRow;				
	private List<? extends Object> list;
	
	public QPage() {
		super();
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public List<? extends Object> getList() {
		return list;
	}
	public void setList(List<? extends Object> list) {
		this.list = list;
	}
	public int getStart(){
		return (pageNumber - 1) * pageSize;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
}
