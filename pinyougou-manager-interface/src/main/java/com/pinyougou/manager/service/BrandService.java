package com.pinyougou.manager.service;

import java.util.List;

import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.TbBrand;

public interface BrandService {

	/** 
	
	 * @Title:        findAll 
	 * @Description:  查询所有品牌
	 * @param:        @return    
	 * @return:       List<TbBrand>    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-04 15:08:28
	 */
	List<TbBrand> findAll();

	/** 
	
	 * @Title:        findPage 
	 * @Description:  分页查询品牌
	 * @param:        @param page
	 * @param:        @param size
	 * @param:        @return    
	 * @return:       PageResult    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-04 21:08:11
	 */
	PageResult findPage(Integer pageNum,Integer pageSize);

	
	/** 
	
	 * @Title:        add 
	 * @Description:  添加新品牌
	 * @param:        @param tbBrand    
	 * @return:       void     
	 * @Date          2018-08-05 21:08:00
	 */
	void add(TbBrand tbBrand);

	/** 
	
	 * @Title:        findOne 
	 * @Description:  根据品牌-Id查询品牌信息
	 * @param:        @param id
	 * @param:        @return    
	 * @return:       TbBrand     
	 * @Date          2018-08-05 22:08:10
	 */
	TbBrand findOne(Long id);

	
	/** 
	
	 * @Title:        update 
	 * @Description:  修改品牌-信息
	 * @param:        @param tbBrand    
	 * @return:       void     
	 * @Date          2018-08-05 22:08:35
	 */
	void update(TbBrand tbBrand);

	
	/** 
	
	 * @Title:        deleteBrand 
	 * @Description:  删除品牌-1orN
	 * @param:        @param selectIds    
	 * @return:       void     
	 * @Date          2018-08-05 23:08:34
	 */
	void deleteBrand(Integer[] selectIds);

	
	/** 
	
	 * @Title:        search 
	 * @Description:  条件搜索查询(本模块替代findPage了)
	 * @param:        @param pageNum
	 * @param:        @param pageSize
	 * @param:        @param tbBrand
	 * @param:        @return    
	 * @return:       PageResult     
	 * @Date          2018-08-06 08:08:52
	 */
	PageResult search(Integer pageNum, Integer pageSize, TbBrand tbBrand);
}
