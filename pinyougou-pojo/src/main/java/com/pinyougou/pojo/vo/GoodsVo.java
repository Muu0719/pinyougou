package com.pinyougou.pojo.vo;

import java.io.Serializable;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;

public class GoodsVo implements Serializable{
	
	private TbGoods goods;
	
	private TbGoodsDesc goodsDesc;

	public TbGoods getGoods() {
		return goods;
	}

	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}

	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	} 
	
}
