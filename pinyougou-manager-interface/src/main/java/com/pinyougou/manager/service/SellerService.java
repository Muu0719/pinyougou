package com.pinyougou.manager.service;
import java.util.List;
import com.pinyougou.pojo.TbSeller;

import com.pinyougou.common.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SellerService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeller> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeller seller);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeller seller);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeller findOne(String id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(String [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbSeller seller, int pageNum,int pageSize);


	
	/** 
	
	 * @Title:        updateStatus 
	 * @Description:  修改商户申请状态
	 * @param:        @param sellerId
	 * @param:        @param status    
	 * @return:       void    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-10 21:08:44
	 */
	public void updateStatus(String sellerId, String status);
	
}
