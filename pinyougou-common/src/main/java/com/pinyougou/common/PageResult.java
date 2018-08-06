package com.pinyougou.common;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
	
	private Long total; //总条数
	
	private List list; //分页集合

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public PageResult(Long total, List list) {
		super();
		this.total = total;
		this.list = list;
	}

	public PageResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
