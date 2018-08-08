package com.pinyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.common.Result;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.pojo.SpecificationVo;
import com.pinyougou.pojo.TbSpecification;

@RestController
@RequestMapping("specification")
public class SpecificationController {

	@Reference
	private SpecificationService specificationService;
	
	@RequestMapping("search")
	public PageResult search(Integer page, Integer size, 
			@RequestBody TbSpecification specifcation){
		PageResult result = specificationService.search(page,size,specifcation);
		return result;
	}
	
	@RequestMapping("add")
	public Result add(@RequestBody SpecificationVo vo){
		try {
			specificationService.add(vo);
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
	
	@RequestMapping("findOne")
	public SpecificationVo findOne(Long id){
		return specificationService.findOne(id);
	}
	
	@RequestMapping("update")
	public Result update(@RequestBody SpecificationVo vo){
		try {
			specificationService.update(vo);
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
}
