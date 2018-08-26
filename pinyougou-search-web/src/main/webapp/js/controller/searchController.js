app.controller("searchController",function($scope,$controller,$location,searchService){
	
	$controller("baseController",{$scope:$scope});
	
	
	// 定义封装搜索参数对象，封装所有参与搜索参数
		// 1,主查询条件 （关键词搜索条件）
		// 2,分类查询参数
		// 3，品牌参数
		// 4,规格属性参数
		// 5,价格参数
		// 6,排序
		// 7,分页

	$scope.searchMap = {
			"keywords" : "",
			"category" : "",
			"brand" : "",
			"spec" : {},
			"price" : "",
			"sort" : "ASC",
			"sortField" : "price",
			"page" : 1,
			"pageSize" : 30
		};

	
	$scope.search=function(){
		searchService.search($scope.searchMap).success(function(response){
			$scope.list = response.rows;
			
		});
	}
	
	$scope.findAll = function(){
		$scope.searchMap.keywords = $location.search()['keywords'];
		if($scope.searchMap.keywords == null){
			$scope.searchMap.keywords = "阿尔卡特";
		}
		$scope.search();
	};
	
	
	$scope.addFilterCondition = function(key, value){
		if('category' == key || 'brand' == key || 'price' == key){
			$scope.searchMap[key] = value;
		} else {
			$scope.searchMap.spec[key] = value;
		}
		$scope.search();
	}
	
});