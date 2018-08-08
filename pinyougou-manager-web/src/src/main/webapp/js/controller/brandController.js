app.controller('brandController',function($scope,$controller,brandService){
	
	//继承:继承了$scope域
	$controller('baseController',{$scope:$scope});
	
	$scope.findAll=function(){
		brandService.findAll().success(function(response){
			$scope.list = response;
		});
	}
	// 分页查询的方法
	$scope.findPage=function(page,size){
		brandService.findPage(page,size).success(function(response){
			$scope.list = response.rows;
			// 再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}
	// 保存数据的方法
	$scope.save=function(){
		// 什么情况下用post什么情况下get？
		// 当提交对象的时候，对象需要序列化所以用POST，加密！
		var objectName = "";
		if($scope.entity.id != null){
			objectName = brandService.update($scope.entity);
		} else{
			objectName = brandService.add($scope.entity);
		}
		objectName.success(function(response){
			if(response.success){
				// 刷新页面
				$scope.reloadList();
			} else{
				alert(response.message);
			}
		});
	}
	// 根据品牌ID查询品牌对象
	$scope.findOne=function(id){
		brandService.findOne(id).success(function(response){
			$scope.entity = response;
		});
	}
	// 删除
	$scope.dele=function(){
		brandService.dele($scope.selectId).success(function(response){
			if(response.success){
				// 刷新页面
				$scope.reloadList();
				// 清空选择的ID
				$scope.selectId=[];
			} else{
				alert(response.message);
			}
		});
	}
	// 初始化一个空的对象
	$scope.searchEntity={};
	// 搜索
	$scope.search=function(page,size){
		brandService.search(page,size,$scope.searchEntity).success(function(response){
			$scope.list = response.rows;
			// 再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
});