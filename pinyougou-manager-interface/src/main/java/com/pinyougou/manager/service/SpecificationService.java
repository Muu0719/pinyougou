package com.pinyougou.manager.service;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;

public interface SpecificationService {

	
	/** 
	
	 * @Title:        search 
	 * @Description:  分页查询规格列表
	 * @param:        @param pageNum
	 * @param:        @param pageSize
	 * @param:        @param tbSpecification
	 * @param:        @return    
	 * @return:       PageResult     
	 * @Date          2018-08-07 17:08:26
	 */
	PageResult search(Integer pageNum, Integer pageSize, TbSpecification tbSpecification);

	/** 
	
	 * @Title:        add 
	 * @Description:  添加规格和规格对应的规格选项
	 * @param:        @param vo    
	 * @return:       void     
	 * @Date          2018-08-07 20:08:50
	 */
	void add(SpecificationVo vo);

	
	/** 
	
	 * @Title:        findOne 
	 * @Description:  根据规格ID查询 规格对应的的Vo类
	 * @param:        @param id
	 * @param:        @return    
	 * @return:       SpecificationVo     
	 * @Date          2018-08-07 21:08:51
	 */
	SpecificationVo findOne(Long id);

	
	/** 
	
	 * @Title:        update 
	 * @Description:  修改规格和规格对应的规格选项
	 * @param:        @param vo    
	 * @return:       void     
	 * @Date          2018-08-07 22:08:57
	 */
	void update(SpecificationVo vo);

	
	/** 
	
	 * @Title:        dele 
	 * @Description:  删除规格信息
	 * @param:        @param ids    
	 * @return:       void     
	 * @Date          2018-08-08 00:08:54
	 */
	void dele(Long[] ids);

}
