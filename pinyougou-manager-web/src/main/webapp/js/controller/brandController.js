app.controller('brandController', function($scope, $http,brandService) {
	$scope.findAll = function() {
		$http.get('../brand/findAll').success(function(response) {
			$scope.list = response;
		});
	}
	//初始化分页组件
	//分页控件配置  
	$scope.paginationConf = {
		currentPage : 1, //当前页 
		totalItems : 10, //总记录数
		itemsPerPage : 10, //每页显示的记录数
		perPageOptions : [ 10, 20, 30, 40, 50 ], //分页标签
		onChange : function() { //当分页组件加载的时候，被执行；当分页组件点击了也执行
			//查询数据
			$scope.reloadList();
		}
	};

	//分页查询的方法
	$scope.findPage = function(pageNum, pageSize) {
		brandService.findPage(pageNum,pageSize).success(function(response) {
			$scope.list = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}

	//刷新页面的方法
	$scope.reloadList = function() {
		$scope.search($scope.paginationConf.currentPage,
			$scope.paginationConf.itemsPerPage);
	}

	//保存数据的方法
	$scope.add = function() {
		//什么情况下用post什么情况下get？
		//当提交对象的时候，对象需要序列化所以用POST，加密！
		var objectName = "";
		if ($scope.entity.id != null) {
			objectName = $http.post('../brand/update', $scope.entity);
		} else {
			objectName = $http.post('../brand/add', $scope.entity);
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
		$http.get('../brand/findOne?id=' + id).success(function(response) {
			$scope.entity = response;
		});
	}

	//定义一个数组来存储选择的ID数据
	$scope.selectId = [];
	//点击复选框事件
	$scope.updateSelection = function($event, id) {
		//获取点击DOM对象$event.target;复选框中的属性checked：判断是否选中，选中为true
		if ($event.target.checked) {
			$scope.selectId.push(id);
		} else {
			//查询这个数据坐标
			var index = $scope.selectId.indexOf(id);
			//删除:参数说明：1、删除的坐标；2、删除个数
			$scope.selectId.splice(index, 1);
		}
	}

	//删除
	$scope.deleteBrand = function() {
		$http.get('../brand/deleteBrand?ids=' + $scope.selectId).success(function(response) {
			if (response.success) {
				//刷新页面
				$scope.reloadList();
				//清空选择的ID
				$scope.selectId = [];
			} else {
				alert(response.message);
			}
		});
	}
	//初始化一个空的对象
	$scope.searchEntity = {};
	//搜索
	$scope.search = function(pageNum, pageSize) {
		$http.post('../brand/search?pageNum=' + pageNum + '&pageSize=' + pageSize, $scope.searchEntity).success(function(response) {
			$scope.list = response.list;
			//再修改一下分页组件
			$scope.paginationConf.totalItems = response.total;
		});
	}

});