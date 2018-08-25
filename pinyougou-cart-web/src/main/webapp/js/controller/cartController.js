app.controller('cartController',function($scope,cartService){
	
	
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
	
})