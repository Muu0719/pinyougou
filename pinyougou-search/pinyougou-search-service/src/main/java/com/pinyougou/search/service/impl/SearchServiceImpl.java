package com.pinyougou.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;

public class SearchServiceImpl implements SearchService {

	@Autowired
	private SolrTemplate solrTemplate;
	
	public Map<String, Object> search(Map map) {
		//1.创建搜索对象
		SimpleQuery query = new SimpleQuery();
		//2.添加搜索条件
		Criteria criteria = null;
		String keywords = (String) map.get("keywords");
		if(null != keywords) {
			criteria = new Criteria("item_keywords").contains(String.valueOf(map.get("keywords")));
		} else {
			criteria = new Criteria().expression("*:*");
			
		}
		query.addCriteria(criteria);
		//3.执行查询
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		//4.返回数据
		List<TbItem> list = page.getContent();
		//5.封装返回对象
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("rows", list);
		return resultMap;
	}

}
