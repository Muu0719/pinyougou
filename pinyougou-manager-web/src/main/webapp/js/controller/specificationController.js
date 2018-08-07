app.controller('specificationController',function($scope,$controller,specificationService){
	
	$controller('baseController',{$scope:$scope});
	
	//删除
		$scope.dele = function() {
			specificationService.dele($scope.selectIds).success(function(response) {
				if (response.success) {
					//刷新页面
					$scope.reloadList();
					//清空选择的ID
					$scope.selectIds = [];
				} else {
					alert(response.message);
				}
			});
		}

	//根据id查询
	$scope.findOne = function(id){
		specificationService.findOne(id).success(function(response){
			$scope.entityVo = response;
		})
	};
	
	//添加规格和对应选项
	$scope.add = function(){
		var objectResult = "";
		if(null != $scope.entityVo.specification.id){
			objectResult = specificationService.update($scope.entityVo);
		} else {
			objectResult = specificationService.add($scope.entityVo);
		}
		objectResult.success(function(response){
			if(response.success){
				$scope.reloadList();
			} else {
				alert(response.message);
			}
		})
	};
	
	//删除规格选项行
	$scope.deleTableRow = function(index){
		$scope.entityVo.specificationOptionList.splice(index,1);
	};
	
	//添加规格选项行
	$scope.addTableRow = function(){
		$scope.entityVo.specificationOptionList.push({});
	};
	
	
	$scope.searchEntity = {};
	$scope.search = function(pageNum,pageSize){
		specificationService.search(pageNum,pageSize,$scope.searchEntity).success(function(response){
			$scope.entity = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		})
	};
})