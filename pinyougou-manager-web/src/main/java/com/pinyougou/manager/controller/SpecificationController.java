package com.pinyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.manager.service.SpecificationService;
import com.pinyougou.pojo.TbSpecification;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
	
	@Reference
	private SpecificationService specificationService;
	
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
