app.service('brandService',function($http){
	this.findPage = function(pageNum,pageSize){
		return $http.get('../brand/findPage?pageNum=' + pageNum + '&pageSize=' + pageSize);
	}
});