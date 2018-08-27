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
		"pageSize" : 20
	};

	
	//初始化方法：获取参数；执行查询
	$scope.findAll=function(){
		$scope.searchMap.keywords = $location.search()['keywords'];
		if($scope.searchMap.keywords != null){
			//直接执行搜索
			$scope.search();
		} else{
			$scope.searchMap.keywords = "三星";
			$scope.search();
		}
	}
	
	$scope.search=function(){
		
		$scope.searchMap.page = $scope.paginationConf.currentPage;
		$scope.searchMap.pageSize = $scope.paginationConf.itemsPerPage;
		
		searchService.search($scope.searchMap).success(function(response){
			$scope.resultMap = response;
			//添加分页的标签
//			buildPageLabel();
			$scope.paginationConf.totalItems=response.total;//更新总记录数
			
			
		});
	}
	
	//过滤条件查询
	$scope.addFilterCondition=function(key,value){
		if("category" == key || "brand" == key || "price" == key){
			$scope.searchMap[key] = value;
		} else{
			$scope.searchMap.spec[key] = value;
		}
		//执行查询
		$scope.search();
	}
	
	//取消面包屑的数据
	$scope.removeSearchItem=function(key){
		if("category" == key || "brand" == key || "price" == key){
			$scope.searchMap[key] = '';
		} else{
			delete $scope.searchMap.spec[key];
		}
		//执行查询
		$scope.search();
	}
	
	//排序设置
	$scope.sortSearch=function(sortField,sort){
		//设置searchMap中的域
		$scope.searchMap.sort = sort;
		$scope.searchMap.sortField = sortField;
		//执行查询
		$scope.search();
	}
	
	//构建分页标签(totalPages为总页数)
	buildPageLabel=function(){
		$scope.pageLabel=[];//新增分页栏属性		
		var maxPageNo= $scope.resultMap.pages;//得到最后页码
		var firstPage=1;//开始页码
		var lastPage=maxPageNo;//截止页码		
		$scope.firstDot=true;//前面有点
		$scope.lastDot=true;//后边有点	

		if(maxPageNo > 5){  //如果总页数大于5页,显示部分页码		
			if($scope.searchMap.page<=3){//如果当前页小于等于3
				lastPage=5; //前5页
				$scope.firstDot = false;
			}else if( $scope.searchMap.page >= lastPage-2  ){//如果当前页大于等于最大页码-2
				firstPage= maxPageNo -4;		 //后5页	
				$scope.lastDot = false;
			}else{ //显示当前页为中心的5页
				firstPage=$scope.searchMap.page-2;
				lastPage=$scope.searchMap.page+2;			
			}
		}	
		//循环产生页码标签			33,34,35,36,37	
		for(var i=firstPage;i<=lastPage;i++){
			$scope.pageLabel.push(i);				
		}		
	}

	$scope.searchPage=function(page){
		//页码验证
		if(page<1 || page>$scope.resultMap.pages){
			return;
		}
		
		$scope.searchMap.page = page;
		//执行查询
		$scope.search();
	}
	
	//验证当前页码
	$scope.isPage=function(page){
		if(page == $scope.searchMap.page){
			return true;
		}
		return false;
	}
	
	//判断是否是上一页和下一页显示
	$scope.isTopPage=function(){
		if($scope.searchMap.page == 1){
			return false;
		}
		return true;
	}
	$scope.isEndPage=function(){
		if($scope.searchMap.page == $scope.resultMap.pages){
			return false;
		}
		return true;
	}
	
});