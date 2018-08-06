package com.pinyougou.common;

import java.io.Serializable;

/**
 * 用于返回执行结果: true/false 和 提示信息(message)
 * @author Muu
 *
 */
public class Result implements Serializable{

	private boolean success;	//执行结果
	
	private String message;		//提示信息

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
