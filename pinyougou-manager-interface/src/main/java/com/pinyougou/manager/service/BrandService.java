package com.pinyougou.manager.service;

import java.util.List;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.TbBrand;

public interface BrandService {

	/**
	 * 查询品牌列表
	 * @return
	 */
	List<TbBrand> findAll();

	/**
	 * 好习惯：在接口上加注释：
	 * 分页查询品牌列表
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult findPage(Integer page, Integer size);

	/**
	 * 添加品牌
	 * @param brand
	 */
	void add(TbBrand brand);
	
	/**
	 * 根据品牌ID查询品牌对象
	 * @param id
	 * @return
	 */
	public TbBrand findOne(Long id);

	/**
	 * 修改品牌
	 * @param brand
	 */
	void update(TbBrand brand);

	/**
	 * 根据多个ID删除品牌
	 * @param ids
	 */
	void dele(Long[] ids);

	/**
	 * 根据条件对象分页
	 * @param page
	 * @param size
	 * @param brand
	 * @return
	 */
	PageResult search(Integer page, Integer size, TbBrand brand);
	
}
