package com.pinyougou.search.service;

import java.util.Map;

public interface SearchService {

	
	/** 
	
	 * @Title:        search 
	 * @Description:  根据map条件查询
	 * @param:        @param map
	 * @param:        @return    
	 * @return:       Map<String,Object>    
	 * @throws 
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-17 10:08:15
	 */
	Map<String, Object> search(Map map);

	
}
