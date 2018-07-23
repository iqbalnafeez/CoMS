var FormCtrl = function ($scope,$http,$location,$routeParams,$rootScope,$filter,baseURL) {
	$scope.studentFormInfo={};
	$scope.studentFormInfo.photoLocation=null;
	$scope.alerts =[];
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	$scope.tabs=['APPFORMREQ','STUDENTINFO','PGFORM','UGFORM','REFERRALINFO'];
	$scope.tabs.active=function(){
		return $scope.tabs[$scope.tabs.index];
	};
	
/*	
 * on load display
 */	
	$scope.tabs.index=0;
	
	 // ***** to fix input type=number  issue on ie 11 
	  var numval = document.querySelector('input[type="number"]');
	  numval.onchange=function(e){

			if(isNaN(e.target.value)){
				e.target.value="";
		  }
		
	  };
	  //++++  to fix input type=number  issue on ie 11
	  
	  
	//for back button start ***
	$scope.backfromugandpg = function () {	
		$scope.tabs.index=1;
	};
	$scope.backfromreferalinfo = function () {
		if($scope.studentFormInfo.courseCategory=="UG"){
			$scope.tabs.index=3;	
		}
		else{
			$scope.tabs.index=2;
		}
	};
	//for back button end ***
	
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
	
/*on click go  method call start ++++*/
	$scope.checkappno=function(){
		$scope.disablecrsbat=false;
		$scope.isDisabled = false;
		$scope.studentFormInfo={};
		$scope.studentFormInfo.photoLocation="/TSNC/photos/person.jpg";

		$scope.alerts =[];
		$scope.feereq.token=localStorage.getItem("token");	
		$scope.feereq.sem=0;
		$http.post(baseURL+'Formctrl/AppFormReq.htm',$scope.feereq).
		success(function(data, status, headers, config) {
			
			console.log($scope.data);
			if(data.success){
				
				$scope.tabs.index=1;
				$scope.studentFormInfo=data.success;
				if($scope.studentFormInfo.courseCategory==null||$scope.studentFormInfo.courseCategory==undefined||$scope.studentFormInfo.courseCategory.length<1){
					$scope.studentFormInfo.date=new Date();
	
				
					$scope.studentFormInfo.dob="";
					
					$scope.studentFormInfo.gender="M";
					$scope.studentFormInfo.courseCategory="UG";
				}else{
					//disable if course and batch start year for old application number 
					$scope.disablecrsbat=true;
				}
				if($scope.studentFormInfo.phyhandi==null||
						$scope.studentFormInfo.phyhandi==undefined||
						$scope.studentFormInfo.phyhandi.length<1){
					$scope.studentFormInfo.phyhandi=false;
				}
				if($scope.studentFormInfo.wardofExservice==null||
						$scope.studentFormInfo.wardofExservice==undefined||
						$scope.studentFormInfo.wardofExservice.length<1){
					$scope.studentFormInfo.wardofExservice=false;
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
			
			console.log("error "+data.error);
			
		});

	};
	
	/*on click go  method call end $$$$$$*/	
	
	
	
	$scope.SAVEMANDINFO=function(){
		$scope.isDisabled = false;
		$scope.alerts =[];
		$scope.studentFormInfo.token=localStorage.getItem("token");
		$http.post(baseURL+'Formctrl/SAVEMANDINFO.htm',$scope.studentFormInfo).
		success(function(data, status, headers, config) {
			//$scope.studentFormInfo=data.success;
			$scope.tabs.index=1;
		//no default value is already exist
			if(data.success){
				console.log("success");
				$scope.tabs.index=1;
				$scope.disablecrsbat=true;
				$scope.alerts.push({type:"Success",msg: "Your application form was created successfully. Please enter further details to complete the application process."});
				
				$scope.studentFormInfo=data.success;
				console.log($scope.studentFormInfo.photoLocation);
			
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
			console.log("error in the path");
		});

	};
$scope.change=function(){


        $scope.isDisabled = true;
       	console.log($scope.isDisabled);
};

//call the choose file function+++

$scope.callUpload=function($choosefile){
console.log("enter here");
	choosefile.click();
};



$scope.setQues = function (element) {
	  $scope.studentFormInfo.photoLocation=null;
		console.log("file");
		$scope.uploadedFile = element.files[0];
		
		if (!$scope.uploadedFile) {
			return;
		}
		var fd = new FormData();

		fd.append("file", $scope.uploadedFile);
		fd.append("token",localStorage.getItem("token"));
		


		var url=baseURL+'Services/uploadphoto.htm';
		$http
		.post(url, fd, {
			headers:{
				'Content-Type':undefined
			},
			transformRequest:angular.identity
		}).
		success(function(data, status, headers, config) {
			if(data.success)
			{console.log("success file name " +data.success);
			$scope.studentFormInfo.photoLocation=data.success;
			console.log($scope.image);
			
			//$scope.alerts.push({type:"success",msg: data.success});
			
			}
			if(data.error)
			{if(data.error=="Invalid token"){
				window.location.href=baseURL;	
			}else{
			$scope.alerts.push({type:"danger",msg:data.error});
			}
			}
		}).
		error(function(data, status, headers, config) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	$scope.STDFRMINFO=function(){
		$scope.alerts =[];
		console.log($scope.studentFormInfo.batchStrYr);
		$scope.studentFormInfo.token=localStorage.getItem("token");
		$http.post(baseURL+'Formctrl/STDFRMINFO.htm',$scope.studentFormInfo).
		success(function(data, status, headers, config) {
			console.log("sdfs");
			if(data.success){
				console.log(data.success);
				
				
				
			if($scope.studentFormInfo.courseCategory=="UG"){
				$scope.tabs.index=3;
				//changes need to be done now 
				console.log(data.success);
				$scope.ugForm=data.success;
				console.log($scope.ugForm);
				//	$scope.lssubjectMarks=[{},{},{},{},{},{}];
				$scope.lssubjectMarks=$scope.ugForm.lssubjectMarks;
				
				if($scope.lssubjectMarks==null||$scope.lssubjectMarks==undefined||$scope.lssubjectMarks.length<1){
					
					$scope.lssubjectMarks=	[
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1},
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1},
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1},
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1},
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1},
{"applicationNo":$scope.ugForm.applicationNo,"subject":null,"markobtain":0,"maxmark":200,"regno":null,"yearofpass":null,"attempt":1}
];
					
					console.log($scope.lssubjectMarks);
					
				}
				$scope.calc();
			}
			else{
				$scope.tabs.index=2;
				$scope.pgform=data.success;
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
			console.log("error in the path");
		});

	};
	  
	
	$scope.calc=function(){
		$scope.ugForm.totalmarkobtained=0;
		$scope.ugForm.totalmaxmark=0;
		$scope.ugForm.percentage=0;
		for(var i=0;i<$scope.lssubjectMarks.length;i++){
			$scope.ugForm.totalmarkobtained=$scope.ugForm.totalmarkobtained+$scope.lssubjectMarks[i].markobtain;	
			$scope.ugForm.totalmaxmark=$scope.ugForm.totalmaxmark+$scope.lssubjectMarks[i].maxmark;	
			$scope.ugForm.percentage=(($scope.ugForm.totalmarkobtained/$scope.ugForm.totalmaxmark)*100).toFixed(2);	
			
		}
		console.log($scope.ugForm);
	};
	$scope.UGFRMSAVE=function(){
		$scope.calc();
		$scope.alerts =[];
		console.log($scope.ugForm);
		$scope.ugForm.token=localStorage.getItem("token");
		
		$scope.ugForm.lssubjectMarks=$scope.lssubjectMarks;
		console.log($scope.lssubjectMarks);
		console.log($scope.ugForm);
		$http.post(baseURL+'Formctrl/UGFRMSAVE.htm',$scope.ugForm).
		success(function(data, status, headers, config) {
			console.log(data);
			if(data.success){
				$scope.tabs.index=4;
				$scope.referralInfo=data.success;
				if(($scope.referralInfo.howknow==null ||$scope.referralInfo.howknow==undefined || $scope.referralInfo.howknow.length<1 )){
					$scope.referralInfo.howknow="NewsPaper";
				}
				console.log($scope.referralInfo);
							}
			if(data.error){
				if(data.error=="Invalid token"){
					window.location.href=baseURL;	
				}else{
				$scope.alerts.push({type:"danger",msg:data.error});
				}
				$scope.alerts.push({type:"danger",msg:data.error});
			}

		}).
		error(function(data, status, headers, config) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			console.log("error in the path2");
		});
	};
	
	$scope.PGFRMSAVE=function(){
		$scope.alerts =[];
		$scope.pgform.token=localStorage.getItem("token");
		console.log($scope.studentFormInfo.applicationNo);
		$scope.pgform.applicationNo=$scope.studentFormInfo.applicationNo;
		
		
		console.log($scope.pgform);
		$http.post(baseURL+'Formctrl/PGFRMSAVE.htm',$scope.pgform).
		success(function(data, status, headers, config) {
		console.log("form save successfully");
			if(data.success){
				console.log(data.success);
				$scope.tabs.index=4;
				$scope.referralInfo=data.success;
				if(($scope.referralInfo.howknow==null ||$scope.referralInfo.howknow==undefined || $scope.referralInfo.howknow.length<1 )){
					$scope.referralInfo.howknow="NewsPaper";
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
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			console.log("error in the path2");
		});
	};
	$scope.REFERRALINFO=function(){
		$scope.alerts =[];
		console.log("REFERRALINFO");
		$scope.referralInfo.token=localStorage.getItem("token");
		$http.post(baseURL+'Formctrl/REFERRALINFO.htm',$scope.referralInfo).
		success(function(data, status, headers, config) {
			console.log("REFERRALINFO successful");
			console.log(data);
				if(data.success){
				$scope.tabs.index=0;
				console.log("you have successfull create the application form");
				//$scope.alerts.push({type:"success",msg:data});
				$scope.alerts.push({type:"success",msg:data.success});
				
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
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			console.log("error in the path2");
		});
	};
	
	
	
	/*	date picker start  ****
	*/	 	 $scope.todaydate= new Date();
		  $scope.opendob = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.openeddob = true;
			  };
			  $scope.opend= function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();
	
				    $scope.openedd= true;
				  };
				  $scope.openPO= function($event) {
					    $event.preventDefault();
					    $event.stopPropagation();
		
					    $scope.openedPO= true;
					  };
	/*		date picker ends  +++++
	*/
						 // ***** customize the error  Number exceed the max length size
					/*	var numval = document.querySelector('input[type="number"]');
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