package com.pinyougou.user.service;
import java.util.List;
import com.pinyougou.pojo.TbAddress;

import com.pinyougou.common.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AddressService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbAddress> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbAddress address);
	
	
	/**
	 * 修改
	 */
	public void update(TbAddress address);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbAddress findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbAddress address, int pageNum,int pageSize);


	
	/** 
	
	 * @Title:        findAddressListByUserId 
	 * @Description:  查询当前登陆用户的收货人信息       
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-27 21:08:07
	 */
	public List<TbAddress> findAddressList(String userId);
	
}
