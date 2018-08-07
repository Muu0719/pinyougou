package com.pinyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.common.Result;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.pojo.TbBrand;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	
	@RequestMapping("/search")
	public PageResult search(Integer pageNum, Integer pageSize, @RequestBody TbBrand tbBrand) {
		PageResult pageResult = brandService.search(pageNum,pageSize,tbBrand);
		return pageResult;
	}
	
	
	@RequestMapping("/findPage")
	public PageResult findPage(Integer pageNum,Integer pageSize) {
		PageResult pageRueult = brandService.findPage(pageNum, pageSize);
		return pageRueult;
	}
	
	
	@RequestMapping("/deleteBrand")
	public Result deleteBrand(Integer[] ids) {
		try {
			brandService.deleteBrand(ids);
			return new Result(true,"操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"操作失败");
		}
	}
	
	
	
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand tbBrand) {
		try {
			brandService.update(tbBrand);
			return new Result(true,"操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"操作失败");
		}
	}
	
	
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}
	
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand tbBrand) {
		try {
			brandService.add(tbBrand);
			return new Result(true,"操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"操作失败");
		}
	}
}
