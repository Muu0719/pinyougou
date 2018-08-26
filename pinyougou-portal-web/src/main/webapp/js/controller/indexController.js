app.controller('indexController',function($scope, indexService){
	
	$scope.contentList = [];
	
	$scope.findContentListByCategoryId = function(categoryId){
		indexService.findOne(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		})
	}
	
	
	$scope.searchSubmit = function(){
		var keywords =  $scope.keywords;
//		页面之间传递参数,不能用: '?' 需要使用:  '#?'
		location.href="http://search.pinyougou.com/search.html#?keywords=" + keywords;
	}
});