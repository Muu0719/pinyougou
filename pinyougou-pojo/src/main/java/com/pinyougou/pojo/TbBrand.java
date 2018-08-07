package com.pinyougou.pojo;

import java.io.Serializable;

/**
 * 什么情况下序列化接口：
 * 当分布式开发的时候，多服务之间传递数据的时候，那么这个数据就需要序列化接口！
 * @author muu
 *
 */
public class TbBrand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String firstChar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}
	
	
}