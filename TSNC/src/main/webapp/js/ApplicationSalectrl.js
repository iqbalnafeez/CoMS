var ApplicationSaleCtrl = function ($scope,$http,$location,$routeParams,$rootScope,baseURL) {
	   //alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end
	  // ***** to fix input type=number  issue on ie 11 
	  var numval = document.querySelector('input[type="number"]');
	  numval.onchange=function(e){

			if(isNaN(e.target.value)){
				e.target.value="";
		  }
		
	  };
	  //++++  to fix input type=number  issue on ie 11


	
	/*getting courses start ---*/
	$scope.lscourses=[];
	$http({
		method:'POST',
		url:baseURL+'Services/Courses.htm',
		data:{'token': localStorage.getItem("token")}
	}).
	success(function(data, status, headers, config) {
		

		if(data.success){
			$scope.lscourses=data.success;
			console.log($scope.lscourses);
			if($scope.lscourses==null || $scope.lscourses==undefined || $scope.lscourses.length < 1 ){
				$scope.alerts.push({type:"danger",msg: " Contact admin to add courses  "});
			}
		}
		if(data.error){
			console.log("error "+data.error);
			if(data.error=="Invalid token"){
				window.location.href=baseURL;	
			}else{
			$scope.alerts.push({type:"danger",msg:data.error});
			}
		}


	}).
	error(function(data, status, headers, config) {
		console.log(data);
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
/*	Courses end ----*/
	
	/*getting location start ---*/
	$scope.locations=[];
	$http({
		method:'POST',
		url:baseURL+'Services/Locations.htm',
		data:{'token': localStorage.getItem("token")}
	}).
	success(function(data, status, headers, config) {
		

		if(data.success){
			$scope.locations=data.success;
			console.log($scope.locations);
			if($scope.locations==null || $scope.locations==undefined || $scope.locations.length < 1 ){
				$scope.alerts.push({type:"danger",msg: "contact admin to  add application sale Locations   "});
			}
		}
		if(data.error){
			console.log("error "+data.error);
			if(data.error=="Invalid token"){
				window.location.href=baseURL;	
			}else{
			$scope.alerts.push({type:"danger",msg:data.error});
			}
		
		}


	}).
	error(function(data, status, headers, config) {
		console.log(data);
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
/*	Locations end ----*/
	$scope.applicationSale={};
	$scope.applicationSale.gender='M';
	$scope.selectedcorse=function(){
		console.log($scope.choosencourse);
		if($scope.choosencourse!="" && $scope.choosencourse!=undefined ){
			
	
		$scope.applicationSale.course=$scope.choosencourse[0];
		$scope.applicationSale.courseCategory=$scope.choosencourse[1];
		console.log($scope.applicationSale);	}
	};
	
	$scope.Appsalesubmit=function(){
		
		$scope.alerts =[];
		if($scope.lscourses==null || $scope.lscourses==undefined || $scope.lscourses.length < 1 ){
			$scope.alerts.push({type:"danger",msg: "contact admin to add courses"});
		}else if($scope.locations==null || $scope.locations==undefined || $scope.locations.length < 1 ){
			$scope.alerts.push({type:"danger",msg: " contact admin to add application sale Locations  "});
		}
		else{
		console.log($scope.applicationSale);
		$scope.applicationSale.token=localStorage.getItem("token");
		$http.post(baseURL+'AppSaleCtrl/appSubmit.htm',$scope.applicationSale).
		success(function(data, status, headers, config) {
			if(data.success){
				$scope.alerts.push({type:"success",msg: data.success});
				$scope.applicationSale={};
				$scope.choosencourse=[];
				$scope.applicationSale.gender='M';
				console.log($scope.applicationSale);
				
							}
			if(data.error){
				console.log("error "+data.error);
				if(data.error=="Invalid token"){
					window.location.href=baseURL;	
				}else{
				$scope.alerts.push({type:"danger",msg:data.error});
				}
				
			}
		}).
		error(function(data, status, headers, config) {
			console.log("error");
		});}
	};
	
	$scope.numlen=function(){
		
	};
	
	/*	date picker start  ****
	*/	 $scope.todaydate= new Date();
		  $scope.open = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.opened = true;
			  };
				/*	date picker end  ****
				*/	
			  
			  $scope.resetsale=function(){
					$scope.applicationSale.date=$scope.todaydate;
			  };
			
			  
			  
			  
			  // ***** customize the error  Number exceed the max length size
		/*		
			  // ***** customize the error  Number exceed the max length size
			var numval = document.querySelector('input[type="number"]');
			alert(numval.validity);
			numval.validity=function(e){alert(e);};
			alert(numval);
			alert(numval.oninvalid);
			console.log(numval);
			console.log(numval.oninvalid);
				numval.oninvalid = function(e) {
					alert(numval);
					if (!e.target.validity.valid) {
						
						console.log(e.target.validity);
						if(e.target.validity.rangeOverflow){
							e.target.setCustomValidity("Number exceeds the maximum size ");
						}else if(e.target.validity.badInput){
							e.target.setCustomValidity("Please enter numeric value ");
						}
						else {
							e.target.setCustomValidity("");
						}
						
					
					};
					 //  +++++ customize the error  Number exceed the max length size
*/
};