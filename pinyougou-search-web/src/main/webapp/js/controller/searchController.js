app.controller('searchController',function($scope,$controller,serachservice){
	$controller('baseController',{$scope:$scope});
	
	$scope.searchMap = {'keywords':''};
	
	$scope.search = function(){
		serachService.search($scope.searchMap).success(function(response){
			$scope.list = response.rows;
		})
	}
});