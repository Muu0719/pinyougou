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
	@Autowired
	private RedisTemplate redisTemplate;

	// 查询redis中购物车信息,不会返回Null,如果查不到,返回空集合
	public List<Cart> findCartListFromRedis(String sessionId) {
		List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(sessionId);
		if (null != cartList) {
			return cartList;
		}
		return new ArrayList<>();
	}

	// 添加旧的购物车到新的购物车,并返回一个新的购物车对象
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
		// 查询商品
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 判断商品状态
		if (null == item) {
			new RuntimeException("无效商品!");
		}
		if ("1".equals(item.getStatus())) {
			new RuntimeException("无效商品(商品已下架)!");
		}
		// 根据商品中的sellerId，去购物车集合中查询是否存储
		Cart cart = this.queryCartBySellerId(cartList, item.getSellerId());
		// 把商品对应的商家信息放到cart对象中
		if (null != cart) {
			// 证明集合中有该商家Id(购物车中已经有在该商家的商品)
			// 判断此商品是否已在购物车对应的商品集合中
			TbOrderItem orderItem = this.queryQrderItemByItemId(cart.getOrderItemList(), itemId);
			if (null != orderItem) {
				// 证明购物车中已经有该商品,当前数量再加num即可
				orderItem.setNum(orderItem.getNum() + num);
				orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));
				// 判断商品数量是否合理
				if (orderItem.getNum() < 1) {
					cart.getOrderItemList().remove(orderItem);
				}
				if (cart.getOrderItemList().size() == 0) {
					cartList.remove(cart);
				}
			} else {
				// 证明购物车中没有该商品,创建新的对象数量设置为num即可
				orderItem = this.createOrderItem(item, num);
				cart.getOrderItemList().add(orderItem);
			}
		} else {

			// 需要继续创建TbOrderItem订单项对象,把新加的商品信息添加进去
			TbOrderItem orderItem = this.createOrderItem(item, num);
			List<TbOrderItem> orderItemList = new ArrayList<>();
			orderItemList.add(orderItem);
			// 购物车中不存在该商家的商品
			cart = new Cart();
			cart.setSellerId(item.getSellerId());
			cart.setSellerName(item.getSeller());
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

	private TbOrderItem queryQrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
		for (TbOrderItem tbOrderItem : orderItemList) {
			if (tbOrderItem.getItemId().equals(itemId)) {
				return tbOrderItem;
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
			if (cart.getSellerId().equals(sellerId)) {
				return cart;
			}
		}
		return null;
	}

	public void addCartListToRedis(String key, List<Cart> cartList) {
		redisTemplate.boundHashOps("cartList").put(key, cartList);

	}
	
	//合并购物车
	public List<Cart> mergeCartList(List<Cart> cartList_session, List<Cart> cartList) {
		for (Cart cart : cartList) {
			List<TbOrderItem> orderItemList = cart.getOrderItemList();
			for (TbOrderItem orderItem : orderItemList) {
				cartList = this.addGoodsToCartList(cartList, orderItem.getItemId(), orderItem.getNum());
			}
		}
		return cartList;
	}

	@Override
	public void delCartListToRedis(String sessionId) {
		redisTemplate.boundHashOps("cartList").delete(sessionId);
	}

}
