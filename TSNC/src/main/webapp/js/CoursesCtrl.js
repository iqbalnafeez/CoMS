var CoursesCtrl = function ($scope,$http,$location,$routeParams,$rootScope,baseURL) {
	//alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end
	/*	alert("CoursesCtrl");*/
	
	// ***** to fix input type=number  issue on ie 11 
	  var numval = document.querySelector('input[type="number"]');
	  numval.onchange=function(e){

			if(isNaN(e.target.value)){
				e.target.value="";
		  }
		
	  };
	  //++++  to fix input type=number  issue on ie 11


	$scope.GO = function() {
		$scope.alerts =[];
		console.log($scope.batchStrYr);
		$scope.batch=$scope.batchStrYr;
		$http({
			method:'POST',
			url:baseURL+'StructureCtrl/Courses/GO.htm',
			params:{'batchStrYr':$scope.batchStrYr},
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {


			if(data.success){
				$scope.courses=data.success;
				if($scope.courses.length<1){
					$scope.ADD();
				}
				console.log($scope.courses);

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


		//	$scope.courses=[{"id":1,"course":"bsc","courseCategory":"UG","sanctionedStrength":50,"adhoc":5,"batchStrYr":2008},{"id":1,"course":"Mcom","courseCategory":"UG","sanctionedStrength":50,"adhoc":5,"batchStrYr":2008},{"id":3,"course":"Msc","courseCategory":"PG","sanctionedStrength":45,"adhoc":5,"batchStrYr":2008}];



	};
	$scope.ADD = function() {
		$scope.alerts =[];
		if($scope.batch){
			$scope.courses.push({"id":null,"course":null,"courseCategory":null,"sanctionedStrength":null,"adhoc":null,"batchStrYr":$scope.batch});
		}else{
			$scope.alerts.push({type:"danger",msg: "Please enter the Batch start year and Click on Go"});
		}

	};
	$scope.SAVE = function() {
		$scope.alerts =[];
		console.log($scope.courses);
		if($scope.courses==null || $scope.courses==undefined || $scope.courses.length < 1 ){
			$scope.alerts.push({type:"danger",msg: "Add courses detail before save"});
		}else{
			var arr = $scope.courses;
			console.log(arr);
			var repcoursechk=_.countBy(arr, function(arr) { return arr.course; });
		//	console.log( _.countBy(arr, function(arr) { return arr.course; }));
			for  (var key in repcoursechk){
				console.log(key +" "+ repcoursechk[key]);
				if(repcoursechk[key] > 1){
					$scope.alerts.push({type:"danger",msg:" Course  '"+ key+ "' already exists" });
					return false;
				}
			}
			
			var repcodechk=_.countBy(arr, function(arr) { return arr.rollcode; });
		//	console.log( _.countBy(arr, function(arr) { return arr.rollcode; }));
			for  (var key in repcodechk){
				console.log(key +" "+ repcodechk[key]);
				if(repcodechk[key] > 1){
					$scope.alerts.push({type:"danger",msg:" Roll number code '"+ key+ "' already exists" });
					return false;
				}
			}
			
			
			
		
			
			
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/Courses/SAVE.htm',

				data:{"token":localStorage.getItem("token"),"lscourse":$scope.courses}
			}).
			success(function(data, status, headers, config) {


				if(data.success){

					console.log( data.success);
					$scope.GO();
					$scope.alerts.push({type:"success",msg: data.success});
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

		}
	};
	$scope.DELETE = function() {
		$scope.alerts =[];
		var did=[];

		for(var i=$scope.courses.length-1;i>=0;i--){
			var course=$scope.courses[i];
			console.log(course);
			if(course.del){
				if(course.id){
					did.push(course.id);
				}else{
					$scope.courses.splice(i, 1);
				}

			}
		}

		console.log(did);
		if(did.length>0){


			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/Courses/DELETE.htm',
				params:{"did":did},
				data:{"token":localStorage.getItem("token")}
			}).
			success(function(data, status, headers, config) {


				if(data.success){

					console.log( data.success);
					$scope.GO();
					$scope.alerts.push({type:"success",msg: data.success});
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
		}
	};
	 // ***** customize the error  Number exceed the max length size
	/*var numval = document.querySelector('input[type="number"]');
	numval.oninvalid = function(e) {
		//e.target.setCustomValidity("");
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
			
			 * if (e.target.value.length == 0) {
				e.target.setCustomValidity("Please fill out this field ");
				} else {
				e.target.setCustomValidity("Please enter only valid number");
				}
		
			}
		};*/
		 //  +++++ customize the error  Number exceed the max length size


};


/*
INSERT INTO courses
(course, courseCategory, sanctionedStrength, adhoc, batchStrYr)
SELECT course, courseCategory, sanctionedStrength, adhoc, 2015
FROM   courses
WHERE  batchStrYr = 2014*/