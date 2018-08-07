app.service('specificationService',function($http){
	
	this.search = function(pageNum,pageSize,searchEntity){
		return $http.post('../specification/search?pageNum=' + pageNum + '&pageSize=' + pageSize, searchEntity);
	}
})