package com.pinyougou.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		//通过Mapper来获取数据
		List<TbBrand> list = brandMapper.findAll();
		
		return list;
	}

	@Override
	public PageResult findPage(Integer page, Integer size) {
		
		//设置分页的属性
		PageHelper.startPage(page, size);
		//进行查询
		Page<TbBrand> pageResult = (Page<TbBrand>)brandMapper.findAll();
		//直接用这个方法获取想要的数据
		
		return new PageResult(pageResult.getTotal(), pageResult.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		brandMapper.add(brand);
	}

	@Override
	public TbBrand findOne(Long id) {
		TbBrand brand = brandMapper.findOne(id);
		return brand;
	}

	@Override
	public void update(TbBrand brand) {
		brandMapper.update(brand);
	}

	@Override
	public void dele(Long[] ids) {
		
		for (Long id : ids) {
			brandMapper.dele(id);
		}
		
	}

	@Override
	public PageResult search(Integer page, Integer size, TbBrand brand) {
		
		//设置分页属性
		PageHelper.startPage(page, size);
		//查询
		Page<TbBrand> pageResult = (Page<TbBrand>)brandMapper.search(brand);
		
		return new PageResult(pageResult.getTotal(), pageResult.getResult());
	}

	@Override
	public List<Map> findBrandList() {
		return brandMapper.findBrandList();
	}

}
