 //控制层 
app.controller('userController' ,function($scope,userService){	
	
	$scope.saveUser=function(){
		//校验
		if($scope.entity.username == null || $scope.entity.password == null 
				|| $scope.entity.phone == null || $scope.smsCode == null){
			alert("请输入完整");
			return;
		}
		if($scope.entity.password != $scope.password){
			alert("两次密码不一致");
			return ;
		}
		userService.add($scope.entity,$scope.smsCode).success(function(response){
			if(response.success){
				//跳转到登录页面
	        	location.href="http://user.pinyougou.com/login.html";
			}else{
				alert(response.message);
			}
		});
	}
	
	$scope.entity={};
	
	$scope.getSmsCode=function(){
		if($scope.entity.phone == null || $scope.entity.phone == '' ){
			alert("请输入手机号");
			return;
		}
		//传参数：手机号
		userService.getSmsCode($scope.entity.phone).success(function(response){
			if(response.success){
				alert(response.message);
			} else{
				alert(response.message);
			}
		});
	}
	 
});	
