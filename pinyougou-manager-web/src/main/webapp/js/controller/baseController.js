app.controller("baseController",function($scope){
	
	// 初始化分页组件
	// 分页控件配置
	$scope.paginationConf = { 
		 currentPage: 1,// 当前页
		 totalItems: 10, // 总记录数
		 itemsPerPage: 10, // 每页显示的记录数
		 perPageOptions: [10, 20, 30, 40, 50], // 分页标签
		 onChange: function(){ // 当分页组件加载的时候，被执行；当分页组件点击了也执行
		 	// 查询数据
			$scope.reloadList();
		 } 
	}; 
	
	// 刷新页面的方法
	$scope.reloadList=function(){
		$scope.search($scope.paginationConf.currentPage,
				 $scope.paginationConf.itemsPerPage);
	}
	
	// 定义一个数组来存储选择的ID数据
	$scope.selectIds=[];
	// 点击复选框事件
	$scope.updateSelection=function($event,id){
		// 获取点击DOM对象$event.target;复选框中的属性checked：判断是否选中，选中为true
		if($event.target.checked){
			$scope.selectIds.push(id);
		} else{
			// 查询这个数据坐标
			var index = $scope.selectIds.indexOf(id);
			// 删除:参数说明：1、删除的坐标；2、删除个数
			$scope.selectIds.splice(index,1);
		}
	}
	
	//定义一个方法；根据某属性名来获取JSON字符串的数据的
	$scope.selectJSON=function(json,key){
		//把JSON字符串转成对象
		var list = JSON.parse(json);
		var strText = "";
		//遍历这个集合对象，获取key的值
		for(var i = 0; i < list.length; i++){
			if(i != 0){
				strText += ",";
			}
			strText += list[i][key];
		}
		return strText;
	}
	
});