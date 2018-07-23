var ReportCtrl = function ($scope,$http,$location,$routeParams,$rootScope,baseURL) {
	$scope.tabs=['ApplictionSales','AdmissionReport','FeesReport','StudentMasterReport'];
	$scope.tabs.active=function(){
		return $scope.tabs[$scope.tabs.index];
	};
	/*	
	 * on load display
	 */	
		$scope.tabs.index=0;

$scope.show=function(i){
	$scope.tabs.index=i;
	console.log($scope.tabs.index +"  "+$scope.tabs.active());
};

// ***** to fix input type=number  issue on ie 11 
var numval = document.querySelector('input[type="number"]');
numval.onchange=function(e){

		if(isNaN(e.target.value)){
			e.target.value="";
	  }
	
};
//++++  to fix input type=number  issue on ie 11
	
	/*getting courses           start ---*/
	$scope.lscourses=[];
	$http({
		method:'POST',
		url:baseURL+'Services/Courses.htm',
		data:{'token': localStorage.getItem("token")}
	}).
	success(function(data, status, headers, config) {
		

		if(data.success){
			$scope.lscourses=data.success;
			$scope.lscourses.push(["ALL","ALL"]);
			console.log($scope.lscourses);
			if($scope.lscourses==null || $scope.lscourses==undefined || $scope.lscourses.length < 1 ){
				$scope.alerts.push({type:"danger",msg: " contact admin to add courses  "});
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
			$scope.locations.push({"id":"0","location":"ALL"});
			console.log($scope.locations);
			if($scope.locations==null || $scope.locations==undefined || $scope.locations.length < 1 ){
				$scope.alerts.push({type:"danger",msg: "contact admin to  add application sale Locations   "});
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
/*	Locations end ----*/
	
	
	
	
		/*ApplictionSalesCtrl start ****/
	$scope.ApplictionSalesRPCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
	
	//alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end


	
	$scope.GO = function() {
		
		$scope.alerts =[];
		console.log($scope.batchStrYr);
		$scope.saleReportReq.token=localStorage.getItem("token");
		$http({
			method:'POST',
			url:baseURL+'ReportCtrl/appsalereport/GO.htm',
			
			data:$scope.saleReportReq
		}).
		success(function(data, status, headers, config) {


			if(data.success){
				console.log(data.success);
				$scope.dwn_prv_btn=true;
				$scope.down=data.success.downloadpath;
					$scope.prev=data.success.previewpath;
				

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
	
	/*	date picker start  ****
	*/	 
		  $scope.openfrm = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.openedfrm = true;
			  };
	  $scope.opento = function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope.openedto = true;
			  };
				/*	date picker end  ****
				*/	
	
	};
	/*ApplictionSalesCtrl END ****/
	
	

	/*FeesRPCtrl start ****/
$scope.FeesRPCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {

//alert start
$scope.alerts =[];
$scope.closeAlert = function(index) {
	$scope.alerts.splice(index, 1);
};
//alert end



$scope.GO = function() {
	
	$scope.alerts =[];
	console.log($scope.batchStrYr);
	$scope.feesReportReq.token=localStorage.getItem("token");
	$http({
		method:'POST',
		url:baseURL+'ReportCtrl/feereport/GO.htm',
		
		data:$scope.feesReportReq
	}).
	success(function(data, status, headers, config) {


		if(data.success){
			console.log(data.success);
			$scope.dwn_prv_btn=true;
			$scope.down=data.success.downloadpath;
				$scope.prev=data.success.previewpath;
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

/*	date picker start  ****
*/	 
	  $scope.openfrm = function($event) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope.openedfrm = true;
		  };
  $scope.opento = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.openedto = true;
		  };
			/*	date picker end  ****
			*/	

};
/*FeesRPCtrl END ****/

/*Student Master Report Controller Start*********   */

$scope.MasterRPCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
	
	//alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end


	
	$scope.GenerateMaster = function() {
		
		console.log($scope.masterReport.batchStryr);
		//var batchStrYr=$scope.masterReport.batchStryr;
//			
		$http({
			method:'POST',
			url:baseURL+'ReportCtrl/StudentMasterReport/GO.htm',
			params:{'batchStrYr':$scope.masterReport.batchStryr,
				'reportFormat':$scope.masterReport.format
				},
			
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {


			if(data.success){
				console.log(data.success);
				$scope.dwn_prv_btn=true;
				$scope.down=data.success.downloadpath;
					$scope.prev=data.success.previewpath;
				

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

	/*	date picker start  ****
	*/	 
		  $scope.openfrm = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.openedfrm = true;
			  };
	  $scope.opento = function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope.openedto = true;
			  };
				/*	date picker end  ****
				*/	

};

/*Student master report Controller end********
$scope.AdmissionRPCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
	
	//alert start
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	//alert end


	
	$scope.GenerateAdmission = function() {
		console.log("enter into admission report");
		console.log($scope.admissionReportReq.batchstartyear);
		console.log($scope.admissionReportReq.todate);
		console.log($scope.admissionReportReq.reportFormat);
		$scope.admissionReportReq.token=localStorage.getItem("token");
		//console.log($scope.admissionReportReq.batchStryr);
		$http({
			method:'POST',
			url:baseURL+'ReportCtrl/StudentAdmissionReport/GO.htm',
			params:{'batchStrYr':$scope.admissionReportReq.batchStryr,'frmdate':$scope.admissionReportReq.frmdate,'todate':$scope.admissionReportReq.todate,'reportFormat':$scope.admissionReportReq.format},
			data:$scope.base
			data:$scope.admissionReportReq
		}).
		success(function(data, status, headers, config) {


			if(data.success){
				console.log(data.success);
				$scope.dwn_prv_btn=true;
							

			}
			if(data.error){
				console.log("error "+data.error);
				$scope.alerts.push({type:"danger",msg:data.error});
			}


		}).
		error(function(data, status, headers, config) {
			console.log(data);
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	
		};
		

			date picker start  ****
			 
			  $scope.openfrm = function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope.openedfrm = true;
				  };
		  $scope.opento = function($event) {
					    $event.preventDefault();
					    $event.stopPropagation();

					    $scope.openedto = true;
				  };
						date picker end  ****
						
};

Student Admission report Controller end*********/
	/*Student Admission report Controller Start*********/
	
	$scope.AdmissionRPCtrl = function ($scope,$http,$location,$routeParams,$rootScope) {
		
		//alert start
		$scope.alerts =[];
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
		//alert end

		
		
		$scope.GenerateAdmission = function() {
			/*console.log("enter into admission report");
			console.log($scope.admissionReportReq.batchstartyear);
			console.log($scope.admissionReportReq.todate);
			console.log($scope.admissionReportReq.reportFormat)*/;
			$scope.admissionReportReq.token=localStorage.getItem("token");
			//console.log($scope.admissionReportReq.batchStryr);
			$http({
				method:'POST',
				url:baseURL+'ReportCtrl/StudentAdmissionReport/GO.htm',
				/*params:{'batchStrYr':$scope.admissionReportReq.batchStryr,'frmdate':$scope.admissionReportReq.frmdate,'todate':$scope.admissionReportReq.todate,'reportFormat':$scope.admissionReportReq.format},
				data:$scope.base*/
				data:$scope.admissionReportReq
			}).
			success(function(data, status, headers, config) {


				if(data.success){
				/*	$scope.admissionReportReq.batchstartyear=null;
					$scope.admissionReportReq.todate=null;
					$scope.admissionReportReq.reportFormat=null;
					$scope.admissionReportReq.fromdate=null;*/
					console.log(data.success);
					$scope.dwn_prv_btn=true;
					$scope.down=data.success.downloadpath;
						$scope.prev=data.success.previewpath;
					

				}
				if(data.error){
					console.log("error "+data.error);
					$scope.alerts.push({type:"danger",msg:data.error});
					$scope.admissionReportReq.batchstartyear=null;
					$scope.admissionReportReq.todate=null;
					$scope.admissionReportReq.reportFormat=null;
					$scope.admissionReportReq.fromdate=null;
				}


			}).
			error(function(data, status, headers, config) {
				console.log(data);
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		
			};
			
			/*	date picker start  ****
			*/	 
				  $scope.openfrm = function($event) {
					    $event.preventDefault();
					    $event.stopPropagation();

					    $scope.openedfrm = true;
					  };
			  $scope.opento = function($event) {
						    $event.preventDefault();
						    $event.stopPropagation();

						    $scope.openedto = true;
					  };
						/*	date picker end  ****
						*/	
	};
	
	/*Student Admission report Controller end*********/

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

