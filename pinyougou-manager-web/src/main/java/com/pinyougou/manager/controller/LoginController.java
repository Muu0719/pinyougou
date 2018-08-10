package com.pinyougou.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	/**
	 * 
	
	 * @Title:        showLoginName 
	 * @Description:  获取登陆用户的用户名
	 * @param:        @return    
	 * @return:       Map<String,String>    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-10 11:08:19
	 */
	@RequestMapping("/showLoginName")
	public Map<String,String> showLoginName(){
		Map<String,String> map = new HashMap<>();
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		map.put("loginName", name);
		return map;
	}
}
