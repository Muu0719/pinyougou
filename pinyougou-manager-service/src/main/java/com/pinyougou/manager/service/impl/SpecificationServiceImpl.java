package com.pinyougou.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
 	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	@Override
	public PageResult search(Integer page, Integer size, TbSpecification specifcation) {
		
		//设置分页
		PageHelper.startPage(page, size);
		//查询
		TbSpecificationExample example = new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		if(specifcation.getSpecName() != null){
			criteria.andSpecNameLike("%"+specifcation.getSpecName()+"%");
		}
		Page<TbSpecification> pageResult = (Page<TbSpecification>)specificationMapper.selectByExample(example);
		
		return new PageResult(pageResult.getTotal(), pageResult.getResult());
	}

	@Override
	public void add(SpecificationVo vo) {
		//1、获取规格对象
		TbSpecification specification = vo.getSpecification();
		//2、添加
		specificationMapper.insertSelective(specification);
		//3、获取规格选项集合
		List<TbSpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
		//4、遍历这个集合一个一个添加到规格选项表中
		for (TbSpecificationOption specificationOption : specificationOptionList) {
			specificationOption.setSpecId(specification.getId());
			specificationOptionMapper.insertSelective(specificationOption);
		}
		
	}

	@Override
	public SpecificationVo findOne(Long id) {
		//1、先查询规格
		TbSpecification specification = specificationMapper.selectByPrimaryKey(id);
		//2、查询规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(id);
		List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);
		//3、封装返回对象
		SpecificationVo vo = new SpecificationVo();
		vo.setSpecification(specification);
		vo.setSpecificationOptionList(list);
		
		return vo;
	}

	@Override
	public void update(SpecificationVo vo) {
		//获取规格数据
		TbSpecification specification = vo.getSpecification();
		//1、修改规格表
		specificationMapper.updateByPrimaryKeySelective(specification);
		//2、先删除后添加
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(specification.getId());
		specificationOptionMapper.deleteByExample(example);
		//2、获取规格选项集合数据
		List<TbSpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
		for (TbSpecificationOption specificationOption : specificationOptionList) {
			//设置上规格ID
			specificationOption.setSpecId(specification.getId());
			specificationOptionMapper.insertSelective(specificationOption);
		}
		
	}

}
