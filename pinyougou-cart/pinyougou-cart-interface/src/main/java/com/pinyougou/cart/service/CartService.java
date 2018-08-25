package com.pinyougou.cart.service;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.vo.Cart;

@Service
public interface CartService {

	
	/** 
	
	 * @Title:        findCartListFromRedis 
	 * @Description:  根据sessionID查询redis购物车的信息
	 * @param:        @param sessionId
	 * @param:        @return    
	 * @return:       List<Cart>    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 09:08:02
	 */
	List<Cart> findCartListFromRedis(String sessionId);

	
	/** 
	
	 * @Title:        addGoodsToCartList 
	 * @Description:  添加商品到购物车集合对象里
	 * @param:        @param cartList
	 * @param:        @param itemId
	 * @param:        @param num
	 * @param:        @return    
	 * @return:       List<Cart>    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 09:08:04
	 */
	List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);


	
	/** 
	
	 * @Title:        addCartListToRedis 
	 * @Description:  把购物车存到redis中,sessionId作为Key;
	 * @param:        @param key
	 * @param:        @param cartList    
	 * @return:       void    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 09:08:45
	 */
	void addCartListToRedis(String key, List<Cart> cartList);


	
	/** 
	
	 * @Title:        mergeCartList 
	 * @Description:  合并购物车       
	 * @param cartList_session
	 * @param cartList
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 18:08:48
	 */
	List<Cart> mergeCartList(List<Cart> cartList_session, List<Cart> cartList);


	
	/** 
	
	 * @Title:        delCartListToRedis 
	 * @Description:  根据key删除redis,中购物车的信息       
	 * @param sessionId   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-25 18:08:35
	 */
	void delCartListToRedis(String sessionId);

	
}
