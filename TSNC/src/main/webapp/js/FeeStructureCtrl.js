var FeeStructureCtrl = function ($scope,$http,$location,$routeParams,$rootScope,baseURL) {
/*	  //alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end
	

			  
*/	
	  // ***** to fix input type=number  issue on ie 11 
	  var numval = document.querySelector('input[type="number"]');
	  numval.onchange=function(e){

			if(isNaN(e.target.value)){
				e.target.value="";
		  }
		
	  };
	  //++++  to fix input type=number  issue on ie 11
	  
	  
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
	
	
	/*FeeCtrl start ****/
	
	$scope.FeeCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
		  //alert start
		$scope.alerts =[];
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
		//alert end	
		$scope.getallcourses = function() {
			
			$http({
				method:'POST',
			url:baseURL+'Services/Courses.htm',
			data:{'token': localStorage.getItem("token")}			
		}).
		success(function(data, status, headers, config) {
				if(data.success){
				$scope.courses=data.success;
							console.log($scope.courses);
				
							}
			if(data.error){
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
		};
		$scope.getallcourses();
		
		$scope.changelastAcademictype=function(){
			if($scope.feestr.sem!=1){
				$scope.feestr.lastAcademicType='Others';
			}else{
				$scope.feestr.lastAcademicType='';
			}
		};

		$scope.feeStructures=[];
		$scope.GO = function() {
			
			$scope.feestr_temp=$scope.feestr;
			$scope.feestr_temp.token=localStorage.getItem("token");
			console.log($scope.feestr_temp);
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/feeStructure/GO.htm',
				data: $scope.feestr_temp
			}).
			success(function(data, status, headers, config) {
				

				if(data.success){
					$scope.feeStructures=data.success;
					console.log($scope.feeStructures);
					if($scope.feeStructures.length<1){
						$scope.ADD();
					}
					
					
								}
				if(data.error){
					if(data.error=="Invalid token"){
						window.location.href=baseURL;	
					}
			
				}


			}).
			error(function(data, status, headers, config) {
				console.log(data);
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
			
			
		
			
		};
		$scope.ADD = function() {
			$scope.alerts =[];
			if($scope.feestr_temp){
				$scope.feeStructures.push({"id":null,"particulars":null,"amount":null,"batchStrYr":$scope.feestr_temp.batchStrYr,"sem":$scope.feestr_temp.sem,"lastAcademicType":$scope.feestr_temp.lastAcademicType,"course":$scope.feestr_temp.course});
			}else{
				$scope.alerts.push({type:"danger",msg: "Please enter the mandatory fields and Click on Go"});
			}
			
		};
		$scope.SAVE = function() {
			$scope.alerts =[];
			console.log($scope.feeStructures);
			if($scope.feeStructures==null || $scope.feeStructures==undefined || $scope.feeStructures.length < 1 ){
				$scope.alerts.push({type:"danger",msg: "Add fee Structure detail before save"});
			}else{
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/feeStructure/SAVE.htm',
				
				data:{"token":localStorage.getItem("token"),"lsfeeStructure":$scope.feeStructures}
			}).
			success(function(data, status, headers, config) {
				

				if(data.success){
					
					console.log( data.success);
					
					$scope.alerts.push({type:"success",msg: data.success});
					$scope.GO();
								}
				if(data.error){
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
			for(var i=$scope.feeStructures.length-1;i>=0;i--){
				
				var feeStructure=$scope.feeStructures[i];
				console.log(feeStructure);
				if(feeStructure.del){
					if(feeStructure.id){
						did.push(feeStructure.id);
					}else{
						$scope.feeStructures.splice(i, 1);
					}
					
				}
			}

			console.log(did);
		if(did.length>0){
			
		
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/feeStructure/DELETE.htm',
				params:{"did":did},
				data:{"token":localStorage.getItem("token")}
			}).
			success(function(data, status, headers, config) {
				

				if(data.success){
					
					console.log( data.success);
				
					$scope.alerts.push({type:"success",msg: data.success});
					$scope.GO();
								}
				if(data.error){
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

	};
	/*FeeCtrl Ends ****/
	/*ConcessionCtrl Starts ****/
	$scope.ConcessionCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
		  //alert start
		$scope.alerts =[];
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
		//alert end
	//	$scope.concessions=[{"id":1,"type":"sports","percent":10},{"id":2,"type":"above 80","percent":80}];
		$scope.concessions=[];
		$scope.GO = function() {
			
				$http({
				method:'POST',
				url:baseURL+'StructureCtrl/Concession/GO.htm',
				data:{'token': localStorage.getItem("token")}
			}).
			success(function(data, status, headers, config) {
				

				if(data.success){
					$scope.concessions=data.success;
					if($scope.concessions.length<1){
						$scope.ADD();
					}
					console.log($scope.concessions);
					
								}
				if(data.error){
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
		
		$scope.GO();
		$scope.ADD = function() {
			console.log($scope.alerts);
			$scope.alerts =[];
			console.log($scope.alerts);
			$scope.concessions.push({"id":null,"type":null,"percent":null});
		};
		
		$scope.SAVE = function() {
			$scope.alerts =[];
		console.log($scope.concessions);
		if($scope.concessions==null || $scope.concessions==undefined || $scope.concessions.length < 1){
			$scope.alerts.push({type:"danger",msg: "Add concession detail before save"});
		}else{
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/Concession/SAVE.htm',
				
				data:{"token":localStorage.getItem("token"),"lsconcession":$scope.concessions}
			}).
			success(function(data, status, headers, config) {
				

				if(data.success){
					
					console.log( data.success);
					
					$scope.alerts.push({type:"success",msg: data.success});
					$scope.GO();
								}
				if(data.error){
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
		
					for(var i=$scope.concessions.length-1;i>=0;i--){
					var concession=$scope.concessions[i];
					console.log(concession);
					if(concession.del){
						if(concession.id){
						did.push(concession.id);

						}else{
							$scope.concessions.splice(i, 1);
						}
					}
				}
		
			
		
			console.log(did);
		if(did.length>0){
			
		
			$http({
				method:'POST',
				url:baseURL+'StructureCtrl/Concession/DELETE.htm',
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
		
	
	
};

/*ConcessionCtrl Ends ****/


/*FineCtrl Starts ****/
$scope.FineCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
	  //alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end
	 $scope.fndate = new Date();
	 
	$scope.fineStructures=[];
	$scope.GO = function() {
		var bathcstryr=$scope.fnbatchStrYr;
			$http({
			method:'POST',
			url:baseURL+'StructureCtrl/fine/GO.htm',
			params:{"batchStrYr":bathcstryr},
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {
			

			if(data.success){
				$scope.fineStructures=data.success;
				if($scope.fineStructures==null || $scope.fineStructures==undefined || $scope.fineStructures.length < 1){
					$scope.fineStructures=[
					                       {"batchStrYr":bathcstryr,"sem":1,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":2,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":3,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":4,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":5,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":6,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":7,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":8,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":9,"dueDate":null},
					                       {"batchStrYr":bathcstryr,"sem":10,"dueDate":null}
					                       ];
				}
				
							}
			if(data.error){
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
		

	
		
	};
	
	$scope.SAVE = function() {
		$scope.alerts =[];
	console.log($scope.fineStructures);
	if($scope.fineStructures==null || $scope.fineStructures==undefined || $scope.fineStructures.length < 1){
		$scope.alerts.push({type:"danger",msg: "Please enter the batch start year and Click on Go "});
	}else{
		$http({
			method:'POST',
			url:baseURL+'StructureCtrl/fine/SAVE.htm',
			
			data:{"token":localStorage.getItem("token"),"lsfineStructure":$scope.fineStructures}
		}).
		success(function(data, status, headers, config) {
			

			if(data.success){
				
				console.log( data.success);
				$scope.fineStructures=[];
				$scope.alerts.push({type:"success",msg: data.success});
				
							}
			if(data.error){
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
/*	$scope.numval = document.querySelector('input[type="number"]');
	$scope.numval.oninvalid = function(e) {
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
	
/*	date picker start  ****
*/	  $scope.opened=[];
	  $scope.open = function($event,sem) {
		    $event.preventDefault();
		    $event.stopPropagation();
console.log(sem);
		    $scope.opened[sem] = true;
		  };
/*		date picker ends  +++++
*/		
};

/*FineCtrl Ends ****/


};


/*INSERT INTO fee_structure
(particulars, amount, sem, course, lastAcademicType, batchStrYr)
SELECT particulars, amount, sem, 'M.COM', lastAcademicType, batchStrYr
FROM   fee_structure
WHERE  course = 'B.SC(COMP.SCIENCE)'*/