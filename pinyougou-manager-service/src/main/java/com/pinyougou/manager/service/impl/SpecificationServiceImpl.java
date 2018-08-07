package com.pinyougou.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	public PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification) {
		PageHelper.startPage(pageNum, pageSize);
		TbSpecificationExample example = new TbSpecificationExample();
		if(null != tbSpecification.getSpecName() && tbSpecification.getSpecName().length() > 0) {
			Criteria criteria = example.createCriteria();
			criteria.andSpecNameLike("%"+ tbSpecification.getSpecName() + "%");
		}
		Page<TbSpecification> pageResult = (Page<TbSpecification>)specificationMapper.selectByExample(example);
		return new PageResult(pageResult.getTotal(),pageResult.getResult());
	}
	
}
