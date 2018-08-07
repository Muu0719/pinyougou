package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.List;

public class SpecificationVo implements Serializable{
	
	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionList;
	
	
	public TbSpecification getSpecification() {
		return specification;
	}
	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}
	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}
	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
	@Override
	public String toString() {
		return "SpecificationVo [specification=" + specification + ", specificationOptionList="
				+ specificationOptionList + "]";
	}
	public SpecificationVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpecificationVo(TbSpecification specification, List<TbSpecificationOption> specificationOptionList) {
		super();
		this.specification = specification;
		this.specificationOptionList = specificationOptionList;
	}

	
}
