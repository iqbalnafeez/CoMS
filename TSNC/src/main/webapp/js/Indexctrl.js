
var app = angular.module('TSNC_index',  ['ui.bootstrap']);
/*app.constant('baseURL', 'http://172.20.1.176:8080/TSNC/');*/
app.constant('baseURL', 'http://61.246.255.122:99/TSNC/');



var Ctrl=function($scope,$http,baseURL){

//	alert start

	$scope.alerts =[];
/*	$scope.alerts.push({type:"danger",msg: "RAFDAF EEEEE"});
	$scope.alerts.push({type:"success",msg: "SSSSSS"});*/
	/*  $scope.alerts = [
	                   { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
	                   { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
	                 ];*/
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end


	
	

	/*	Login start ++++++
	 * */
	$scope.Login_submit=function(){
		console.log($scope.login);
		$scope.alerts =[];
		var role=null;

		
		$http.post(baseURL+'IndexCtrl/loginSubmit.htm',$scope.login).
		success(function(data, status, headers, config) {
			// this callback will be called asynchronously
			// when the response is available
		

			console.log("data from backend "+data);

			if(data.success){
				console.log(data.success);
				var login=data.success;
				/*alert(login);*/
				role=login.role;
				localStorage.setItem("name", login.username);		
				/*alert("role "+role);*/
			//	alert(localStorage.getItem("name"));
				$scope.redirect(role,login.token);
				

			}if(data.error){
				console.log("error "+data.error);
				$scope.alerts.push({type:"danger",msg: data.error});
			}
		}).
		error(function(data, status, headers, config) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

	};

	/*	Login end ++++++
	 * */
	
	//redirect start +++
	$scope.redirect=function(role,tkn){
		localStorage.setItem("token", tkn);
		localStorage.setItem("role", role);
		
			window.location.href=baseURL+"Main.html#/Fees";
		
	};
	//redirect end +++




};