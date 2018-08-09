app.service("specificationService",function($http){
	
	this.search=function(page,size,searchEntity){
		return $http.post("../specification/search?page="+page+"&size="+size,searchEntity);
	}
	this.add=function(entity){
		return $http.post("../specification/add",entity);
	}
	this.findOne=function(id){
		return $http.get("../specification/findOne?id="+id);
	}
	this.update=function(entity){
		return $http.post("../specification/update",entity);
	}
	this.findSpecList=function(){
		return $http.get("../specification/findSpecList");
	}
});