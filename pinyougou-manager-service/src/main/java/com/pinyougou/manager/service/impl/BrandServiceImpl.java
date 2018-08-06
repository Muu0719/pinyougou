package com.pinyougou.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private TbBrandMapper tbBrandMapper; 
	
	public List<TbBrand> findAll() {
		return null;
	}

	public PageResult findPage(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> pageResult = (Page<TbBrand>)tbBrandMapper.findAll();
		return new PageResult(pageResult.getTotal(),pageResult.getResult());
	}

	
	public void add(TbBrand tbBrand) {
		tbBrandMapper.add(tbBrand);
	}

	public TbBrand findOne(Long id) {
		return tbBrandMapper.findOne(id);
	}

	public void update(TbBrand tbBrand) {
		tbBrandMapper.update(tbBrand);
	}

	public void deleteBrand(Integer[] selectIds) {
		if (null != selectIds && selectIds.length > 0) {
			for (Integer id : selectIds) {
				tbBrandMapper.deleteBrand(id);
			}
		}
	}

	
	public PageResult search(Integer pageNum, Integer pageSize, TbBrand tbBrand) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> result = (Page<TbBrand>)tbBrandMapper.search(tbBrand);
		return new PageResult(result.getTotal(),result.getResult());
	}

}
