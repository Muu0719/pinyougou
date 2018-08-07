package com.pinyougou.manager.service;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.TbSpecification;

public interface SpecificationService {

	
	/** 
	
	 * @Title:        search 
	 * @Description:  分页查询列表
	 * @param:        @param pageNum
	 * @param:        @param pageSize
	 * @param:        @param tbSpecification
	 * @param:        @return    
	 * @return:       PageResult     
	 * @Date          2018-08-07 17:08:26
	 */
	PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification);

}
