app.controller("searchController",function($scope,$controller,searchService){
	
	$controller("baseController",{$scope:$scope});
	
	$scope.searchMap={'keywords':''};
	
	$scope.search=function(){
		searchService.search($scope.searchMap).success(function(response){
			$scope.list = response.rows;
			
		});
	}
	
});