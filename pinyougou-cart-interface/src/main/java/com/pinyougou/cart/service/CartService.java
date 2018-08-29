package com.pinyougou.cart.service;

import java.util.List;

import com.pinyougou.pojo.vo.Cart;

public interface CartService {

	/**
	 * 把商品添加到购物车集合中
	 * @param carrtList
	 * @param itemId
	 * @param num
	 * @return
	 */
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

	/**
	 * 根据Key获取redis中的集合数据
	 * @param sessionId
	 * @return
	 */
	public List<Cart> findCartListFromRedis(String sessionId);

	/**
	 * 把购物车集合添加到redis中
	 * @param sessionId
	 * @param cartList
	 */
	public void addCartListToRedis(String sessionId, List<Cart> cartList);

	/**
	 * 把两个集合合并
	 * @param cartList
	 * @param cartList_session
	 * @return
	 */
	public List<Cart> mergeCartList(List<Cart> cartList, List<Cart> cartList_session);

	/**
	 * 根据Key来删除redis中数据
	 * @param key
	 */
	public void delCartListToRedis(String key);
	
}
