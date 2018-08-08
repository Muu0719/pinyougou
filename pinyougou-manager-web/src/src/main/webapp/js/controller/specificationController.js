app.controller("specificationController",function($scope,$controller,specificationService){
	
	//继承
	$controller("baseController",{$scope:$scope});
	//定义一个空对象
	$scope.searchEntity={};
	$scope.search=function(page,size){
		specificationService.search(page,size,$scope.searchEntity).success(function(response){
			$scope.list = response.rows;
			//给我们的分页总记录数赋值
			$scope.paginationConf.totalItems = response.total;
		});
	}
	//添加行
	$scope.addTableRow=function(){
		$scope.entity.specificationOptionList.push({});
	}
	
	//删除行
	$scope.deleTableRow=function(index){
		$scope.entity.specificationOptionList.splice(index,1);
	}
	
	$scope.save=function(){
		var objectName = "";
		if($scope.entity.specification.id!=null){
			objectName = specificationService.update($scope.entity);
		} else{
			objectName = specificationService.add($scope.entity)
		}
		objectName.success(function(response){
			if(response.success){
				//刷新页面
				$scope.reloadList();
			}else{
				alert(response.message);
			}
		});
	}
	
	$scope.findOne=function(id){
		specificationService.findOne(id).success(function(response){
			$scope.entity = response;
		});
	}
});