package com.pinyougou.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.common.Result;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.vo.Cart;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Reference
	private CartService cartService;

	
	/** 
	
	 * @Title:        addGoodsToCartList 
	 * @Description:  添加商品到购物车里       
	 * @param httpSession
	 * @param itemId
	 * @param num
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 18:08:13
	 */
	@RequestMapping("/addGoodsToCartList")
	public Result addGoodsToCartList(HttpSession httpSession, Long itemId, Integer num) {
		try {
			// 获取sessionID
			String sessionId = httpSession.getId();
			String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("当前登陆的用户名:" + loginName);
			List<Cart> cartList = null;
			String key = null;
			//判断当前用户是否登陆
			if("anonymousUser".equals(loginName)) {
				//未登录:
				// 根据sessionID去redis里查询数据,如果查不到则会返回空集合
				key = sessionId;
				
			} else {
				//已登陆
				key = loginName;
			}
				cartList = cartService.findCartListFromRedis(key);
				// 添加商品到购物车里
				cartList = cartService.addGoodsToCartList(cartList, itemId, num);
				// 再把最新的购物车对象存放到Rediszhong,Key
				cartService.addCartListToRedis(key, cartList);
			
			
			return new Result(true, "添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败???");
		}
	}

	/** 
	
	 * @Title:        findCartList 
	 * @Description:  查询购物车       
	 * @param httpSeesion
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 18:08:01
	 */
	@RequestMapping("/findCartList")
	public List<Cart> findCartList(HttpSession httpSeesion) {
		String sessionId = httpSeesion.getId();
		List<Cart> cartList_session = cartService.findCartListFromRedis(sessionId);
		String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
		if("anonymousUser".equals(loginName)) {
			//证明用户没有登录,直接返回查询的数据即可
			return cartList_session;
		}
		//执行到这里证明,用户登陆了,那么根据用户名来查询redis数据
		List<Cart> cartList = cartService.findCartListFromRedis(loginName);
		if(cartList_session.size() > 0) {
			//证明该用户在登陆前购物车中已经有商品存在,需要合并购物车
			//合并
			cartList = cartService.mergeCartList(cartList_session, cartList);
			//删除登陆之前的购物车信息
			 cartService.delCartListToRedis(sessionId);
			 //把最新的购物车存放到Redis中
			 cartService.addCartListToRedis(loginName, cartList);
		}
		return cartList;
	}



}
