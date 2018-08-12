 //控制层 
app.controller('goodsController' ,function($scope,$controller, itemCatService,
		typeTemplateService, goodsService,uploadService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页查询
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//通过ID查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		//获取文本域中的数据赋值给entity.goodsDesc.introduction
		$scope.entity.goodsDesc.introduction = editor.html();
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
					alert("添加成功!");
		        	$scope.reloadList();//重新加载
		        	//清空entity对象
		        	$scope.entity={};
		        	editor.html('');
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	
	$scope.selectItemCat1List = function(){
		itemCatService.findByParentId('0').success(function(response){
			$scope.itemCat1List = response;
			$scope.itemCat2List = {};
			$scope.itemCat3List = {};
		})
	};
	
	$scope.$watch('entity.goods.category1Id',function(newValue, oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List = response;
			$scope.itemCat3List = {};
		})
	});
	
	$scope.$watch('entity.goods.category2Id',function(newValue, oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat3List = response;
		})
	});
	
	$scope.$watch('entity.goods.category3Id',function(newValue, oldValue){
		itemCatService.findOne(newValue).success(function(response){
			$scope.entity.goods.typeTemplateId = response.typeId;
		})
	});
	
	$scope.$watch('entity.goods.typeTemplateId',function(newValue,oldValue){
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate = response;
			$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
			$scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
		})
	})
	
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){
				$scope.entity_image.url = response.message;
			}else {
				alert(response.message);
			}
		})
	};
	
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
	
	//把上传的图片对象保存在描述itemImages数组中
	$scope.addImageToList=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.entity_image);
	}
	//从图片数组中删除
	$scope.deleImageToList=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
});	
