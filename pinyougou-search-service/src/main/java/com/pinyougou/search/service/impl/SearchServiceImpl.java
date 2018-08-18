package com.pinyougou.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Override
	public Map<String, Object> search(Map map) {
		//1、创建搜索的对象
		SimpleQuery query = new SimpleQuery();
		//2、添加条件
		Criteria criteria = null;//没有实例化
		String keywords = (String)map.get("keywords");
		if(keywords != null && !"".equals(keywords)){
			criteria = new Criteria("item_keywords").contains(keywords);//添加条件的时候给他实例化
		} else{
			criteria = new Criteria().expression("*:*"); 
		}
		query.addCriteria(criteria);
		//3、执行查询
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		//4、返回数据
		List<TbItem> list = page.getContent();
		//5、封装返回对象
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("rows", list);
		return resultMap;
	}

}
