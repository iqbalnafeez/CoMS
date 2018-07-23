var FeesCtrl = function ($scope,$http,$location,$routeParams,$rootScope,$filter,baseURL) {
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
	
		$scope.maxchqnum=function(){
			if(isNaN($scope.fee.paymentnumber)){
				$scope.fee.paymentnumber="";
			}else{
				$scope.fee.paymentnumber;
			}
			
		};
	$scope.tabs=['PAYMENT','HISTORY','PARTICULARHISTORY','shownothing'];
	
	$scope.tabs.active=function(){
		return $scope.tabs[$scope.tabs.index];
	};
	/* $scope.date = $filter("date")(Date.now(), 'yyyy-MM-dd');*/
	 $scope.dt = new Date();
		$scope.getconcessions = function() {
			$http({
			method:'POST',
			url:baseURL+'StructureCtrl/Concession/GO.htm',
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {
			

			if(data.success){
				$scope.concessions=data.success;
				console.log($scope.concessions);
				
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
		
	};
	$scope.getconcessions();
/*	$scope.tabs.index=0;*/

	$scope.PAYMENT=function(){
		$scope.tabs.index=3;
		$scope.alerts =[];
		console.log($scope.feereq);
	//$scope.feereq={type: "ano", num: "100", sem: "1", lastAcademicType: "TNHSC&UNIVOFMadras", token: "ad59b0a0-7e98-4e4b-8757-84432a82704f"} ;
		$scope.feereq.token=localStorage.getItem("token");
		$http.post(baseURL+'FeesCtrl/PAYMENT.htm',$scope.feereq).
		success(function(data, status, headers, config) {
			console.log(data);
			if(data.success){
				console.log(data.success);
				$scope.fee=data.success.fee;
				// $scope.fee.dueDate = $filter("date")($scope.fee.dueDate, 'yyyy-MM-dd');
				$scope.lsfeeParticulars=data.success.lsfeeParticulars;
				
			
				for(var i=0;i<$scope.concessions.length;i++){
					var con=$scope.concessions[i];
					if(con.type==$scope.fee.concessionType){
						$scope.concessionstr=con;
						console.log(con.type+" == "+$scope.fee.concessionType);
						break;
					}else{
						$scope.concessionstr={};
					}
				}
				
				$scope.amountpaidnow=0;
				$scope.fee.receiptNo=null;
			
					
				
				console.log($scope.lsfeeParticulars);
				for(var i=0;i<$scope.lsfeeParticulars.length;i++){
					var feeParticulars=$scope.lsfeeParticulars[i];
				if(feeParticulars.id==0){
					
					feeParticulars.isnewone=false;
				}else{
					feeParticulars.isnewone=true;
				}
				$scope.lsfeeParticulars[i]= feeParticulars;
														
				}
				console.log($scope.lsfeeParticulars);
				//changed to current date
				 $scope.dt = new Date();
				$scope.tabs.index=0;
			}if(data.error){
				console.log("error "+data.error);
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
		});


		
	};
/*$scope.PAYMENT();*/
	
	$scope.addfee=function(feeParticulars)
	{
		$scope.fee.actualFee=0;
		for(var i=0;i<feeParticulars.length;i++){
			var feeParticular=feeParticulars[i];
			
			if(feeParticular.isnewone==true){
				$scope.fee.actualFee=feeParticular.amount+$scope.fee.actualFee;
			}
			console.log(feeParticular);
			console.log($scope.fee.actualFee);
			
		}
		
		if($scope.concessionstr.percent){
			$scope.fee.feeNeedTOPay=$scope.fee.actualFee-($scope.concessionstr.percent*$scope.fee.actualFee)/100;
				
		}else{
			$scope.fee.feeNeedTOPay=$scope.fee.actualFee;
		}
		if($scope.amountpaidnow==null){
			$scope.amountpaidnow=0;
		}
		$scope.fee.due=(($scope.fee.feeNeedTOPay+$scope.fee.fine)-($scope.fee.feePaid+$scope.amountpaidnow)).toFixed(2);
	};
	$scope.addconcession=function()
	{
		if($scope.concessionstr==null||$scope.concessionstr==undefined||$scope.concessionstr.length<1){
	
			$scope.fee.feeNeedTOPay=$scope.fee.actualFee;
			
		}
		
		if($scope.concessionstr!=null && $scope.concessionstr.percent){
		
			$scope.fee.feeNeedTOPay=$scope.fee.actualFee-($scope.concessionstr.percent*$scope.fee.actualFee)/100 ;
				
		}else{
			
			$scope.fee.feeNeedTOPay=$scope.fee.actualFee;
		}
		if($scope.amountpaidnow==null){
			
			$scope.amountpaidnow=0;
		}
	
	
		$scope.fee.due=(($scope.fee.feeNeedTOPay+$scope.fee.fine)-($scope.fee.feePaid+$scope.amountpaidnow)).toFixed(2);
		
		console.log("$scope.fee.due"+$scope.fee.due);
	};
	$scope.amountnow=function()
	{
		console.log($scope.amountpaidnow);

		$scope.fee.due=(($scope.fee.feeNeedTOPay+$scope.fee.fine)-($scope.fee.feePaid+$scope.amountpaidnow)).toFixed(2);
	};
	
	$scope.PaymentSubmit=function(){
		$scope.tabs.index=3;
		$scope.fee.concessionType=$scope.concessionstr.type;
		$scope.fee.concessionPercantage=$scope.concessionstr.percent;
		$scope.fee.feePaid=$scope.fee.feePaid+$scope.amountpaidnow;
		$scope.fee.date=$scope.dt;
		console.log($scope.fee);
		console.log($scope.lsfeeParticulars);
		$scope.paymentreq={"fee":$scope.fee,"lsfeeParticulars":$scope.lsfeeParticulars};
		$scope.paymentreq.token=localStorage.getItem("token");
		console.log($scope.paymentreq);
		$http.post(baseURL+'FeesCtrl/PaymentSubmit.htm',$scope.paymentreq).
		success(function(data, status, headers, config) {
			console.log(data);
			if(data.success){
				$scope.alerts.push({type:"success",msg: data.success});
				
				

			}if(data.error){
				console.log("error "+data.error);
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
		});


		
	};
	
	
	
	$scope.HISTORY=function(){
		$scope.tabs.index=3;
		$scope.alerts =[];
		console.log($scope.myForm.$valid);
		if(!$scope.myForm.$valid){
			$scope.alerts.push({type:"danger",msg: "All the fields are mandatory"});
		}else{
			
			
			
			$scope.feereq.token=localStorage.getItem("token");
			console.log($scope.feereq);
			$http.post(baseURL+'FeesCtrl/HISTORY.htm',$scope.feereq).
			success(function(data, status, headers, config) {
				console.log(data);
				if(data.success){
					console.log(data.success);
					$scope.lsfeeHistorys=data.success;
					$scope.tabs.index=1;
				}if(data.error){
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
			});


			
		}
		
	};
	

	$scope.PARTICULARHISTORY=function(hid){
		/*alert(hid);
	*/$scope.alerts =[];
		$scope.tabs.index=1;
		$http({
			method:'POST',
			url:baseURL+'FeesCtrl/PARTICULARHISTORY.htm',
			params:{"hid":hid},
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {
			

			if(data.success){
				$scope.lsfeeParticulars=data.success;
				console.log($scope.concessions);
				$scope.tabs.index=2;
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
	
	$scope.reverttransaction=function(hid,amount){
		
		/*alert(hid+" , "+amount);*/
		
		$scope.alerts =[];
		 if (confirm("Are you sure to revert the transaction ?") == true) {
		$scope.tabs.index=1;
		$http({
			method:'POST',
			url:baseURL+'FeesCtrl/canceltransaction.htm',
			params:{"hid":hid,"amount":amount},
			data:{'token': localStorage.getItem("token")}
		}).
		success(function(data, status, headers, config) {
			

			if(data.success){
				$scope.lsfeeHistorys=data.success;
				$scope.alerts.push({type:"success",msg:"Transaction was reverted successfully"});
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
		
		 }else {
		       console.log("cancel");
		    }
	};
	
	 $scope.todaydate= new Date();
	 
	 $scope.open1 = function($event) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope.opened1 = true;
		  };
	
	  $scope.open2 = function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.opened2 = true;
			  };
			
			  $scope.open3 = function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope.opened3 = true;
				  };
				  $scope.resetfee = function(){
				
						$scope.fee.concessionPercantage=0;
						
						$scope.amountpaidnow=0;
						 $scope.dt = $scope.todaydate;
						 $scope.fee.paymentdated= $scope.todaydate;
						 $scope.fee.feeNeedTOPay=$scope.fee.actualFee;
						 $scope.fee.due=(($scope.fee.feeNeedTOPay+$scope.fee.fine)-($scope.fee.feePaid+$scope.amountpaidnow)).toFixed(2);
				  };	  

	

};



//Date strat ************************************************
/*	ref 
* link http://plnkr.co/edit/?p=preview
* 	http://angular-ui.github.io/bootstrap/
*/
/* $scope.today = function() {
	    $scope.dt = new Date();
	  };
	  $scope.today();

	  $scope.clear = function () {
	    $scope.dt = null;
	  };

	  // Disable weekend selection
	  $scope.disabled = function(date, mode) {
	    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	  };

	  $scope.toggleMin = function() {
	    $scope.minDate = $scope.minDate ? null : new Date();
	  };
	  $scope.toggleMin();

	  $scope.open = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.opened = true;
	  };

	  $scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 1
	  };

	  $scope.initDate = new Date('2016-15-20');
	  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	  $scope.format = $scope.formats[0];*/
//Date ends+++++++++++++++++++++++++++++++++++++++++++++++++++++
