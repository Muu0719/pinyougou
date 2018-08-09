package com.pinyougou.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.pinyougou.pojo.TbBrand;

public interface TbBrandMapper {
	
	@Select("select id,name,first_char as firstChar from tb_brand")
	List<TbBrand> findAll();

	void add(TbBrand brand);

	TbBrand findOne(Long id);

	void update(TbBrand brand);

	void dele(Long id);

	Page<TbBrand> search(TbBrand brand);

	List<Map> findBrandList();

}
