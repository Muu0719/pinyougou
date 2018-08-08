package com.pinyougou.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.PageResult;
import com.pinyougou.common.Result;
import com.pinyougou.manager.service.BrandService;
import com.pinyougou.pojo.TbBrand;

@RestController
@RequestMapping("brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	//http://localhost:8081/brand/findAll
	@RequestMapping("findAll")
	public  List<TbBrand> findAll(){
		//通过Service来获取数据
		
		List<TbBrand> list = brandService.findAll();
		
		return list;
	}
	
	@RequestMapping("findPage")
	public PageResult findPage(Integer page, Integer size){
		PageResult result = brandService.findPage(page, size);
		return result;
	}
	
	@RequestMapping("add")
	public Result add(@RequestBody TbBrand brand){
		try {
			brandService.add(brand);
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
	
	@RequestMapping("findOne")
	public TbBrand findOne(Long id){
		TbBrand brand = brandService.findOne(id);
		return brand;
	}
	
	@RequestMapping("update")
	public Result update(@RequestBody TbBrand brand){
		try {
			brandService.update(brand);
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
	
	@RequestMapping("dele")
	public Result dele(Long[] ids){
		try {
			brandService.dele(ids);
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
	
	@RequestMapping("search")
	public PageResult search(Integer page, Integer size, @RequestBody TbBrand brand){
		return brandService.search(page,size,brand);
	}
	
}
