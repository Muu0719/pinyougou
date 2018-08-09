package com.pinyougou.manager.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;

public interface SpecificationService {

	/**
	 * 根据条件分页查询
	 * @param page
	 * @param size
	 * @param specifcation
	 * @return
	 */
	PageResult search(Integer page, Integer size, TbSpecification specifcation);

	/**
	 * 添加规格及规格选项
	 * @param vo
	 */
	void add(SpecificationVo vo);

	/**
	 * 根据规格ID查询规格数据及规格选项数据
	 * @param id
	 * @return
	 */
	SpecificationVo findOne(Long id);

	/**
	 * 修改规格
	 * @param vo
	 */
	void update(SpecificationVo vo);

	/**
	 * 查询规格Map列表
	 * @return
	 */
	List<Map> findSpecList();

}
