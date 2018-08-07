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
@RequestMapping("/specification")
public class SpecificationController {
	
	@Reference
	private SpecificationService specificationService;
	
	
	@RequestMapping("/dele")
	public Result dele(Long[] ids) {
		try {
			specificationService.dele(ids);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败!");
		}
	}
	
	
	@RequestMapping("/update")
	public Result update(@RequestBody() SpecificationVo vo) {
		try {
			specificationService.update(vo);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败!");
		}
	}
	
	@RequestMapping("/findOne")
	public SpecificationVo findOne(Long id) {
		return specificationService.findOne(id);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody() SpecificationVo vo) {
		try {
			specificationService.add(vo);
			return new Result(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"添加失败!");
		}
		
	}
	
	
	@RequestMapping("/search")
	public PageResult search(Integer pageNum, Integer pageSize,@RequestBody() TbSpecification tbSpecification) {
		
		PageResult pageResult = null;
		try {
			pageResult = specificationService.search(pageNum,pageSize,tbSpecification);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pageResult;
	}

}
