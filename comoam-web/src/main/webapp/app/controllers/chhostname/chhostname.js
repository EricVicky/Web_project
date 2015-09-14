angular.module('chhostname',[]).controller('chhostnamectr', function($scope,KVMService,OSService,monitorService,$state){
	
    KVMService.getComInstance().then( function(data) {
		$scope.Instance = data;
		$scope.comInstance = [];
		for(var ci=0;ci<$scope.Instance.length;ci++){
			$scope.Instance[ci].comConfig = JSON3.parse($scope.Instance[ci].comConfig);
			if($scope.Instance[ci].comType == 'HPSIM'||$scope.Instance[ci].comType == 'ATC'){
				continue;
			}else{
				$scope.comInstance.push($scope.Instance[ci]);
			}
		}
		//$scope.setDefaultInstace();
		
    });
    
    $scope.initconfig = function(){
    	$scope.installConfig = $scope.com_instance.comConfig;
    	if($scope.installConfig.comType == 'FCAPS'||$scope.installConfig.comType == 'CM'){
    		$scope.installConfig.old_vm_config = {
    				"oam": { "old_hostname": $scope.installConfig.vm_config.oam.hostname},
    				"cm" : { "old_hostname": $scope.installConfig.vm_config.cm.hostname},
    				"db" : { "old_hostname": $scope.installConfig.vm_config.db.hostname}
    		};
    	}else if($scope.installConfig.comType == 'OAM'){
    		$scope.installConfig.old_vm_config = {
    				"oam": { "old_hostname": $scope.installConfig.vm_config.oam.hostname},
    				"db" : { "old_hostname": $scope.installConfig.vm_config.db.hostname}
    		};
    	}else{
    		$scope.installConfig.old_vm_config = {
    				"ovm": { "old_hostname": $scope.installConfig.vm_config.ovm.hostname}
    		};
    	}
    }; 
    
    
    $scope.changehostname = function(){
    	
    	if($scope.installConfig.environment == 'KVM'){
    		if($scope.installConfig.comType == 'QOSAC'){
    			KVMService.chQosacHostname($scope.installConfig).then( function(){
    				monitorService.monitor("KVM", "CHHOSTNAME", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
    				$state.go("dashboard.monitor");
    			});
    		}else{
    			KVMService.chHostname($scope.installConfig).then( function(){
    				monitorService.monitor("KVM", "CHHOSTNAME", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
    				$state.go("dashboard.monitor");
    			});
    		}
    	}else{
    		if($scope.installConfig.comType == 'QOSAC'){
    			OSService.chQosacHostname($scope.installConfig).then( function(){
    				monitorService.monitor("Openstack", "CHHOSTNAME", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
    				$state.go("dashboard.monitor");
    			});
    		}else{
    			OSService.chHostname($scope.installConfig).then( function(){
    				monitorService.monitor("Openstack", "CHHOSTNAME", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
    				$state.go("dashboard.monitor");
    			});	
    		}
    	}

    };
	

}); 