package com.pinyougou.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.common.Result;
import com.pinyougou.pojo.vo.Cart;


@RestController
@RequestMapping("cart")
public class CartController {

	@Reference
	private CartService cartService;
	
	@RequestMapping("addGoodsToCartList")
	@CrossOrigin(origins="http://item.pinyougou.com",allowCredentials="true") 
	public Result addGoodsToCartList(HttpSession httpSession,Long itemId, Integer num){
		
//		response.setHeader("Access-Control-Allow-Origin", "http://item.pinyougou.com");
//		response.setHeader("Access-Control-Allow-Credentials", "true"); 
		
		try {
			//1、先获取SessionID
			String sessionId = httpSession.getId();
			
			String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
			System.err.println("当前登录者：" + loginName);
			//如果当前登录者名：anonymousUser就说明未登录状态
			if("anonymousUser".equals(loginName)){
				//2、根据sessionID从redis中获取购物车集合
				List<Cart> cartList = cartService.findCartListFromRedis(sessionId);
				
				//3、把传递过来的商品ID和num放到购物车集合中
				cartList = cartService.addGoodsToCartList(cartList,itemId, num);
				//4、把购物车集合数据放在redis中
				cartService.addCartListToRedis(sessionId,cartList);
				
			} else{
				//根据用户名去redis中查询购物车集合
				List<Cart> cartList = cartService.findCartListFromRedis(loginName);
				//把商品添加到购物车集合中
				//3、把传递过来的商品ID和num放到购物车集合中
				cartList = cartService.addGoodsToCartList(cartList,itemId, num);
				//4、把购物车集合数据放在redis中
				//把购物车集合放在redis中，以用户名作为Key
				cartService.addCartListToRedis(loginName,cartList);
			}
			
			return new Result(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	@RequestMapping("findCartList")
	public List<Cart> findCartList(HttpSession HttpSession){
		
		String key = HttpSession.getId();
		/*String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
	    //判断是否登录、如果没有登录默认是anonymousUser，如果不是那就说明登录状态
	    if(!"anonymousUser".equals(loginName)){
	        //登录后把当前登录者信息，赋值给SessionID，那么后面就不用修改了，直接根据登录者来查询
	        key = loginName;
	    }
		List<Cart> list = cartService.findCartListFromRedis(key);*/

		 //获取sessionID对应的数据
	    List<Cart> cartList_session = cartService.findCartListFromRedis(key);
	    
	    String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
	    if("anonymousUser".equals(loginName)){
	        return cartList_session;
	    }
	    
	    //获取当前登录者对应的数据
	    List<Cart> cartList = cartService.findCartListFromRedis(loginName);
	    
	    if(cartList_session.size() > 0){
	        //合并购物车
	        cartList=cartService.mergeCartList(cartList, cartList_session);
	        //清除redis中sessionId的数据
	        cartService.delCartListToRedis(key);
	        //将合并后的数据存入redis
	        cartService.addCartListToRedis(loginName, cartList);
	    }

		return cartList;
	}
	
}
