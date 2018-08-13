 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService,uploadService,typeTemplateService,goodsService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
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
					//
		        	alert(response.message);
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
	}
    
	//初始化，查询商品一级类目数据
	$scope.findItemCat1List=function(){
		itemCatService.findByParentId('0').success(function(response){
			$scope.itemCat1List = response;
			$scope.itemCat2List = {};
			$scope.itemCat3List = {};
		});
	}
	//监控事件
	//两个参数：1、监控的变量；2、执行的方法;参数：1、变动的新值；2、之前的值
	$scope.$watch("entity.goods.category1Id",function(newValue, oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List = response;
			$scope.itemCat3List = {};
		});
	});
	//监控的二级分类变量
	$scope.$watch("entity.goods.category2Id",function(newValue, oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat3List = response;
		});
	});
	
	//监控的三级分类变量
	$scope.$watch("entity.goods.category3Id",function(newValue, oldValue){
		itemCatService.findOne(newValue).success(function(response){
			$scope.entity.goods.typeTemplateId = response.typeId;
		});
	});
	//监听模板ID变动的时候
	$scope.$watch("entity.goods.typeTemplateId",function(newValue, oldValue){
		//根据模板的Service查询模板对象
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate = response;
			$scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
			$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
		});
		typeTemplateService.findSpecList(newValue).success(function(response){
			$scope.sepcList = response;
		});
		
	});
	
	//上传图片的方法
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){
				$scope.entity_image.url = response.message;
			} else{
				alert(response.message);
			}
		});
	}
	
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
	
	//把上传的图片对象保存在描述itemImages数组中
	$scope.addImageToList=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.entity_image);
	}
	//从图片数组中删除
	$scope.deleImageToList=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
	
	//点击规格选项，选择规格的
		$scope.updateSpecAttribute=function($event,name,value){
			
			//判断你选择的这个name,value 是否在集合中存在，如果集合中存在你要添加的name那么就返回对象
			var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,
					"attributeName",name);
			//用对象的attributeValue来push
			if(object != null){
				//判断是否选中
				if($event.target.checked){
					object.attributeValue.push(value);
				} else{
					//没选中那么就删除attributeValue的值
					object.attributeValue.splice(object.attributeValue.indexOf(value),1);
					//删除后你判断：attributeValue是否有值；如果没值了，对象删除
					if(object.attributeValue.length == 0){
						$scope.entity.goodsDesc.specificationItems.splice(
								$scope.entity.goodsDesc.specificationItems.indexOf(object),1);
					}
				}
				
			} else{
				$scope.entity.goodsDesc.specificationItems.push(
						{"attributeName":name,"attributeValue":[value]});
			}
			
		}
		//来判断选中的name这个Key是否在集合中存在
		$scope.searchObjectByKey=function(list,key,value){
			for(var i = 0; i < list.length; i++){
				if(list[i][key] == value){
					return list[i];
				}
			}
			return null;
		}
		
	
	//创建 SKU 列表 
	$scope.createItemList=function(){  
		 $scope.entity.itemList = [{spec:{},price:0,num:99999,status:'0',isDefault:'0' }];//初始 
		 var items= $scope.entity.goodsDesc.specificationItems; 
		 for(var i=0;i< items.length;i++){ 
			 $scope.entity.itemList = 
				 addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue);     
		 } 
	} 
	
	//添加列值  					"网络"		["移动3G","移动4G"]
	addColumn=function(list,columnName,conlumnValues){ 
		 var newList=[];//新的集合 
		 for(var i=0;i<list.length;i++){ 
			 var oldRow= list[i]; 
			 for(var j=0;j<conlumnValues.length;j++){ 
				 var newRow= JSON.parse( JSON.stringify( oldRow ) );//深克隆 
				 newRow.spec[columnName]=conlumnValues[j]; 
				 //newRow={spec:{"网络":"移动3G"},price:0,num:99999,status:'0',isDefault:'0' }
				 //newRow={spec:{"网络":"移动4G"},price:0,num:99999,status:'0',isDefault:'0' }
				 newList.push(newRow); 
			 } 
		 } 
	 return newList; 
	 //[{spec:{"网络":"移动3G"},price:0,num:99999,status:'0',isDefault:'0' },{spec:{"网络":"移动4G"},price:0,num:99999,status:'0',isDefault:'0' }]
	} 
	
});	
