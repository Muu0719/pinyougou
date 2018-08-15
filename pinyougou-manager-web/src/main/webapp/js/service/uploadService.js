app.service("uploadService",function($http){
	
	this.uploadFile=function(){
		var formData = new FormData(); //表单
		
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