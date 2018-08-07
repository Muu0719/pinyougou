package com.pinyougou.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	public PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification) {
		PageHelper.startPage(pageNum, pageSize);
		TbSpecificationExample example = new TbSpecificationExample();
		if (null != tbSpecification.getSpecName() && tbSpecification.getSpecName().length() > 0) {
			Criteria criteria = example.createCriteria();
			criteria.andSpecNameLike("%" + tbSpecification.getSpecName() + "%");
		}
		Page<TbSpecification> pageResult = (Page<TbSpecification>) specificationMapper.selectByExample(example);
		return new PageResult(pageResult.getTotal(), pageResult.getResult());
	}

	public void add(SpecificationVo vo) {
		// 获取规格
		TbSpecification specification = vo.getSpecification();
		// 添加,并获取对应的id
		specificationMapper.insertSelective(specification);
		// 获取规格选项集合
		List<TbSpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
		if (null != specificationOptionList && specificationOptionList.size() > 0) {
			// 遍历添加数据
			for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
				// 先赋值id再添加
				tbSpecificationOption.setSpecId(specification.getId());
				specificationOptionMapper.insertSelective(tbSpecificationOption);
			}
		}
	}

	public SpecificationVo findOne(Long id) {
		TbSpecification specification = specificationMapper.selectByPrimaryKey(id);
		
		//根据id查询所欧规格的选项
		TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
		
		optionExample.createCriteria().andSpecIdEqualTo(id);
		
		List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(optionExample);
		
		//封装返回的对象
		SpecificationVo vo = new SpecificationVo();
		
		vo.setSpecification(specification);
		vo.setSpecificationOptionList(specificationOptionList);
		return vo;
	}

	
	public void update(SpecificationVo vo) {
		TbSpecification specification = vo.getSpecification();
		//修改规格信息
		specificationMapper.updateByPrimaryKeySelective(specification);
		//先删除对应规格选项在添加新的修改.
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		//设置删除条件
		example.createCriteria().andSpecIdEqualTo(specification.getId());
		specificationOptionMapper.deleteByExample(example);
		//遍历添加规格选项
		List<TbSpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
		if(null != specificationOptionList && specificationOptionList.size() > 0 ) {
			
			for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
				tbSpecificationOption.setSpecId(specification.getId());
				specificationOptionMapper.insertSelective(tbSpecificationOption);
			}
		}
		
	}

	
	public void dele(Long[] ids) {
		if(null != ids && ids.length > 0) {
			for (Long id : ids) {
				//删除规格,因为两者没有关联关系 所以,删除顺序无所谓了...
				specificationMapper.deleteByPrimaryKey(id);
				//删除规格对应的选项
				TbSpecificationOptionExample example = new TbSpecificationOptionExample();
				//设置删除条件
				example.createCriteria().andSpecIdEqualTo(id);
				specificationOptionMapper.deleteByExample(example);
			}
		}
	}

}
