angular.module('os').controller('osupgradectr', function($scope, $filter,  $log 
		,  OSService, monitorService, $dialogs, $state , DashboardService) {
    OSService.getImages().then(function(data){
            	$scope.oam_cm_images = data;
            	$scope.db_images = data;
            });
    $scope.setDefaultInstace = function(){
    	var selectedOSInstance = DashboardService.getSelectedInstance();
    	if(selectedOSInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.oscomInstance){
    		var com_config = JSON3.parse($scope.oscomInstance[inst].comConfig);
    		if(angular.equals(com_config,selectedOSInstance)){
    		   $scope.com_instance = $scope.oscomInstance[inst];
    		   $scope.installConfig = com_config;
    		   return;
    		}
        }
    }
    
    OSService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.oscomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "OPENSTACK"){
				$scope.oscomInstance.push($scope.comInstance[ci]);
			}
		}
		$scope.setDefaultInstace();
    });
    $scope.showInstance = function(){
           $scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    }
    $scope.doUpgrade = function (){
    	OSService.upgrade($scope.installConfig).then( function(){
    		monitorService.monitorKVMUpgrade($scope.installConfig.stack_name);
     		$state.go("dashboard.monitor");
		});
    };

    $scope.upgrade = function(){
         $scope.doUpgrade();
    };

} );



