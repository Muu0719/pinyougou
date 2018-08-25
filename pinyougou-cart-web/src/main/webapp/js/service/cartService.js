app.service('cartService',function($http){
	
	this.findCartList = function(){
		return $http.get('../cart/findCartList');
	};
	
	this.addGoodsToCartList = function(itemId, num){
		return $http.get('../cart/addGoodsToCartList?itemId='+ itemId + '&num=' + num);
	};
	
	//计算购物车里所有商品总价和总个数
	this.sum=function(cartList){		
		
		var totalValue={totalNum:0, totalMoney:0.00 };//合计实体
		
		for(var i=0;i<cartList.length;i++){
			var cart=cartList[i];
			for(var j=0;j<cart.orderItemList.length;j++){
				var orderItem=cart.orderItemList[j];//购物车明细
				totalValue.totalNum+=orderItem.num;
				totalValue.totalMoney+= orderItem.totalFee;
			}				
		}
		return totalValue;
	}

})