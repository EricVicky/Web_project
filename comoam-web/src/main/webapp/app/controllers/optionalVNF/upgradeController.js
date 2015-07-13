angular.module('kvm').controller('ovmupgradectr', function($scope, $filter,  $log, KVMService,  
		monitorService, DashboardService, $dialogs, $state) {
	
	$scope.stepNo = 1;
	
	$scope.setDefaultInstace = function(){
    	var selectedKVMInstance = DashboardService.getSelectedInstance();
    	if(selectedKVMInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.kvmcomInstance){
        		var com_config = JSON3.parse($scope.kvmcomInstance[inst].comConfig);
        		if(angular.equals(com_config,selectedKVMInstance)){
        		   $scope.com_instance = $scope.kvmcomInstance[inst];
        		   $scope.installConfig = com_config;
        		   return;
        		}
        }
  
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    }
	
	KVMService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "KVM"){
				if(JSON3.parse($scope.comInstance[ci].comConfig).comType == "QOSAC" ||
				     JSON3.parse($scope.comInstance[ci].comConfig).comType == "HPSIM"){
					$scope.kvmcomInstance.push($scope.comInstance[ci]);
				}
			}
		}
		$scope.setDefaultInstace();
		
    });
	
	$scope.reloadinstallconfig = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    };
	
    $scope.reloadimglist = function(){
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
    };
    

	$scope.loadimglist = function(host, dir) {
		KVMService.imagelist({
			"host" : host,
			"dir" : dir
		}).then(function(data) {
			$scope.imagelist = data;
	    });
	};
	
	$scope.upgrade = function(){
		KVMService.comstackStatus($scope.installconfig.deployment_prefix).then(function(status){
            		var action_in_progress = 2;
            		if(status.state == action_in_progress){
            			if(window.confirm("some operation  proceed on selected vnf instance, go to monitor?")){
            				monitorservice.monitor("KVM", status.lastaction, $scope.installconfig.comtype, $scope.installconfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doUpgrade();
            		}
        });
		
    $scope.doUpgrade = function (){
        KVMService.upgradeOVM($scope.installConfig).then( function(){
            	monitorservice.monitor("KVM", "UPGRADE", $scope.installconfig.comtype, $scope.installconfig.deployment_prefix);
             	$state.go("dashboard.monitor");
    	});
    };
        
        
}
	
});