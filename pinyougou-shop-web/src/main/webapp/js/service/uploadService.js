app.service("uploadService",function($http){
	//文件上传
	this.uploadFile=function(){
		//创建一个表单对象
		var formData = new FormData();
		//使用file对象的files[index]属性,往里添加文件数据
		formData.append("file",file.files[0]);
		
		return $http({
			method:'POST',
			url:"../upload",
			data: formData,
			headers: {'Content-Type':undefined},
					transformRequest: angular.identity
			});
	}
});