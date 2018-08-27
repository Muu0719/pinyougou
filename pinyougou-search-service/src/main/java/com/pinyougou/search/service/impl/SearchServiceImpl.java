package com.pinyougou.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

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
//		SimpleQuery query = new SimpleQuery();
		SimpleHighlightQuery query = new SimpleHighlightQuery();
		
		//2.1添加条件
		Criteria criteria = null;//没有实例化
		String keywords = (String)map.get("keywords");
		if(keywords != null && !"".equals(keywords)){
			criteria = new Criteria("item_keywords").contains(keywords);//添加条件的时候给他实例化
		} else{
			criteria = new Criteria().expression("*:*"); 
		}
		query.addCriteria(criteria);
		
		//2.2设置高亮的域，前后缀
		HighlightOptions options = new HighlightOptions();
		options.addField("item_title");
		options.setSimplePrefix("<span style=\"color:red\">");
		options.setSimplePostfix("</span>");
		query.setHighlightOptions(options);
		
		//2.3 添加分类过滤条件
//		String category = (String)map.get("category");
//		if(category != null && !"".equals(category)){
//			//添加分类的过滤
//			SimpleFilterQuery filterQuery = new SimpleFilterQuery();
//			Criteria contains = new Criteria("item_category").contains(category);
//			filterQuery.addCriteria(contains);
//			query.addFilterQuery(filterQuery);
//		}
		//2.3添加分类的过滤条件
		String category = (String)map.get("category");
		if(null != category && category.trim().length() > 0) {
			//添加分类过滤条件,这里的代码好像不能简化,每次都要根据不同的查询条件new一个Criteria.
			Criteria filterCriteria  = new Criteria("item_category").contains(category);
			SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria );
			query.addFilterQuery(filterQuery);
		}
		//2.4添加品牌过滤
		String brand = (String)map.get("brand");
		if(null != brand && brand.trim().length() > 0) {
			Criteria filterCriteria = new Criteria("item_brand").contains(brand);
			SimpleFilterQuery filterQuery = new SimpleFilterQuery(criteria);
			query.addFilterQuery(filterQuery);
		}
		
		//2,5添加价格过滤条件
		String price = (String)map.get("price");
		if(null != price && price.trim().length() > 0) {
			String[] split = price.split("-");
			if(null != split) {
				if(null != split[0] && !"0".equals(split[0])) {
					Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(split[0]);
					SimpleFilterQuery filterQuery = new SimpleFilterQuery(criteria);
					query.addFilterQuery(filterQuery);
				}
				if(null != split[1] && !"*".equals(split[1])) {
					Criteria filterCriteria = new Criteria("item_price").lessThanEqual(split[1]);
					SimpleFilterQuery filterQuery = new SimpleFilterQuery(criteria);
					query.addFilterQuery(filterQuery);
				}
			}
		
		}
//		2.6 添加规格的过滤条件	{"网络":"移动3G","机身内存"："16G"}
//		Map.put("网络","移动3G");Map.put("机身内存","16G");key 就是属性名字,value就是对应的值
		Map<String,String> specMap = (Map<String,String>)map.get("spec");
		Set<String> keySet = specMap.keySet();
		for (String key : keySet) {
			Criteria filterCriteria = new Criteria("item_spec_" + key).contains(specMap.get(key));
			SimpleFilterQuery filterQuery = new SimpleFilterQuery(criteria);
			query.addFilterQuery(filterQuery);
		}
//		2.7 添加排序条件
		String sort = (String)map.get("sort");
		String sortField = (String)map.get("sortField");
		if(sort != null && !"".equals(sort)){
			if("ASC".equals(sort)){
				//1、排序规则；2、排序的域
				Sort s = new Sort(Sort.Direction.ASC, sortField);
				query.addSort(s);
				
			} 
			if("DESC".equals(sort)){
				//1、排序规则；2、排序的域
				Sort s = new Sort(Sort.Direction.DESC, sortField);
				query.addSort(s);
			}
		}
		//2.8 添加分页的数据
		Integer page = (Integer) map.get("page");
		if(page == null){
			page = 1;
		}
		Integer pageSize = (Integer)map.get("pageSize");
		if(pageSize == null){
			pageSize = 20;
		}
		//添加
		query.setOffset((page - 1) * pageSize);//分页起始索引
		query.setRows(pageSize);//每页显示记录数
		
//		3、执行查询
		HighlightPage<TbItem> resultPage = solrTemplate.queryForHighlightPage(query, TbItem.class); 
		//SpringDataSolr中获取高亮数据的方法
		for(HighlightEntry<TbItem> h: resultPage.getHighlighted()){//循环高亮入口集合
			TbItem item = h.getEntity();//获取原实体类	
			if(h.getHighlights().size()>0 && h.getHighlights().get(0).getSnipplets().size()>0){
				item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
			}	
		}
		
		//4、返回数据
		List<TbItem> list = resultPage.getContent();
		long total = resultPage.getTotalElements();
		int pages = resultPage.getTotalPages();
		//5、封装返回对象
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("rows", list);
		resultMap.put("total", total);
		resultMap.put("pages", pages);
		
		return resultMap;
	}
	
//		//1、创建搜索的对象
////		SimpleQuery query = new SimpleQuery();
//		SimpleHighlightQuery query = new SimpleHighlightQuery();
//		
//		//2.1添加条件
//		Criteria criteria = null;//没有实例化
//		String keywords = (String)map.get("keywords");
//		if(keywords != null && !"".equals(keywords)){
//			criteria = new Criteria("item_keywords").contains(keywords);//添加条件的时候给他实例化
//		} else{
//			criteria = new Criteria().expression("*:*"); 
//		}
//		query.addCriteria(criteria);
//		
//		//2.2设置高亮的域，前后缀
//		HighlightOptions options = new HighlightOptions();
//		options.addField("item_title");
//		options.setSimplePrefix("<span style=\"color:red\">");
//		options.setSimplePostfix("</span>");
//		query.setHighlightOptions(options);
//		HighlightPage<TbItem> resultPage = solrTemplate.queryForHighlightPage(query, TbItem.class); 
//		//SpringDataSolr中获取高亮数据的方法
//		for(HighlightEntry<TbItem> h: resultPage.getHighlighted()){//循环高亮入口集合
//			TbItem item = h.getEntity();//获取原实体类			
//			if(h.getHighlights().size()>0 && h.getHighlights().get(0).getSnipplets().size()>0){
//				item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
//			}	
//		}
//		
//		//4、返回数据
//		List<TbItem> list = resultPage.getContent();
//		//4、返回数据
//		//5、封装返回对象
//		Map<String,Object> resultMap = new HashMap<>();
//		resultMap.put("rows", list);
//		return resultMap;
//	}

}
