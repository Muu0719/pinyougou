app.controller('brandController', function($scope,$controller, brandService) {
	//继承:继承了$scope域
	$controller('baseController',{$scope:$scope});
	
	$scope.findAll = function() {
		brandService.findAll().success(function(response) {
			$scope.list = response;
		});
	}
	
	//分页查询的方法
	$scope.findPage = function(pageNum, pageSize) {
		brandService.findPage(pageNum,pageSize).success(function(response) {
			$scope.list = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
	//保存数据的方法
	$scope.add = function() {
		//什么情况下用post什么情况下get？
		//当提交对象的时候，对象需要序列化所以用POST，加密！
		var objectName = "";
		if ($scope.entity.id != null) {
			objectName = brandService.update($scope.entity);
		} else {
			objectName = brandService.add($scope.entity);
		}
		objectName.success(function(response) {
			if (response.success) {
				//刷新页面
				$scope.reloadList();
			} else {
				alert(response.message);
			}
		});
	}
	//根据品牌ID查询品牌对象
	$scope.findOne = function(id) {
		brandService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}
	
	//删除
	$scope.deleteBrand = function() {
		brandService.deleteBrand($scope.selectIds).success(function(response) {
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
	//初始化一个空的对象
	$scope.searchEntity = {};
	//搜索
	$scope.search = function(pageNum, pageSize) {
		brandService.search(pageNum,pageSize,$scope.searchEntity).success(function(response) {
			$scope.list = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}
});