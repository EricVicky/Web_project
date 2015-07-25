angular.module('os').controller('ovmupgradectr', function($scope, $filter,  $log, OSService,  
		monitorService, DashboardService, $dialogs, $state) {
	

	
}).controller('ovmqosacupgradectr', function($scope, $filter,  $log, OSService,  
		monitorService, DashboardService, $dialogs, $state) {
    
    OSService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.oscomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "OPENSTACK"){
				if(JSON3.parse($scope.comInstance[ci].comConfig).comType == "QOSAC"){
					$scope.oscomInstance.push($scope.comInstance[ci]);
				}
			}
		}
    });
	
    $scope.showInstance = function(){
    	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    };
});