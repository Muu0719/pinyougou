app.controller('itemCatController' ,function($scope,$controller ,typeTemplateService  ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  
		
		$scope.entity.parentId = $scope.parentId;
		
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
					$scope.findByParentId($scope.entity.parentId);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					//需要返回当前目录分级的页面
//					$scope.selectList(entity);
					$scope.findByParentId($scope.parentId);
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	$scope.parentId=0;//上级 ID 
	
	$scope.findByParentId=function(parentId){
		
		$scope.parentId = parentId;
		
		itemCatService.findByParentId(parentId).success(function(response){
			$scope.list = response;
		});
	}
	
	//定义一个变量是等级
	$scope.grade = 1;
	//set方法
	$scope.setGrade=function(value){ 
		 $scope.grade=value; 
	 } 
	
	//判断等级的方法
	$scope.selectList=function(p_entity){
		
		if($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if($scope.grade == 2){
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if($scope.grade == 3){
			$scope.entity_2 = p_entity;
		}
		//再根据parentID查询
		$scope.findByParentId(p_entity.id);
	}
	
	//获取模板集合
	$scope.findTypeTemplateList=function(){
		typeTemplateService.findTypeTemplateList().success(function(response){
			$scope.typeTemplateList = {data:response};
		});
	}
	
});	
