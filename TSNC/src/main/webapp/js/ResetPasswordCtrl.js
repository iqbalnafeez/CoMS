var ResetPasswordCtrl = function ($scope,$http,$location,$routeParams,$rootScope,$filter,baseURL) {
	console.log("ResetPasswordCtrl");
	   //alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end
	
	$scope.base={};
	$scope.loginls=[];
	$scope.login={};
	$scope.base.token=localStorage.getItem("token");
	
	$http.post(baseURL+'ResetPasswordCtrl/RESETPASSWORDREQUEST.htm',$scope.base).
	success(function(data, status, headers, config) {
	if(data.success){
		console.log(data.success);
		$scope.loginls=data.success;
		}
	if(data.error){
		if(data.error=="Invalid token"){
			window.location.href=baseURL;	
		}
		
		}
	}).
	error(function(data, status, headers, config) {
	console.log("error in the path");
	});
	$scope.confpassword=[];
	$scope.changePassword=function(index,confpassword){
		console.log(index);
		$scope.alerts =[];
		$scope.login=$scope.loginls[index];
		console.log($scope);
		console.log($scope.login.password);
	
		if($scope.login.password==null || $scope.login.password==undefined || $scope.login.password.length < 1 ){
			$scope.alerts.push({type:"danger",msg: " Please enter password  "});
		
		}else if(confpassword==null || confpassword==undefined ||confpassword.length < 1 ){
			
			$scope.alerts.push({type:"danger",msg: " Please enter confirm password  "});
			
		}else if($scope.login.password != confpassword)
		{
		$scope.IsMatch=true;
		$scope.alerts.push({type:"danger",msg: "Password mismatch"});
		
		}else{
			$scope.IsMatch=false;
			$scope.login.token=localStorage.getItem("token");
			console.log($scope.login);
			console.log("Password change request sent successfully");
			$http.post(baseURL+'ResetPasswordCtrl/RESETPASSWORD.htm',$scope.login).
			success(function(data, status, headers, config) {
			if(data.success){
				$scope.alerts.push({type:"success",msg: data.success});
				console.log(data.success);
				$scope.confpassword=[];
				confpassword="";
				$scope.login.password=null;
				$scope.login=null;
			
				
				}
			if(data.error){
				if(data.error=="Invalid token"){
					window.location.href=baseURL;	
				}
				
				}
			}).
			error(function(data, status, headers, config) {
			console.log("error in the path");
			});
			
		}
		
	
	};






};