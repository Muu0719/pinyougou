package com.pinyougou.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.vo.Cart;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {

		
		// 1、根据这个商品ID查询商品对象
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		if (item == null) {
			throw new RuntimeException("无效商品");
		}
		if (!"1".equals(item.getStatus())) {
			throw new RuntimeException("无效商品");
		}
		// 2、根据商品中的sellerId，去购物车集合中查询是否存储
		Cart cart = this.queryCartBySellerId(cartList, item.getSellerId());
		// 3、判断此商品对应的商家ID是否在购物车存储
		if (cart != null) {// 已存在
			// 判断此商品是否已在购物车对应的商品集合中
			TbOrderItem orderItem = this.queryOrderItemByItemId(cart.getOrderItemList(), itemId);
			if (orderItem != null) {// 已存在
				// 修改商品存在购物车中的数量和价格
				orderItem.setNum(orderItem.getNum() + num);
				orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));

				// 判断商品的数量是否正确
				// 如果商品的数量小于等于0，就把商品orderItem从集合中删除
				if (orderItem.getNum() <= 0) {
					cart.getOrderItemList().remove(orderItem);
				}
				// 如果商品的集合的大小为0，那么就把cart对象从CartList中删除
				if (cart.getOrderItemList().size() == 0) {
					cartList.remove(cart);
				}

			} else {
				// 如果没有，创建一个新对象
				orderItem = this.createOrderItem(item, num);
				// 把orderItem数据放在集合中
				cart.getOrderItemList().add(orderItem);
			}
		} else {
			cart = new Cart();
			cart.setSellerId(item.getSellerId());
			cart.setSellerName(item.getSeller());
			// 如果没有，创建一个新对象
			TbOrderItem orderItem = this.createOrderItem(item, num);
			List<TbOrderItem> orderItemList = new ArrayList<>();
			orderItemList.add(orderItem);
			cart.setOrderItemList(orderItemList);
			cartList.add(cart);
		}
		return cartList;
	}

	/**
	 * 根据商品SKU和数量创建orderItem对象
	 * 
	 * @param item
	 * @param num
	 * @return
	 */
	private TbOrderItem createOrderItem(TbItem item, Integer num) {
		TbOrderItem orderItem = new TbOrderItem();
		orderItem.setGoodsId(item.getGoodsId());
		orderItem.setItemId(item.getId());
		orderItem.setNum(num);
		orderItem.setPicPath(item.getImage());
		orderItem.setPrice(item.getPrice());
		orderItem.setSellerId(item.getSellerId());
		orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
		orderItem.setTitle(item.getTitle());
		return orderItem;
	}

	/**
	 * 根据商品的ID查询是否在购物车商品集合中
	 * 
	 * @param orderItemList
	 * @param itemId
	 * @return
	 */
	private TbOrderItem queryOrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
		for (TbOrderItem orderItem : orderItemList) {
			if (orderItem.getItemId().longValue() == itemId.longValue()) {
				return orderItem;
			}
		}
		return null;
	}

	/**
	 * 根据sellerID查询购物车集合
	 * 
	 * @param cartList
	 * @param sellerId
	 * @return
	 */
	private Cart queryCartBySellerId(List<Cart> cartList, String sellerId) {
		for (Cart cart : cartList) {
			if (sellerId.equals(cart.getSellerId())) {
				return cart;
			}
		}
		return null;
	}

	@Autowired
	private RedisTemplate redisTemplate;

	
	
	public List<Cart> findCartListFromRedis(String sessionId) {
		List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(sessionId);
		if (cartList != null) {
			return cartList;
		}
		return new ArrayList<>();
	}

	public void addCartListToRedis(String sessionId, List<Cart> cartList) {
		redisTemplate.boundHashOps("cartList").put(sessionId, cartList);
	}

	public List<Cart> mergeCartList(List<Cart> cartList, List<Cart> cartList_session) {
		for (Cart cart : cartList_session) {
			for (TbOrderItem item : cart.getOrderItemList()) {
				cartList = this.addGoodsToCartList(cartList, item.getItemId(), item.getNum());
			}
		}
		return cartList;
	}

	@Override
	public void delCartListToRedis(String key) {
		redisTemplate.boundHashOps("cartList").delete(key);
	}

}
