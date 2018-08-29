app.controller("cartController",function($scope,cartService,addressService,orderService){
	
	$scope.findCartList=function(){
		cartService.findCartList().success(function(response){
			$scope.cartList = response;
			//查询列表的时候计算总价格
			$scope.totalValue = cartService.sum($scope.cartList);
		});
	}
	
	//点击+/-商品修改
	$scope.addGoodsToCartList=function(itemId,num){
		cartService.addGoodsToCartList(itemId,num).success(function(response){
			if(response.success){
				//刷新页面
				$scope.findCartList();
			} else{
				alert(response.message);
			}
		});
	}
	
	//结算页面中初始化方法
	$scope.findAddressList=function(){
		addressService.findAddressList().success(function(response){
			$scope.addressList = response;
			//把默认的收货人对象赋值给$scope.address
			for(var i = 0; i < $scope.addressList.length; i++){
				if($scope.addressList[i].isDefault == '1'){
					$scope.address = $scope.addressList[i];
				}
			}
		});
	}
	
	//点击收货人信息：保存选中的收货人对象
	$scope.selectAddress=function(address){
		$scope.address = address;
	}
	
	//判断你选择的收货人对象是否和列表遍历出来的一致：如果一致，返回true，否则返回false
	$scope.isSelectAddress=function(address){
		if($scope.address == address){
			return true;
		}
		return false;
	}
	
	//订单以一个变量：$scope.entity.paymentType
	$scope.order={paymentType:'1'}
	
	$scope.selectPaymenType=function(status){
		$scope.entity.paymentType = status;
	}
	
	//提交订单
	$scope.saveOrder=function(){
		
		//把刚才选中的Address赋值给order对象
		$scope.order.receiverAreaName = $scope.address.address;
		$scope.order.receiverMobile = $scope.address.mobile;
		$scope.order.receiver = $scope.address.contact;
		
		orderService.add($scope.order).success(function(response){
			if(response.success){
				//跳转到支付页面
				location.href="/pay.html";
			} else{
				alert(response.message);
			}
		});
		
	}
});