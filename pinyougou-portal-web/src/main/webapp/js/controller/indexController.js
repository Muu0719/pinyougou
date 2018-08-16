app.controller('indexController',function($scope, indexService){
	
	$scope.contentList = [];
	
	$scope.findContentListByCategoryId = function(categoryId){
		indexService.findOne(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		})
	}
});