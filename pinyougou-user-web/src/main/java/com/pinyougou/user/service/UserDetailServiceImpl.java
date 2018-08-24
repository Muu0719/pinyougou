package com.pinyougou.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 返回的User对象需要一个对应的权限集合
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		// 这个权限集合应该是从数据库中获取的,这里先写固定的了
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new User(username,"",grantedAuths);
	}

}
