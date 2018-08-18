package com.pinyougou.search.service;

import java.util.Map;

public interface SearchService {

	/**
	 * 根据Map条件对象查询Map数据
	 * @param map
	 * @return
	 */
	Map<String, Object> search(Map map);

}
