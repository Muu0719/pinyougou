package com.pinyougou.manager.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.GoodsService;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsExample;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.vo.GoodsVo;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbSellerMapper sellerMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	/**
	 * 增加
	 */
	@Override
	public void add(GoodsVo goodsVo) {
		
		//1、先获取TBGoods对象
		TbGoods goods = goodsVo.getGoods();
		//2、先添加TBGoods对象，返回主键
		goods.setAuditStatus("0");
		goods.setIsDelete("0");
		goodsMapper.insertSelective(goods);
		//3、添加goodsDesc
		goodsVo.getGoodsDesc().setGoodsId(goods.getId());
		goodsDescMapper.insertSelective(goodsVo.getGoodsDesc());
		
		//保存ItemList集合的
		List<TbItem> itemList = goodsVo.getItemList();
		for (TbItem item : itemList) {
			
			//商品的SKU的title是SPU的name+规格
			String title = "";
			title = goods.getGoodsName();
			Map<String,String> specMap = JSON.parseObject(item.getSpec(), Map.class);
			for(String key : specMap.keySet()){
				title += "	" + specMap.get(key);
			}
			item.setTitle(title);
			item.setCategoryid(goods.getCategory3Id());
			item.setCreateTime(new Date());
			item.setUpdateTime(new Date());
			item.setGoodsId(goods.getId());
			item.setSellerId(goods.getSellerId());
			
			//获取商品叶子节点分类
			String categoryName = itemCatMapper.selectByPrimaryKey(item.getCategoryid()).getName();
			item.setCategory(categoryName);
			//获取商品品牌
			String brandName = brandMapper.findOne(goods.getBrandId()).getName();
			item.setBrand(brandName);
			//获取商品所属的商家
			String nickName = sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName();
			item.setSeller(nickName);
			//图片
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goods.getId());
			List<Map> imageList = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
			if(imageList.size() > 0){
				item.setImage(String.valueOf(imageList.get(0).get("url")));
			}
			//添加
			itemMapper.insertSelective(item);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods findOne(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
