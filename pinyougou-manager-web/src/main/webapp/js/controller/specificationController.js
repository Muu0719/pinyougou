app.controller('specificationController',function($scope,$controller,specificationService){
	
	$controller('baseController',{$scope:$scope});
	
	$scope.searchEntity = {};
	$scope.search = function(pageNum,pageSize){
		specificationService.search(pageNum,pageSize,$scope.searchEntity).success(function(response){
			$scope.entity = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		})
	};
})