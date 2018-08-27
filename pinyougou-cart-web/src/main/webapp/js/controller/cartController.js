app.controller('cartController',function($scope,cartService, addressService){
	
	
	$scope.findCartList = function(){
		cartService.findCartList().success(function(response){
			$scope.cartList = response;
			
		    $scope.totalValue = cartService.sum($scope.cartList);
		})
	};
	
	$scope.addGoodsToCartList = function(itemId, num){
		cartService.addGoodsToCartList(itemId,num).success(function(response){
			$scope.findCartList();
		})
	}
	
	//获取地址
	$scope.findAddressList = function(){
		addressService.findAddressList().success(function(response){
			$scope.addressList = response;
		})
	}
	
	//判断是收货人是否选中
	$scope.selectAddress = function(address){
		$scope.address = address;
	}
	$scope.isSelectedAddress = function(address){
		if(address ==$scope.address){
			return true;
		} else {
			return false;
		}
	}
})