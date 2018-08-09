app.service('brandService',function($http){
	
	this.findAll=function(){
		return $http.get('../brand/findAll');
	}
	this.findPage=function(page,size){
		return $http.get('../brand/findPage?page='+page+'&size='+size);
	}
	this.update=function(entity){
		return $http.post('../brand/update',entity);
	}
	this.add=function(entity){
		return $http.post('../brand/add',entity);
	}
	this.findOne=function(id){
		return $http.get('../brand/findOne?id='+id);
	}
	this.dele=function(ids){
		return $http.get('../brand/dele?ids='+ids);
	}
	this.search=function(page,size,searchEntity){
		//调用后台的接口URL
		return $http.post("../brand/search?page="+page+"&size="+size,searchEntity);
	}
	this.findBrandList=function(){
		return $http.get("../brand/findBrandList");
	}
});