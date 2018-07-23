

var app = angular.module('TSNC_main',  ['ui.bootstrap','ngRoute']);

app.config(RouteConfig); 
/*app.constant('baseURL', 'http://172.20.1.176:8080/TSNC/');*/
app.constant('baseURL', 'http://61.246.255.122:99/TSNC/');
app.controller("MainCtrl", function ($scope, $http, $routeParams,$rootScope,baseURL) {
	

	

		
	if(localStorage.getItem("name")!=null){
		var username=localStorage.getItem("name").split("@");
		
		$scope.name=username[0];
	
	}
	if(localStorage.getItem("role")!=null){
		$scope.role=localStorage.getItem("role");
		
	
	
	}
	
	$scope.Logout = function() {
	console.log("logout");
		 if (confirm("Are you sure to exit?") == true) {
		
		 
		var token = localStorage.getItem("token");
		var role = localStorage.getItem("role");
		console.log(role);
		localStorage.removeItem("token");
		localStorage.removeItem("role");
		if(token==null){
			window.location.href = baseURL;
		}
		$http({
			method : 'DELETE',
			url : baseURL+'IndexCtrl/logout.htm',
			data  :  token
		
		}).success(function(data, status, headers, config) {
			if (data.success) {
				console.log(data.success);
				window.location.href = baseURL;
			}
			if (data.error) {
				console.log("error " + data.error);
				window.location.href = baseURL;
			}

		}).error(function(data, status, headers, config) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
		 } else {
		       console.log("cancel");
		    }
	};

	 
});


function RouteConfig ($routeProvider) {
	$routeProvider.

	when('/ResetPassword', {
		controller: ResetPasswordCtrl, 
		templateUrl: 'views/ResetPassword.html'
	}).when('/Report', {
		controller: ReportCtrl, 
		templateUrl: 'views/Report.html'
	}).
	when('/Form', {
		controller : FormCtrl, 
		templateUrl: 'views/Form.html'
	}).
	when('/Courses', {
		controller : CoursesCtrl, 
		templateUrl: 'views/Courses.html'
	}).
	when('/FeeStructure', {
		controller : FeeStructureCtrl, 
		templateUrl: 'views/FeeStructure.html'
	}).
	when('/Fees', {
		controller : FeesCtrl, 
		templateUrl: 'views/Fees.html'
	}).when('/ApplicationSales', {
		controller : ApplicationSaleCtrl, 
		templateUrl: 'views/ApplicationSale.html'
	})/*.

	otherwise({
		redirectTo: '/Fees'})*/;
}

/*
var filters={};
filters.limitscoredate=function (){
	return function(scoress,scr) {
		console.log(scoress);
		console.log(scr);
		var scores=[];
		var j=0;
		if(scoress!=null&&scr!=null){
			 var angularDateFilter = $filter('date');

    var mindate=angularDateFilter(scr.mindate, 'yyyy-mm-dd');
    var maxdate=angularDateFilter(scr.maxdate, 'yyyy-mm-dd');
			 
			var mindate=scr.mindate;
			var maxdate=scr.maxdate;
			if( mindate!=null&& maxdate!=null ){
				console.log("min ams date" +mindate+" "+maxdate);
				for(var i=0; i<scoress.length; i++){
					
					if(scoress[i].examdate >=mindate && scoress[i].examdate  <=maxdate  ){
						scores[j]=scoress[i];
						j=j+1;
						console.log(mindate+" >= "+scoress[i].examdate+" <= "+maxdate);
					}}
				return scores;
			}
			if(scr.minsc!=null&& scr.maxsc!=null ){
			console.log("min ams score");
				for(var i=0; i<scoress.length; i++){
				//console.log(states[i]);
				if(scoress[i].score.replace('%', '') >=scr.minsc && scoress[i].score.replace('%', '')  <=scr.maxsc  ){
					scores[j]=scoress[i];
					j=j+1;
					console.log(scoress[i]);
				}

			}
			return scores;
			
			}
		}
		return scoress;

	};};
	filters.limitfbdate=function (){
		return function(feedbackss,fb) {

			var feedbacks=[];
			var j=0;
			if(feedbackss!=null&&fb!=null){
				
				var mindate=fb.mindate;
				var maxdate=fb.maxdate;
				if( mindate!=null&& maxdate!=null ){
					
					for(var i=0; i<feedbackss.length; i++){
						
						if(feedbackss[i].postdate >=mindate && feedbackss[i].postdate  <=maxdate  ){
							feedbacks[j]=feedbackss[i];
							j=j+1;
						console.log(mindate+" >= "+feedbackss[i].postdate+" <= "+maxdate);
						}}
					return feedbacks;
				}
			
			}
			return feedbackss;

		};};
		
	
	app.filter(filters);
*/
