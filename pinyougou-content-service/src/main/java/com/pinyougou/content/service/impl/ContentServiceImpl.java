package com.pinyougou.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.PageResult;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import com.pinyougou.pojo.TbContentExample.Criteria;

/**
 * 服务实现层
 * 
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContent content) {
		//每次添加都要清空缓存
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		contentMapper.insert(content);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbContent content) {
		//先根据id查出修改之前的对象信息,
		TbContent old = contentMapper.selectByPrimaryKey(content.getId());
		//先把旧缓存删掉
		redisTemplate.boundHashOps("content").delete(old.getCategoryId());
		
		contentMapper.updateByPrimaryKey(content);
		//判断新的信息category和旧的是否一致,如果不一致那么还需要再删除新的category缓存,保证缓存实时变更
		if(old.getCategoryId().longValue() != content.getCategoryId().longValue()) {
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			//先根据id查询对应的catefgoryId
			TbContent content = contentMapper.selectByPrimaryKey(id);
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
			
			contentMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();

		if (content != null) {
			if (content.getTitle() != null && content.getTitle().length() > 0) {
				criteria.andTitleLike("%" + content.getTitle() + "%");
			}
			if (content.getUrl() != null && content.getUrl().length() > 0) {
				criteria.andUrlLike("%" + content.getUrl() + "%");
			}
			if (content.getPic() != null && content.getPic().length() > 0) {
				criteria.andPicLike("%" + content.getPic() + "%");
			}
			if (content.getStatus() != null && content.getStatus().length() > 0) {
				criteria.andStatusLike("%" + content.getStatus() + "%");
			}

		}

		Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	public List<TbContent> findContentListByCategoryId(Long categoryId) {
		try {
			//先查缓存
			List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
			
			if (null != list && list.size() > 0) {
				//查出来直接返回
				System.out.println("缓存有数据");
				return list;
			}
			//如果没有缓存才去查数据库
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			// 设置分类状态为1 启用的才查出来,
			criteria.andStatusEqualTo("1");
			// 设置排序顺序 默认是升序 如需降序,'DESC'直接写入后边即可
			example.setOrderByClause("sort_order");
			list = contentMapper.selectByExample(example);
			System.out.println("查询了一次数据库");
			//查出来结果了向redis里存一份
			redisTemplate.boundHashOps("content").put(categoryId, list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
