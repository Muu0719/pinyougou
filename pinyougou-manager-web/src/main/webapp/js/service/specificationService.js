app.service('specificationService',function($http){
	
	this.add = function(entityVo){
		return $http.post('../specification/add',entityVo);
	}
	
	this.search = function(pageNum,pageSize,searchEntity){
		return $http.post('../specification/search?pageNum=' + pageNum + '&pageSize=' + pageSize, searchEntity);
	}
	
	this.findOne = function(id){
		return $http.get('../specification/findOne?id=' + id);
	}
	
	this.update = function(entityVo){
		return $http.post('../specification/update',entityVo);
	}
	
	this.dele = function(ids){
		return $http.get('../specification/dele?ids=' + ids);
	}
})