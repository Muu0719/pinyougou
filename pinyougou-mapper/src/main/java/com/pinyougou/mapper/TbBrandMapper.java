package com.pinyougou.mapper;

import java.util.List;

import com.pinyougou.pojo.TbBrand;

public interface TbBrandMapper {

	List<TbBrand> findAll();

	void add(TbBrand tbBrand);

	TbBrand findOne(Long id);

	void update(TbBrand tbBrand);

	void deleteBrand(Integer id);

	List<TbBrand> search(TbBrand tbBrand);

	
}
