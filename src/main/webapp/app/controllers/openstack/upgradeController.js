angular.module('os').controller('osupgradectr', function($scope, $filter,  $log 
		,  OSService, monitorService, $dialogs, $state) {
    OSService.getImages().then(function(data){
            	$scope.oam_cm_images = data;
            	$scope.db_images = data;
            });
    OSService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.oscomInstance = [];
		for(var ci in $scope.comInstance){
			if(JSON3.parse($scope.com_instance.comConfig).environment ==  "OPENSTACK"){
				$scope.oscomInstance.push($scope.comInstance[ci]);
			}
		}
    });
    $scope.showInstance = function(){
           $scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    }
    $scope.doUpgrade = function (){
		 $scope.installConfig.oam_cm_image = $scope.oam_cm_image;
		 $scope.installConfig.db_image = $scope.db_image;
		OSService.upgrade(
         		$scope.installConfig,
    			function(data){
            			monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
                 		$state.go("dashboard.monitor");
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };

    $scope.upgrade = function(){
         $scope.doUpgrade();
    };
    
	$scope.doupgrade = function (){
		var installconfig = json3.parse($scope.com_instance.comconfig);
		installconfig.oam_cm_image = $scope.oam_cm_image;
		installconfig.db_image = $scope.db_image;
		kvmservice.upgrade(
         		$scope.installconfig,
    			function(data){
            			monitorservice.monitorkvmupgrade($scope.installconfig.active_host_ip);
                 		$state.go("dashboard.monitor");
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };

} );



