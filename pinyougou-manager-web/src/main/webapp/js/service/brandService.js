app.service('brandService', function($http) {

	this.deleteBrand = function(selectIds){
		return $http.get('../brand/deleteBrand?ids=' + selectIds);
	}
	
	this.findOne = function(id) {
		return $http.get('../brand/findOne?id=' + id);
	}

	this.add = function(entity) {
		return $http.post('../brand/add', entity);
	}

	this.update = function(entity) {
		return $http.post('../brand/update', entity);
	}

	this.findPage = function(pageNum, pageSize) {
		return $http.get('../brand/findPage?pageNum=' + pageNum + '&pageSize=' + pageSize);
	}

	this.findAll = function() {
		return $http.get('../brand/findAll');
	}

	this.search = function(pageNum, pageSize, searchEntity) {
		return $http.post('../brand/search?pageNum=' + pageNum + '&pageSize=' + pageSize, searchEntity);
	}
});