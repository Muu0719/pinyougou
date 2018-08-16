app.service('indexService',function($http){
	this.findOne = function(categoryId){
		return $http.get('../content/findContentListByCategoryId?categoryId=' + categoryId);
	}
});